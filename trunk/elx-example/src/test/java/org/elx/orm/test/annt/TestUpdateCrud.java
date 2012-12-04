package org.elx.orm.test.annt;

import java.util.List;
import java.util.Queue;
import java.util.Set;


import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Criteria;
import org.elx.orm.db.Crud;
import org.elx.orm.db.SessionConncection;
import org.elx.orm.entity.annt.TblAddress;
import org.junit.Test;

public class TestUpdateCrud extends GenericTest {

	private static Logger log = Logger.getLogger(TestUpdateCrud.class);

	@Test
	public void testUpdate() throws Exception {
		try {
			List<TblAddress> lstResult= getCrud().find(TblAddress.class, 0, new Criteria());
			
			TblAddress entity= lstResult.get(0);
			entity.setCity("MOD CITY");
			entity.setName("MOD NAME");
						
			getCrud().update(entity);
			
			lstResult= getCrud().find(TblAddress.class, 0, new Criteria());
			 
			entity= lstResult.get(0);			 
			log.debug(entity);
			
			SessionConncection.getManagerConncection().closeAll();
		} catch (final Throwable e) {
			log.error(e.getMessage(), e);
			SessionConncection.getManagerConncection().rollbackAll();
		}
	}
	
	
	@Test
	public void createTestUpdateEntity() throws Exception {
		// OK
		try {
			TblAddress address= new TblAddress();
			address.setCity("CITY:madrid.");
			address.setName("NAME:PaLo BaJO.");
			address.setState("STATE:YO_misMO.");
			log.debug(address);
			new Crud().insert(address);
			log.debug(address);
			address.setCity("CITY:mod.");
			address.setName("NAME:mod.");
			address.setState("STATE:mod.");
			new Crud().update(address);
			
			log.debug(address);
			
			List<TblAddress> result= new Crud().find(TblAddress.class, 0, new Criteria(" entity.add_id_address = ?  ", address.getIdAddress()));
			for (TblAddress tblAddress : result) {
				log.debug(tblAddress);	
			}
			
			
			log.debug(address);
		} catch (final Throwable e) {
			SessionConncection.getManagerConncection().rollbackAll();
			log.error(e.getMessage(), e);
		}
		SessionConncection.getManagerConncection().commitAll();
	}
	
}
