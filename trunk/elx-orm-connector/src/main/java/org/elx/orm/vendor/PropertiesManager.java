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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class PropertiesManager {

	public static final String TO_TRIM = "trim";
	public static final String TO_UPPER_CASE = "toUpperCase";
	public static final String TO_LOWER_CASE = "toLowerCase";
	private static Properties prop = null;
	public static String OUTER_JOIN = "outerJoin";
	public static String PAGINATION = "pagination";
	public static String NEXT_SEQUENCE = "NEXT_SEQ";

	public static String getConsultByVendor(String strVendorName,
			String strTypeConsult) {
		try {
			PropertiesManager.init();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final String strConsult = PropertiesManager.prop
				.getProperty(strVendorName.replace(" ", "_") + "."
						+ strTypeConsult);
		return strConsult;
	}

	private static void init() throws Exception {
		if (PropertiesManager.prop == null) {
			PropertiesManager.prop = new Properties();
			PropertiesManager.load("sql_derby.properties");
			PropertiesManager.load("sql_postgre.properties");
		}
	}

	/**
	 * Load a Properties File
	 * 
	 * @param propsFile
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties load(File propsFile) throws IOException {
		final Properties props = new Properties();
		final FileInputStream fis = new FileInputStream(propsFile);
		props.load(fis);
		fis.close();
		return props;
	}

	/**
	 * Load a properties file from the class path
	 * 
	 * @param propsName
	 * @return Properties
	 * @throws Exception
	 */
	public static void load(String propsName) throws Exception {
		final URL url = ClassLoader.getSystemResource(propsName);
		PropertiesManager.prop.load(url.openStream());
	}

}
