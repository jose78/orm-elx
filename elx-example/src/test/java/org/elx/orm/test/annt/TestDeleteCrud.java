package org.elx.orm.test.annt;

import java.util.List;


import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Criteria;
import org.elx.orm.db.SessionConncection;
import org.elx.orm.entity.annt.TblAddress;
import org.junit.Test;

public class TestDeleteCrud extends GenericTest {

	private static Logger log = Logger.getLogger(TestDeleteCrud.class);

	@Test
	public void testDelete() throws Exception {
		try {
			List<TblAddress> lstResult= getCrud().find(TblAddress.class, 0, new Criteria());
			
			TblAddress entity= lstResult.get(0);
			
			getCrud().delete(entity);
			
			SessionConncection.getManagerConncection().closeAll();
		} catch (final Throwable e) {
			log.error(e.getMessage(), e);
			SessionConncection.getManagerConncection().rollbackAll();
		}
	}
}
