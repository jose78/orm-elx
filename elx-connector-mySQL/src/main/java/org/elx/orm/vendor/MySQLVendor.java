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

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Properties;

import org.elx.orm.mapper.ParameterMapper;
import org.elx.orm.utils.Tupla;
import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.SourceVendor;
import org.elx.orm.utils.type.TypeMySQL;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class MySQLVendor extends Vendor {

	public MySQLVendor(SourceVendor typeVendor) {
		super(typeVendor);
	}

	private static Properties properties = null;
	static {
		final URL url = ClassLoader.getSystemResource("sql_mysql.properties");
		properties = new Properties();
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	final private static String strVendorName = "MySQL";

	@Override
	public String buildPaginationConsult(String sql, Integer pagNum,
			Integer pagSize) {

		pagNum = (pagNum - 1) * pagSize;

		return getConsultByVendor(PropertiesManager.PAGINATION)
				.replace("$SELECT", sql).replace("$PAG", pagNum + "")
				.replace("$SIZE", pagSize + "");
	}

	public static String getConsultByVendor(String strTypeConsult) {

		final String strConsult = properties.getProperty(strVendorName + "."
				+ strTypeConsult);
		return strConsult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.elx.vendor.Vendor#generateOuterJoin(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public String generateOuterJoin(String nameTable, String nameSchema,
			String nameColumnTo, String aliasTableTo, String aliasTableFrom,
			String nameColumnFrom) {
		String valueSchema = "$SCHEMA";
		if (nameSchema.length() == 0) {
			valueSchema += ".";
		}

		return getConsultByVendor(PropertiesManager.OUTER_JOIN)
				.replace(valueSchema, nameSchema)
				.replace("$TABLE", nameTable + "")
				.replace("$ALIAS", aliasTableFrom + "")
				.replace("$CONDITION_FROM",
						aliasTableFrom + "." + nameColumnFrom + "")
				.replace("$CONDITION_TO",
						aliasTableTo + "." + nameColumnTo + "");
	}

	@Override
	public String toTrim(String column) {
		return getConsultByVendor(PropertiesManager.TO_TRIM).replace("$COLUMN",
				column);
	}

	@Override
	public String toLowerCase(String column) {
		return getConsultByVendor(PropertiesManager.TO_LOWER_CASE).replace(
				"$COLUMN", column);
	}

	@Override
	public String toUpperCase(String column) {
		return getConsultByVendor(PropertiesManager.TO_UPPER_CASE).replace(
				"$COLUMN", column);
	}

	@Override
	public String buildNextValSequence(String nameSchema) {
		return getConsultByVendor(PropertiesManager.NEXT_SEQUENCE);
	}

//	public ParameterMapper getParameterMapperMySQL() {
//		return new ParameterMapper() {
//
//			@Override
//			public <T> void setParameter(PreparedStatement statement,
//					int index, T obj) throws SQLException {
//
//				com.mysql.jdbc.PreparedStatement mysqlStatement = null;
//
//				Tupla<TypeMySQL, Object> tupla = Utils.getUtil().getTuplaMySql(
//						obj);
//				switch (tupla.getKey()) {
//				case java_lang_Long:
//					statement.setLong(index, (Long) tupla.getValue());
//					break;
//				case java_lang_Double:
//					statement.setDouble(index, (Double) tupla.getValue());
//					break;
//				case java_lang_Integer:
//					statement.setInt(index, (Integer) tupla.getValue());
//					break;
//				case java_util_Date:
//				case java_sql_Date:
//					statement.setDate(index, (java.sql.Date) tupla.getValue());
//					break;
//				case java_lang_Boolean:
//					statement.setBoolean(index, (Boolean) tupla.getValue());
//					break;
//				case java_lang_Float:
//					statement.setFloat(index, (Float) tupla.getValue());
//					break;
//				case java_lang_Byte:
//					statement.setByte(index, (Byte) tupla.getValue());
//					break;
//				case java_lang_Bytes:
//					statement.setBytes(index, (byte[]) tupla.getValue());
//					break;
//				case java_lang_Short:
//					statement.setShort(index, (Short) tupla.getValue());
//					break;
//				case java_lang_String:
//					statement.setString(index, (String) tupla.getValue());
//					break;
//				case java_sql_Time:
//					statement.setTime(index, (Time) tupla.getValue());
//					break;
//				case java_sql_Timestamp:
//					statement.setTimestamp(index, (Timestamp) tupla.getValue());
//					break;
//				case java_sql_Ref:
//					statement.setRef(index, (Ref) tupla.getValue());
//					break;
//				case Object:
//					statement.setObject(index, tupla.getValue());
//					break;
//				case java_sql_Blob:
//					statement.setBlob(index, (Blob) tupla.getValue());
//					break;
//				case java_sql_Clob:
//					statement.setClob(index, (Clob) tupla.getValue());
//					break;
//				case Null:
//					statement.setObject(index, tupla.getValue());
//					break;
//				case com_mysql_jdbc_Blob:
//					mysqlStatement = (com.mysql.jdbc.PreparedStatement) statement;
//					mysqlStatement.setBlob(index,
//							(com.mysql.jdbc.Blob) tupla.getValue());
//				case com_mysql_jdbc_Clob:
//					mysqlStatement = (com.mysql.jdbc.PreparedStatement) statement;
//					mysqlStatement.setClob(index,
//							(com.mysql.jdbc.Clob) tupla.getValue());
//				}
//			}
//		};
//	}

}
