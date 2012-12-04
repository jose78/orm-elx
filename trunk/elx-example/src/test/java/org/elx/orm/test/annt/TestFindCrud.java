package org.elx.orm.test.annt;

import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Criteria;
import org.elx.orm.entity.annt.TblCompany;
import org.junit.Test;

public class TestFindCrud extends GenericTest {

	private static Logger log = Logger.getLogger(TestFindCrud.class);

	@Test
	public void testFindQuery() throws Exception {

		try {
			Integer level = 3;
			final Set<TblCompany> result = this.getCrud()
					.find(TblCompany.class,
							level,
							new Criteria(" comp_id_company < 201 ")
									.setPagination(1, 1));

			for (TblCompany value : result) {
				log.debug(value.toString());
			}

		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
