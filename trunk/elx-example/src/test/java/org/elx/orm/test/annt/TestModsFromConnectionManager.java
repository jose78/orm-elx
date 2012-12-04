package org.elx.orm.test.annt;


import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Criteria;
import org.elx.orm.db.Result;
import org.elx.orm.db.Select;
import org.elx.orm.db.SessionConncection;
import org.elx.orm.entity.annt.TblAddress;
import org.elx.orm.entity.annt.TblCompany;
import org.elx.orm.entity.annt.TblEmployee;
import org.junit.Test;

public class TestModsFromConnectionManager extends GenericTest {

	private static Logger log = Logger.getLogger(TestModsFromConnectionManager.class);

	@Test
	public void testUpdate() throws Exception {
		try {

			Result result= SessionConncection.getManagerConncection().execute(new Select("update Address ...  ", 1,2,3), "derby_CNX" , "MYSQL_CNX");
			SessionConncection.getManagerConncection().commitAll();
		} catch (final Throwable e) {
			log.error(e.getMessage(), e);
			SessionConncection.getManagerConncection().rollbackAll();
			
		}
	}
	@Test
	public void testDelete() throws Exception {
		try {

			Result result= SessionConncection.getManagerConncection().execute(new Select("delte  Address ...  ", 1,2,3), "derby_CNX" , "MYSQL_CNX");
			SessionConncection.getManagerConncection().commitAll();
		} catch (final Throwable e) {
			log.error(e.getMessage(), e);
			SessionConncection.getManagerConncection().rollbackAll();
			
		}
	}	
}
