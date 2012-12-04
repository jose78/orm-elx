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
import org.elx.orm.utils.type.TypeJava;
import org.elx.orm.vendor.PropertiesManager;
import org.elx.orm.vendor.Vendor;


/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class PostgreVendor extends Vendor {

	public PostgreVendor(SourceVendor typeVendor) {
		super(typeVendor);
	}

	private static Properties properties = null;
	static {
		final URL url = ClassLoader.getSystemResource("sql_postgre.properties");
		properties = new Properties();
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	final private static String strVendorName = "PostgreSQL";

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

		return getConsultByVendor(PropertiesManager.OUTER_JOIN)
				.replace("$SCHEMA", nameSchema)
				.replace("$TABLE", nameTable + "")
				.replace("$ALIAS", aliasTableTo + "")
				.replace("$CONDITION_FROM",
						aliasTableFrom + "." + nameColumnFrom + "")
				.replace("$CONDITION_TO",
						aliasTableTo + "." + nameColumnTo + "");
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
	public String toTrim(String column) {
		return getConsultByVendor(PropertiesManager.TO_TRIM).replace("$COLUMN",
				column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elx.orm.vendor.Vendor#buildPaginationConsult(java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String buildPaginationConsult(String sql, Integer pagNum,
			Integer pagSize) {
		return getConsultByVendor(PropertiesManager.PAGINATION)
				.replace("$SELECT", sql).replace("$PAG", pagNum + "")
				.replace("$SIZE", pagSize + "");
	}

	@Override
	public String buildNextValSequence(String nameSchema) {
		return getConsultByVendor(PropertiesManager.NEXT_SEQUENCE);

	}
	
	

}