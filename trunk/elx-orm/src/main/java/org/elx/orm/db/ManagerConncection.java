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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.elx.orm.annotations.Connection;
import org.elx.orm.annotations.ConnectionDef;
import org.elx.orm.annotations.Connections;
import org.elx.orm.annotations.Table;
import org.elx.orm.annotations.Tables;
import org.elx.orm.utils.CacheClass;
import org.elx.orm.utils.ElxGenericException;
import org.elx.orm.utils.Utils;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * 
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class ManagerConncection {

	private static Boolean flagInit = true;
	private static ManagerConncection managerConncection = null;
	private Logger log = Logger.getLogger(ManagerConncection.class);
	private Map<String, AbstractConncection> containerInstances = null;

	protected ManagerConncection() {
		try {
			containerInstances = new TreeMap<String, AbstractConncection>();
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized private void init() throws ElxGenericException {

		if (flagInit) {
			flagInit = false;
			URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan
			// java.class.path
			AnnotationDB db = new AnnotationDB();
			try {
				db.scanArchives(urls);
			} catch (IOException e1) {
				throw new ElxGenericException(e1.getMessage());
			} catch (Throwable e) {
				e.printStackTrace();
			}

			Class<?> classs[] = { Connections.class, Connection.class,
					Tables.class, Table.class };

			for (Class<?> anntClazz : classs) {

				Set<String> result = db.getAnnotationIndex().get(
						anntClazz.getName());
				if (result != null) {
					for (String value : result) {
						Class<?> clazz = getClass(value);

						try {
							CacheClass.addClass(clazz);
						} catch (ElxGenericException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			}

			for (String value : db.getAnnotationIndex().get(
					ConnectionDef.class.getName())) {
				Class<?> clazz = getClass(value);
				AbstractConncection conncection = (AbstractConncection) Utils
						.getUtil().createInstance(clazz);

				ManagerConncection.this.containerInstances.put(
						conncection.getName(), conncection);
			}
			test();
		}

	}

	private Class<?> getClass(String nameClass) {
		Class<?> claz = null;
		try {
			claz = Class.forName(nameClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return claz;
	}

	public void test() {
		log.info("**************************************************************************************");
		log.info("****************************** START TEST ********************************************");
		log.info("**************************************************************************************");
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			for (Entry<String, AbstractConncection> entry : map.entrySet()) {
				try {
					entry.getValue().executeTest();
				} catch (Exception e) {
					log.error("ERROR TEST CONNECTION " + entry.getKey());
				}
			}
		}
		log.info("End test connection ok.");
		log.info("**************************************************************************************");
		log.info("****************************** START APP *********************************************");
		log.info("**************************************************************************************");
	}

	/**
	 * Retrieve the connection associate to the name.
	 * 
	 * @param nameCnx
	 *            name of the connection.
	 * @return Connection.
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractConncection> T get(String nameCnx) {
		Map<String, AbstractConncection> map = containerInstances;
		T conncection = (T) map.get(nameCnx);
		return conncection;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void rollbackAll() throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			for (Entry<String, AbstractConncection> entry : map.entrySet()) {
				if (!entry.getValue().isClosed()) {
					rollback(entry.getKey());
				}
			}
		}
	}

	public void closeAll() throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			for (Entry<String, AbstractConncection> entry : map.entrySet()) {
				if (!entry.getValue().isClosed()) {
					close(entry.getKey());
				}
			}
		}
	}

	public void commitAll() throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			for (Entry<String, AbstractConncection> entry : map.entrySet()) {
				if (!entry.getValue().isClosed()) {
					commit(entry.getKey());

				}
			}
		}
	}

	public void rollback(String nameCnx) throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			map.get(nameCnx).rollback();
			log.debug("Rollback Connection " + map.get(nameCnx).getName());
		}
	}

	public void close(String nameCnx) throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			map.get(nameCnx).close();
			log.debug("Close Connection " + map.get(nameCnx).getName());
		}
	}

	public void commit(String nameCnx) throws SQLException {
		Map<String, AbstractConncection> map = containerInstances;
		if (map != null) {
			map.get(nameCnx).commit();
			log.debug("Commit Connection " + map.get(nameCnx).getName());
		}
	}

	/**
	 * The main way to get the ManagerConnection.
	 * 
	 * @return
	 */
	public static ManagerConncection getInstance() {
		if (managerConncection == null) {
			managerConncection = new ManagerConncection();
		}
		return managerConncection;
	}

	/**
	 * @return
	 * 
	 */
	public Collection<AbstractConncection> getIteratorConnection() {
		return containerInstances.values();
	}

	public Result execute(Select select, String... connections)
			throws SQLException {
		Result result = new Result();
		for (String nameCnx : connections) {
			PreparedStatement preparedStatement = get(nameCnx).getConnection()
					.prepareStatement(select.getQuery());
			for (int index = 0; index < select.getParameters().length; index++) {
				preparedStatement.setObject(index + 1,
						select.getParameters()[0]);
				result.addResult(nameCnx, preparedStatement.executeUpdate());
			}
		}
		return result;
	}

}
