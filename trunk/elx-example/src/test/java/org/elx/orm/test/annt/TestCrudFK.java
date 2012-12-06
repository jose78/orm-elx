package org.elx.orm.test.annt;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.annotations.Table;
import org.elx.orm.db.AbstractConncection;
import org.elx.orm.db.SessionConncection;
import org.junit.Test;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class TestCrudFK extends GenericTest {

	private static Logger log = Logger.getLogger(TestCrudFK.class);

	private Class<?> getClass(String nameClass) {
		Class<?> claz = null;
		try {
			claz = Class.forName(nameClass);
		} catch (ClassNotFoundException e) {
			System.err.println("-->" + nameClass);
			// e.printStackTrace();
		}
		return claz;
	}

	@Test
	public void testDeleteMasive() throws Exception {

		List<String> nameTables = new ArrayList<String>();

		URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan
															// java.class.path
		AnnotationDB db = new AnnotationDB();
		db.scanArchives(urls);
		for (String value : db.getAnnotationIndex().get(Table.class.getName())) {
			Class<?> clazz = getClass(value);

			Table anntTbl = clazz.getAnnotation(Table.class);
			String schema = anntTbl.schema().length() > 0 ? anntTbl.schema()
					+ "." : "";
			String table = anntTbl.name();
			nameTables.add(schema + table);

		}

		String querDropTbl = "DROP TABLE ";
		try {
			for (String nameTable : nameTables) {

				AbstractConncection absConnection = SessionConncection
						.getManagerConncection().get("MySQL_CNX");
				java.sql.PreparedStatement pst = absConnection.getConnection()
						.prepareStatement(querDropTbl + nameTable);
				pst.execute();

			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			SessionConncection.getManagerConncection().rollbackAll();
		}

		log.debug("OK DROP.");
		SessionConncection.getManagerConncection().commitAll();
	}
}
