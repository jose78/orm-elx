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
import java.util.Properties;

import org.elx.orm.utils.type.SourceVendor;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class DerbyVendor extends Vendor {

	public DerbyVendor(SourceVendor typeVendor) {
		super(typeVendor);
	}

	private static Properties properties = null;
	static {
		final URL url = ClassLoader.getSystemResource("sql_derby1.properties");
		properties = new Properties();
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	final private static String strVendorName = "DERBY";

	@Override
	public String buildNextValSequence(String nameSchema) {

		String valueSchema = "$SCHEMA";
		if (nameSchema.length() == 0) {
			valueSchema += ".";
		}

		return getConsultByVendor(PropertiesManager.NEXT_SEQUENCE).replace(
				valueSchema, nameSchema);
	}

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
	 * @see org.elx.orm.vendor.Vendor#generateOuterJoin(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
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

	// @Override
	// public void setParams(PreparedStatement statement,
	// List<Tupla<?, Object>> lstParameter) throws SQLException {
	//
	// for (int index = 1; index <= lstParameter.size(); index++) {
	// final Tupla<?, Object> tupla = lstParameter.get(index - 1);
	// switch (tupla.getKey()) {
	// case Long:
	// statement.setLong(index, (Long) tupla.getValue());
	// break;
	// case Double:
	// statement.setDouble(index, (Double) tupla.getValue());
	// break;
	// case Integer:
	// statement.setInt(index, (Integer) tupla.getValue());
	// break;
	// case Date:
	// statement.setDate(index, (java.sql.Date) tupla.getValue());
	// break;
	// case Boolean:
	// statement.setBoolean(index, (Boolean) tupla.getValue());
	// break;
	// case Float:
	// statement.setFloat(index, (Float) tupla.getValue());
	// break;
	// case Byte:
	// statement.setByte(index, (Byte) tupla.getValue());
	// break;
	// case Bytes:
	// statement.setBytes(index, (byte[]) tupla.getValue());
	// break;
	// case Short:
	// statement.setShort(index, (Short) tupla.getValue());
	// break;
	// case String:
	// statement.setString(index, (String) tupla.getValue());
	// break;
	// case Time:
	// statement.setTime(index, (Time) tupla.getValue());
	// break;
	// case Timestamp:
	// statement.setTimestamp(index, (Timestamp) tupla.getValue());
	// break;
	// case Ref:
	// statement.setRef(index, (Ref) tupla.getValue());
	// break;
	// case Object:
	// statement.setObject(index, tupla.getValue());
	// break;
	// case Blob:
	// statement.setBlob(index, (Blob) tupla.getValue());
	// break;
	// case Clob:
	// statement.setClob(index, (Clob) tupla.getValue());
	// break;
	// case Null:
	// statement.setObject(index, tupla.getValue());
	// break;
	// }
	// }
	// }
}
