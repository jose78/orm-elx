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
package org.elx.orm.vendor;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elx.orm.annotations.Column;
import org.elx.orm.mapper.ParameterMapper;
import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Entity;
import org.elx.orm.utils.Tupla;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.SourceVendor;
import org.elx.orm.utils.type.TypeJava;
import org.elx.validation.ElxValidateException;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public abstract class Vendor {

	private static Logger log = Logger.getLogger(Vendor.class);

	private SourceVendor typeVendor = null;

	public Vendor(SourceVendor typeVendor) {
		this.typeVendor = typeVendor;
	}

	private AbstractCriteriaBuilder abstractCriteria = null;

	public void buildUpdate(Entity entity, String nameConnection)
			throws ElxValidateException {
		abstractCriteria = new UpdateCriteriaBuilder(entity, nameConnection);
	}

	public void buildDelete(Entity entity, String nameConnection) {
		abstractCriteria = new DeleteCriteriaBuilder(entity, nameConnection);
	}

	public void buildInsert(Entity entity, String nameConnection)
			throws ElxValidateException {
		abstractCriteria = new InsertCriteriaBuilder(entity, nameConnection);
	}

	/**
	 * Build the result of the Select.
	 * 
	 * @param rs
	 * @param clazz
	 * @return List of Entities such as the class.
	 * @throws SQLException
	 * @throws ElxGenericException
	 */
	public <T> java.util.List<T> getResultOfQuerySelect(
			ResultSet rs, Class<T> clazz) throws SQLException,
			ElxGenericException {
		final List<T> lstResul = new ArrayList<T>();
		// We need storage into HASHMAP of the name of the column in db and the
		// field into the class
		final HashMap<String, Field> mapAtrField = new HashMap<String, Field>();
		for (final Field auxField : clazz.getDeclaredFields()) {
			final Column anntColumn = auxField.getAnnotation(Column.class);
			mapAtrField.put(anntColumn.name().toUpperCase(), auxField);
		}

		try {
			while (rs.next()) {
				// For each Row we create a new Entity that will contain the
				// data of
				// the ResultSet and set their the values from resultSet
				final T dataNew = this.reloadSelect(mapAtrField, clazz, rs);
				lstResul.add(dataNew);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			rs.close();
		}

		return lstResul;
	}

	/**
	 * Get all data of the execution of the <b>SELECT</b>.
	 * 
	 * @param mapAtrField
	 *            Map whit the KEY-VALUE -> NameColumn-Field of the class.
	 * @param clazz
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws ElxGenericException
	 */
	private <T> T reloadSelect(
			HashMap<String, Field> mapAtrField, Class<T> clazz, ResultSet rs)
			throws SQLException, Exception {

		final T objSelect = Utils.getUtil().createInstance(clazz);

		final Integer contColumns = rs.getMetaData().getColumnCount();
		for (Integer index = new Integer(1); index <= contColumns; index++) {
			final String strColumnName = rs.getMetaData().getColumnLabel(index);
			final Field field = mapAtrField.get(strColumnName.toUpperCase());
			final Object value = rs.getObject(strColumnName);
			Utils.getUtil().setValueField(objSelect, field, value);
		}

		return objSelect;
	}

	final private static ParameterMapper PARAMETER_MAPPER = new ParameterMapper() {

		public <T> void setParameter(PreparedStatement statement, int index,
				T obj) throws SQLException {

			Tupla<TypeJava, Object> tupla = Utils.getUtil().getTupla(obj);
			switch (tupla.getKey()) {
			case java_lang_Long:
				statement.setLong(index, (Long) tupla.getValue());
				break;
			case java_lang_Double:
				statement.setDouble(index, (Double) tupla.getValue());
				break;
			case java_lang_Integer:
				statement.setInt(index, (Integer) tupla.getValue());
				break;
			case java_util_Date:
			case java_sql_Date:
				statement.setDate(index, (java.sql.Date) tupla.getValue());
				break;
			case java_lang_Boolean:
				statement.setBoolean(index, (Boolean) tupla.getValue());
				break;
			case java_lang_Float:
				statement.setFloat(index, (Float) tupla.getValue());
				break;
			case java_lang_Byte:
				statement.setByte(index, (Byte) tupla.getValue());
				break;
			case java_lang_Bytes:
				statement.setBytes(index, (byte[]) tupla.getValue());
				break;
			case java_lang_Short:
				statement.setShort(index, (Short) tupla.getValue());
				break;
			case java_lang_String:
				statement.setString(index, (String) tupla.getValue());
				break;
			case java_sql_Time:
				statement.setTime(index, (Time) tupla.getValue());
				break;
			case java_sql_Timestamp:
				statement.setTimestamp(index, (Timestamp) tupla.getValue());
				break;
			case java_sql_Ref:
				statement.setRef(index, (Ref) tupla.getValue());
				break;
			case java_sql_Blob:
				statement.setBlob(index, (Blob) tupla.getValue());
				break;
			case java_sql_Clob:
				statement.setClob(index, (Clob) tupla.getValue());
				break;
			case Object:
			case Null:
				statement.setObject(index, tupla.getValue());
				break;
			}
		}
	};

	final public ParameterMapper getParameterMapper(
			final SourceVendor typeVendor) {
		ParameterMapper mapper = null;

		// if (typeVendor == SourceVendor.Java || this.typeVendor !=
		// typeVendor){
		mapper = PARAMETER_MAPPER;
		// }else{
		// String nameMethod= "getParameterMapper"+typeVendor.toString();
		// mapper = Utils.getUtil().executeMethod(this, nameMethod );
		// }
		return mapper;
	}

	/**
	 * Build the consult that will execute the sequence
	 * 
	 * @param nameSchema
	 *            TODO
	 * @param strNameSeq
	 * 
	 * @return
	 */
	public abstract String buildNextValSequence(String nameSchema);

	/**
	 * Build select pagination with the original SELECT.
	 * 
	 * @param sql
	 * @return
	 */
	public abstract String buildPaginationConsult(String sql, Integer pagNum,
			Integer pagSize);

	public String getQuery() {
		return abstractCriteria.getSql();
	}

	public List<Object> getParameter() {
		return abstractCriteria.getListParameters();
	}

	public Entity loadaData(Class<?> clazz, Map<String, Object> mapResult) {
		return null;
	}

	/**
	 * @param nameTable
	 * @param nameSchema
	 * @param nameColumnTo
	 * @param aliasTable
	 * @param aliasTableFrom
	 * @param nameColumnFrom
	 * @return TODO
	 */
	abstract public String generateOuterJoin(String nameTable,
			String nameSchema, String nameColumnTo, String aliasTable,
			String aliasTableFrom, String nameColumnFrom);

	/**
	 * @param nameSchema
	 * @param nameTable
	 * @param nameAliasTable
	 *            TODO
	 * @param lstTables
	 * @param lstColums
	 * @param lstOuterJoin
	 * @param lstColumnsOrderBy
	 *            TODO
	 * @param lstColumnsWhere
	 *            TODO
	 * @return
	 */
	public StringBuilder generateQuery(String nameSchema, String nameTable,
			String nameAliasTable, List<String> lstTables,
			List<String> lstColums, List<String> lstOuterJoin,
			List<String> lstColumnsOrderBy, List<String> lstColumnsWhere) {

		StringBuilder sbColumns = new StringBuilder();

		StringBuilder sbCore = new StringBuilder();
		sbCore.append(Constant.STR_FROM).append(nameSchema).append(nameTable)
				.append(" ").append(nameAliasTable);

		for (String outerJoins : lstOuterJoin) {
			sbCore.append(outerJoins);
		}

		sbCore.append("  \nWHERE $WHERE \n ORDER BY ");

		String coma = "";
		for (String columns : lstColumnsOrderBy) {
			if (columns.trim().length() > 0) {
				sbColumns.append(coma).append(columns);
				coma = " , ";
			}
		}
		sbCore.append(sbColumns).append("\n");

		StringBuilder sb = new StringBuilder();
		sb.append("\n").append(Constant.STR_SELECT);

		sbColumns = new StringBuilder();
		coma = "";
		for (String columns : lstColums) {
			if (columns.trim().length() > 0) {
				sbColumns.append(coma).append(columns);
				coma = " , ";
			}
		}
		sb.append(sbColumns);

		sb.append(sbCore);

		return sb;
	}

	abstract public String toTrim(String column);

	abstract public String toLowerCase(String column);

	abstract public String toUpperCase(String column);

	//
	// /**
	// * @param nameSchema
	// * @param nameTable
	// * @param nameAliasTable
	// * TODO
	// * @param lstTables
	// * @param lstColums
	// * @param lstOuterJoin
	// * @param lstColumnsCore
	// * TODO
	// * @param lstColumnsWhere TODO
	// * @return
	// */
	// public StringBuilder generateQuery(String nameSchema, String nameTable,
	// String nameAliasTable, List<String> lstTables,
	// List<String> lstColums, List<String> lstOuterJoin,
	// List<String> lstColumnsCore, List<String> lstColumnsWhere) {
	//
	// StringBuilder sbCore = new StringBuilder();
	// StringBuilder sbColumns = new StringBuilder();
	//
	// sbCore.append(" (").append(Constant.STR_SELECT);
	// String coma = "";
	// for (String columns : lstColumnsCore) {
	// if (columns.trim().length() > 0) {
	// sbColumns.append(coma).append(columns);
	// coma= " , ";
	// }
	// }
	// sbCore.append(sbColumns);
	//
	// sbCore.append(Constant.STR_FROM).append(nameSchema).append(nameTable)
	// .append(" ").append(nameAliasTable);
	//
	// for (String outerJoins : lstOuterJoin) {
	// sbCore.append(outerJoins);
	// }
	//
	// sbCore.append(" ) CORE, ");
	//
	//
	// StringBuilder sb = new StringBuilder();
	// sb.append("\n").append(Constant.STR_SELECT);
	//
	// sbColumns = new StringBuilder();
	// coma= "";
	// for (String columns : lstColums) {
	// if (columns.trim().length() > 0) {
	// sbColumns.append(coma).append(columns);
	// coma =" , ";
	// }
	// }
	// sb.append(sbColumns.substring(0, sbColumns.length() - 3));
	//
	// sb.append(Constant.STR_FROM);
	//
	// sb.append(sbCore);
	// coma= "";
	// for (String nameTables : lstTables) {
	// sb.append(coma).append(nameTables);
	// coma =" , ";
	// }
	//
	// sb.append(Constant.STR_WHERE);
	// coma= "     ";
	// for (String strWhere : lstColumnsWhere) {
	// sb.append(coma).append(strWhere);
	// coma= "\n AND ";
	// }
	//
	// return sb;
	// }
	// }
}