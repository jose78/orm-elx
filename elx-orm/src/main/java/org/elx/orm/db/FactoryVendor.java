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

import org.elx.orm.utils.Utils;
import org.elx.orm.utils.type.SourceVendor;
import org.elx.orm.vendor.Vendor;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
class FactoryVendor {

	/**
	 * 
	 * @param typeVendor
	 * @return
	 */
	public static Vendor get(TypeVendor typeVendor, SourceVendor sourceVendor) {

		Vendor vendor = null;
		try {
			switch (typeVendor) {
			case Apache_Derby:

				vendor = (Vendor) Utils.getUtil()
						.getClass("org.elx.orm.vendor.DerbyVendor")
						.getDeclaredConstructor(SourceVendor.class)
						.newInstance(sourceVendor);

				break;
			case MySQL:
				vendor = (Vendor) Utils.getUtil()
						.getClass("org.elx.orm.vendor.MySQLVendor")
						.getDeclaredConstructor(SourceVendor.class)
						.newInstance(sourceVendor);

				break;
			case PostgreSQL:
				vendor = (Vendor) Utils.getUtil()
						.getClass("org.elx.orm.vendor.PostgreVendor")
						.getDeclaredConstructor(SourceVendor.class)
						.newInstance(sourceVendor);

				break;
			case Oracle:
				vendor = (Vendor) Utils.getUtil()
						.getClass("org.elx.orm.vendor.OracleVendor")
						.getDeclaredConstructor(SourceVendor.class)
						.newInstance(sourceVendor);
				break;
			default:

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendor;
	}

}
