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
package org.elx.orm;

import java.net.URL;

import org.elx.orm.annotations.Connection;
import org.elx.orm.annotations.ConnectionDef;
import org.junit.Test;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class TestScaner {

	@Test
	public void testScan() throws Exception {

		URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan
															// java.class.path
		AnnotationDB db = new AnnotationDB();
		db.scanArchives(urls);
		for (String value : db.getAnnotationIndex().get(
				Connection.class.getName())) {
			System.out.println("Entity:" + value);
		}
		for (String value : db.getAnnotationIndex().get(
				ConnectionDef.class.getName())) {
			System.out.println("Conection:" + value);
		}

	}

}
