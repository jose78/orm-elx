package org.elx.orm.test.annt;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Select;
import org.elx.orm.entity.annt.TblCompany;
import org.elx.orm.select.Item;
import org.elx.orm.utils.ElxGenericException;
import org.junit.Test;

public class TestSelectCrud extends GenericTest {

	private static Logger log = Logger.getLogger(TestSelectCrud.class);

	// @Test
	// public void testSelectQuery() throws Exception {
	// //OK
	// try {
	// final Result<List<Company>> result = this
	// .getCrud()
	// .read(Company.class,
	// "SELECT c.* FROM schema_elx.Company c WHERE c.comp_id_company >  ? AND comp_id_company < ?",
	// 1, 1000);
	// for (Entry<String, List<Company>> entry : result.getResult()
	// .entrySet()) {
	// log.debug("--> " + entry.getKey());
	// for (Company auxDummyTo : entry.getValue()) {
	// TestSelectCrud.log.debug(auxDummyTo.toString());
	// }
	// }
	// } catch (final ElxGenericException e) {
	// TestSelectCrud.log.error(e.getMessage(), e);
	// } catch (final Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Test
	public void testSelectQueryPag() throws Exception {
		// OK
		try {
			Select select = new Select(
					"SELECT c.* FROM schema_elx.Company c WHERE c.comp_id_company >  ? AND comp_id_company < ?",
					1, 100).setPagination(3, 4);

			final List<TblCompany> result = this.getCrud().read(
					TblCompany.class, select);
			for (TblCompany value : result) {
				TestSelectCrud.log.debug(value.toString());
			}
		} catch (final ElxGenericException e) {
			TestSelectCrud.log.error(e.getMessage(), e);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectQueryPagItem() throws Exception {
		// OK
		try {
			Select select = new Select(
					"SELECT c.comp_id_company as id, c.COMP_NAME_COMPANY as name "
							+ " FROM Company c WHERE c.comp_id_company >  ? AND comp_id_company < ?",
					1, 100).setPagination(1, 15);

			final Set<org.elx.orm.select.Item> result = this.getCrud().read(Item.class, select);

			for (Item value : result) {
				TestSelectCrud.log.debug(value.toString());
			}
		} catch (final ElxGenericException e) {
			TestSelectCrud.log.error(e.getMessage(), e);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
