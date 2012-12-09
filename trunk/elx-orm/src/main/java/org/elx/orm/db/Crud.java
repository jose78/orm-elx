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

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.elx.orm.annotations.Connection;
import org.elx.orm.annotations.ConnectionDef;
import org.elx.orm.annotations.Connections;
import org.elx.orm.annotations.Id;
import org.elx.orm.annotations.Table;
import org.elx.orm.annotations.Tables;
import org.elx.orm.utils.CacheClass;
import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.SourceVendor;
import org.elx.orm.validate.ElxValidateException;
import org.elx.orm.vendor.Vendor;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class Crud implements CrudPattern {

	public Crud() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.db.CrudPattern#update(T)
	 */
	public <T extends Entity> Integer insert(T entity) throws SQLException,
			ElxGenericException, ElxValidateException {

		// CNX_NAME , SCHEMA_NAME
		Map<String, String> mapCnxSchema = new TreeMap<String, String>();

		CacheClass cacheClass = CacheClass.getCacheClass(entity.getClass());
		final String nameConnection = cacheClass.getIdField()
				.getAnnotation(Id.class).nameConnection();

		Tables anntTables = entity.getClass().getAnnotation(Tables.class);
		if (anntTables != null) {
			for (Table anntTable : anntTables.lstTables()) {
				mapCnxSchema.put(anntTable.connection().nameConnection(),
						anntTable.schema());
			}
		} else {
			Table anntTable = entity.getClass().getAnnotation(Table.class);
			mapCnxSchema.put(anntTable.connection().nameConnection(),
					anntTable.schema());

		}

		List<AbstractConncection> abstractConncections = getConncection(
				entity.getClass(), TYPE_OPERATION.WRITE);
		Integer numRows = 0;

		Collections.sort(abstractConncections,
				new Comparator<AbstractConncection>() {
					public int compare(AbstractConncection o1,
							AbstractConncection o2) {
						int result = -1;
						if (o1.getName().equals(nameConnection)
								&& o2.getName().equals(nameConnection)) {
							result = 0;
						} else if (o1.getName().equals(nameConnection)) {
							result = -1;
						} else if (o2.getName().equals(nameConnection)) {
							result = 1;
						} else {
							result = o1.getName().compareTo(o2.getName());
						}
						return result;
					}
				});

		if (!nameConnection.equals(abstractConncections.get(0).getName())) {
			throw new ElxGenericException(
					"Error, the name of connection to generate the PK:["
							+ nameConnection
							+ "] is not  the same that the connection:["
							+ abstractConncections.get(0).getName() + "].");
		}

		

		Boolean flagFirstPass = false;
		for (int index = 0; index < abstractConncections.size(); index++) {

			final String nameSchema = mapCnxSchema.get(abstractConncections
					.get(index).getName());

			AbstractConncection abs = abstractConncections.get(index);
			Vendor vendor = FactoryVendor.get(abs.getVendor(),
					SourceVendor.Java);
			if (!flagFirstPass) {
				flagFirstPass = true;
				if (abs.getVendor() != TypeVendor.MySQL) {
					abs.executeSequeces(entity,
							vendor.buildNextValSequence(nameSchema));
				}
			}
			vendor.buildInsert(entity, abs.getName());
			numRows = abs.executeInsert(entity, vendor.getQuery(),
					vendor.getParameterMapper(SourceVendor.Java),
					vendor.getParameter());
		}
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.db.CrudPattern#update(T)
	 */
	public <T extends Entity> Integer delete(T entity) throws SQLException,
			ElxGenericException {

		List<AbstractConncection> abstractConncections = getConncection(
				entity.getClass(), TYPE_OPERATION.WRITE);
		Integer numRows = 0;

		for (int index = 0; index < abstractConncections.size(); index++) {
			AbstractConncection abs = abstractConncections.get(index);
			Vendor vendor = FactoryVendor.get(abs.getVendor(),
					SourceVendor.Java);
			vendor.buildDelete(entity, abs.getName());
			numRows = abs.execute(vendor.getQuery(),
					vendor.getParameterMapper(SourceVendor.Java),
					vendor.getParameter());
		}
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.db.CrudPattern#update(T)
	 */
	public <T extends Entity> Integer update(T entity) throws SQLException,
			ElxGenericException, ElxValidateException {

		List<AbstractConncection> abstractConncections = getConncection(
				entity.getClass(), TYPE_OPERATION.WRITE);
		Integer numRows = 0;

		
		for (int index = 0; index < abstractConncections.size(); index++) {
			AbstractConncection abs = abstractConncections.get(index);
			Vendor vendor = FactoryVendor.get(abs.getVendor(),
					SourceVendor.Java);
			vendor.buildUpdate(entity, abs.getName());
			numRows += abs.execute(vendor.getQuery(),
					vendor.getParameterMapper(SourceVendor.Java),
					vendor.getParameter());
		}
		return numRows;
	}

	/**
	 * This Method retrieves the entity's connections to execute the operations.
	 * 
	 * @param clazz
	 *            Entity that contain the form to access to
	 *            {@link ManagerConncection} and the name of connections to use.
	 * @param enumTypeOperation
	 *            Type of operation (WRITE or READ)
	 * @return List with the connections.
	 * @throws ElxGenericException
	 */
	private List<AbstractConncection> getConncection(Class<?> clazz,
			TYPE_OPERATION enumTypeOperation) throws ElxGenericException {

		List<AbstractConncection> lstAbstractConncections = null;
		Tables anntTables = clazz.getAnnotation(Tables.class);
		if (anntTables != null) {

			Connection[] lstConnections = new Connection[anntTables.lstTables().length];

			for (int index = 0; index < lstConnections.length; index++) {
				Table anntTable = anntTables.lstTables()[index];
				lstConnections[index] = anntTable.connection();
			}
			lstAbstractConncections = readDataConnections(enumTypeOperation,
					lstConnections);
		} else {
			Table anntTable = clazz.getAnnotation(Table.class);
			if (anntTable != null) {
				lstAbstractConncections = readDataConnections(
						enumTypeOperation, anntTable.connection());
			} else {
				Connections anntConnections = clazz
						.getAnnotation(Connections.class);
				if (anntConnections != null) {
					lstAbstractConncections = readDataConnections(
							enumTypeOperation, anntConnections.lstConnections());
				} else {
					Connection anntConnection = clazz
							.getAnnotation(Connection.class);
					if (anntConnection != null) {
						lstAbstractConncections = readDataConnections(
								enumTypeOperation, anntConnection);
					} else {
						throw new ElxGenericException("Connection BAD USED.");
						// Error no hay conexión¡¡¡¡
					}
				}
			}
		}

		return lstAbstractConncections;
	}

	private List<AbstractConncection> readDataConnections(
			TYPE_OPERATION enumTypeOperation, Connection... lstConnections)
			throws ElxGenericException {

		List<AbstractConncection> lstAbstractConncections = new ArrayList<AbstractConncection>(
				lstConnections.length);
		ManagerConncection managerConncection = null;

		for (int index = 0; index < lstConnections.length; index++) {

			
			
			Connection anntConnection = lstConnections[index];
			
			ConnectionDef anntConnectionDef= SessionConncection.getManagerConncection().get(anntConnection.nameConnection()).getClass().getAnnotation(ConnectionDef.class);
			
			Class<?> clazzManager = Utils.getUtil().getClass(
					anntConnectionDef.nameClassConnectionProvider());
			if (clazzManager == null) {
				throw new ElxGenericException("Error, this class:["
						+ anntConnectionDef.nameClassConnectionProvider()
						+ "] not exist.");
			}
			try {
				Method method = clazzManager.getMethod(anntConnectionDef
						.nameStaticMethodConnectionProvider());
				managerConncection = (ManagerConncection) method.invoke(null);
				if (method == null) {
					throw new ElxGenericException(
							"Error, this method:["
									+ anntConnectionDef
											.nameStaticMethodConnectionProvider()
									+ "] is not contained in the class:["
									+ anntConnectionDef
											.nameClassConnectionProvider()
									+ "].");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			AbstractConncection abstractConncection = managerConncection
					.get(anntConnection.nameConnection());
			if (abstractConncection == null) {
				throw new ElxGenericException(
						"Error, the name of the conection:["
								+ anntConnection.nameConnection()
								+ "] isn't found.");
			}

			Boolean flagAddConnection = false;

			switch (enumTypeOperation) {
			case READ:
				flagAddConnection = abstractConncection.getClass()
						.getAnnotation(ConnectionDef.class).read();
				break;
			case WRITE:
				flagAddConnection = abstractConncection.getClass()
						.getAnnotation(ConnectionDef.class).write();
				break;
			}

			if (flagAddConnection) {
				lstAbstractConncections.add(abstractConncection);
			}
		}
		return lstAbstractConncections;
	}

	private enum TYPE_OPERATION {
		READ, WRITE
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.db.CrudPattern#read(java.lang.Class,
	 * org.elx.orm.vendor.Select)
	 */
	public <T, C extends Collection<T>> C read(Class<T> clazz,
			Select select) throws SQLException, ElxGenericException {

		C result = (C) new ArrayList<T>();
		
		List<AbstractConncection> abstractConncections = getConncection(clazz,
				TYPE_OPERATION.READ);
		for (int index = 0; index < abstractConncections.size(); index++) {
			AbstractConncection abs = abstractConncections.get(index);
			Vendor vendor = FactoryVendor.get(abs.getVendor(),
					SourceVendor.Java);

			String query = vendor.buildPaginationConsult(select.getQuery(),
					select.getPageNum(), select.getPageSize());

			ResultSet resultSet = abs.executeSelect(query,
					vendor.getParameterMapper(SourceVendor.Java),
					select.getParameters());

			result.addAll(vendor.getResultOfQuerySelect(resultSet, clazz));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.db.CrudPattern#find(java.lang.Class,
	 * net.java.elx.db.Criteria)
	 */
	public <T extends Entity, C extends Collection<T>> C find(Class<T> clazz,
			Integer level, Criteria criteria) throws SQLException,
			ElxGenericException {


		Collection<T> result = new ArrayList<T>();

		List<AbstractConncection> abstractConncections = getConncection(clazz,
				TYPE_OPERATION.READ);

		for (int index = 0; index < abstractConncections.size(); index++) {
			AbstractConncection abs = abstractConncections.get(index);
			Vendor vendor = FactoryVendor.get(abs.getVendor(),
					SourceVendor.Java);

			Reader<T> reader = new Reader<T>(vendor, clazz, level,
					abs.getName());

			String condition = null;
			if (criteria.getWhere().length() > 0) {
				condition = criteria.getWhere().toString();
			} else {
				condition = "1 = 1";
			}

			String query = reader.getQuery().toString()
					.replace("$WHERE", condition);

			if (criteria.isPagination()) {
				query = vendor.buildPaginationConsult(query,
						criteria.getPageNum(), criteria.getPageSize());
			}

			ResultSet resultSet = abs.executeSelect(query,
					vendor.getParameterMapper(SourceVendor.Java),
					criteria.getParameters());
			result.addAll(reader.buildResult(resultSet));

		}

		return (C) result;
	}

}