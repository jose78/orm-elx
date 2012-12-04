/*Copyright 2012 ELX

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package org.elx.orm.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import org.apache.log4j.Logger;
import org.elx.orm.annotations.ConnectionDef;
import org.elx.orm.annotations.Id;
import org.elx.orm.mapper.ParameterMapper;
import org.elx.orm.utils.CacheClass;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;

/**
 * This class must be extends by the DB connection.
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public abstract class AbstractConncection {

	private static Logger log = Logger.getLogger(AbstractConncection.class);
	private boolean closeCnx = true;
	protected Connection cnx = null;
	private TypeVendor typeVendor;
	private String nameConnection = null;

	private int contMain = 0;
	private int cont = 0;

	public AbstractConncection() {
		nameConnection = this.getClass().getAnnotation(ConnectionDef.class)
				.nameConnection();
		init();
	}

	public void executeTest() throws SQLException {
		getConnection();
		 log.info("Connection:" + nameConnection + " -> START OK");
		getVendor();
		close();
		 log.info("Connection:" + nameConnection + " -> CLOSE OK");
	}

	public ResultSet executeSelect(String select, ParameterMapper mapper,
			List<Object> objects) throws SQLException {

		 log.info("QUERY:" + select);
		PreparedStatement statement = getConnection().prepareStatement(select);
		for (int index = 1; index <= objects.size(); index++) {
			mapper.setParameter(statement, index, objects.get(index - 1));
		}

		return statement.executeQuery();
	}

	public ResultSet executeSelect(String select, ParameterMapper mapper,
			Object... objects) throws SQLException {
		 log.info("Query:[" + select + "]");
		PreparedStatement statement = getConnection().prepareStatement(select);

		if (objects != null) {
			for (int index = 1; index <= objects.length; index++) {
				mapper.setParameter(statement, index, objects[index - 1]);
			}
		}

		return statement.executeQuery();
	}

	public Boolean isClosed() {
		return closeCnx;
	}

	public Connection getConnection() {
		contMain++;

		Connection tmp = null;
		if (closeCnx) {
			cont++;
			tmp = get();
			 log.info(" *** YOU HAVE CREATED A NEW CONNECTION¡¡¡¡ [" + contMain	+ "," + cont + "]--> " + getName());
		} else {
			tmp = cnx;
		}
		if (!cnx.equals(tmp)) {
			log.error("You are not using the connection of father.");
		}
		closeCnx = false;
		return tmp;
	}

	/**
	 * Execute NextVal of sequence.
	 * 
	 * @param strNameSeq
	 * @param cnx
	 * @return
	 * @throws SQLException
	 */
	private Long getSequence(String strNameSeq, String sqlExecuteSequence)
			throws SQLException {
		Long res = null;

		Connection cnx = getConnection();

		final String strCobnsult = sqlExecuteSequence.replace("$PARAM",
				strNameSeq);
		 log.info("Connection:[" + getName() + "] - Query:[ " + strCobnsult	+ "]");
		final PreparedStatement statement = cnx.prepareStatement(strCobnsult);
		final ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			res = rs.getLong(1);
		}
		return res;
	}

	protected void executeSequeces(Entity entity, String sqlExecuteSequence)
			throws SQLException {
		for (Field field : entity.getClass().getDeclaredFields()) {
			Id anntId = field.getAnnotation(Id.class);
			if (anntId != null) {

				Long idValue = getSequence(anntId.sequenceName(),
						sqlExecuteSequence);
				Utils.getUtil().setValueField(entity, field, idValue);
			}
		}
	}

	protected Integer executeInsert(Entity entity, String sql,
			ParameterMapper parameterMapper, List<Object> parameter)
			throws SQLException {
		 log.info("Connection:[" + nameConnection + "] - Query:[" + sql + "]");

		PreparedStatement statement = null;

		Integer numRows = null;

		if (TypeVendor.MySQL != getVendor()) {
			statement = getConnection().prepareStatement(sql);
			for (int index = 1; index <= parameter.size(); index++) {
				parameterMapper.setParameter(statement, index,
						parameter.get(index - 1));
			}
			numRows = statement.executeUpdate();
		} else {
			// MSQL

			Field field = CacheClass.getCacheClass(entity.getClass())
					.getIdField();
			statement = getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			for (int index = 1; index <= parameter.size(); index++) {
				parameterMapper.setParameter(statement, index,
						parameter.get(index - 1));
			}
			numRows = statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs != null) {
				while (rs.next()) {
					Object obj = rs.getObject(1);
					Utils.getUtil().setValueField(entity, field, obj);
				}
			}
		}
		return numRows;
	}

	public Integer execute(String sql, ParameterMapper parameterMapper,
			List<Object> parameter) throws SQLException {

		 log.info("Query:[" + sql + "]");

		PreparedStatement statement = getConnection().prepareStatement(sql);

		for (int index = 0; index < parameter.size(); index++) {
			parameterMapper
					.setParameter(statement, index+1, parameter.get(index));
		}

		Integer numRows = null;

		numRows = statement.executeUpdate();

		return numRows;
	}

	/**
	 * Retrieve the Vendor of the connection.
	 * 
	 * @return vendor.
	 */
	public TypeVendor getVendor() {
		if (typeVendor == null) {
			String strTypeVendor = null;
			try {
				strTypeVendor = getConnection().getMetaData()
						.getDatabaseProductName();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			strTypeVendor = strTypeVendor.replace(" ", "_");
			for (final TypeVendor auxTypeVendor : TypeVendor.values()) {
				if (auxTypeVendor.toString().equals(strTypeVendor)) {
					this.typeVendor = auxTypeVendor;
					break;
				}
			}
		}
		return typeVendor;
	}

	/**
	 * Close the connection.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (closeCnx) {
			log.error("You are trying to close a closed connection.");
		} else {
			getConnection().rollback();
			getConnection().close();
			closeCnx = true;
			cnx = null;
		}
	}

	protected void rollback() throws SQLException {
		getConnection().commit();
	}

	public void commit() throws SQLException {
		getConnection().commit();
	}

	abstract protected void init();

	abstract protected Connection get();

	/**
	 * Get the name of the connection.
	 * 
	 * @return
	 */
	final public String getName() {
		return this.getClass().getAnnotation(ConnectionDef.class)
				.nameConnection();
	}

}
