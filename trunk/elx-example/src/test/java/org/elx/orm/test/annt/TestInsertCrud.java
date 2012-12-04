package org.elx.orm.test.annt;


import org.apache.log4j.Logger;
import org.elx.orm.GenericTest;
import org.elx.orm.db.Crud;
import org.elx.orm.db.SessionConncection;
import org.elx.orm.entity.annt.Book;
import org.elx.orm.entity.annt.TblAddress;
import org.elx.orm.entity.annt.TblCompany;
import org.elx.orm.entity.annt.TblEmployee;
import org.elx.orm.utils.ElxGenericException;
import org.junit.Test;

public class TestInsertCrud extends GenericTest {

	private static Logger log = Logger.getLogger(TestInsertCrud.class);

	@Test
	public void testSimpleInsert() throws Exception {
		// OK

		
		try {
			for (int indexJ = 1; indexJ < 4; indexJ++) {
				final TblAddress addressComp = this.createAddress();
				this.getCrud().insert(addressComp);
				Long idAddresComp = addressComp.getIdAddress();
//				 Long idAddresComp= null;
				final TblCompany company = this.createCompany(idAddresComp);
				this.getCrud().insert(company);

				final long idCompany = company.getIdCompany();

				for (int index = 0; index < 3; index++) {

					final TblAddress addressEmp = this.createAddress();
					this.getCrud().insert(addressEmp);

					final long idAdd = addressEmp.getIdAddress();

					final TblEmployee employee = this.createEmployee(idCompany,idAdd);
					this.getCrud().insert(employee);
					log.debug(employee);
				}
			}
			SessionConncection.getManagerConncection().commitAll();
		} catch (ElxGenericException e) {
			e.printStackTrace();
			SessionConncection.getManagerConncection().rollbackAll();
		} catch (Exception e) {
			SessionConncection.getManagerConncection().rollbackAll();
			e.printStackTrace();
		}
	}

	@Test
	public void createTestCase() throws Exception {
		// OK
		try {

			for (int indexCompany = 0; indexCompany < 5; indexCompany++) {
				final TblAddress addressComp = this.createAddress();
				this.getCrud().insert(addressComp);
				final TblCompany company = this.createCompany(addressComp
						.getIdAddress());
				this.getCrud().insert(company);

				for (int indexEmployee = 0; indexEmployee < 5; indexEmployee++) {
					final TblAddress address = this.createAddress();
					this.getCrud().insert(address);
					final TblEmployee employee = this.createEmployee(
							company.getIdCompany(), address.getIdAddress());
					this.getCrud().insert(employee);
					log.debug("Employee:" + employee.toString());
					log.debug("Address:" + address.toString());
				}
				log.debug("Company:" + company.toString());
			}
		} catch (final Throwable e) {
			SessionConncection.getManagerConncection().rollbackAll();
			log.error(e.getMessage(), e);
		}
		SessionConncection.getManagerConncection().commitAll();
	}

	
	@Test
	public void createTestInstertEntity() throws Exception {
		// OK
		try {
			TblAddress address= new TblAddress();
			address.setCity("CITY:madrid.");
			address.setName("NAME:PaLo BaJO.");
			address.setState("STATE:YO_misMO.");
			log.debug(address);
			new Crud().insert(address);
			log.debug(address);
		} catch (final Throwable e) {
			SessionConncection.getManagerConncection().rollbackAll();
			log.error(e.getMessage(), e);
		}
		SessionConncection.getManagerConncection().commitAll();
	}
	
	
	@Test
	public void createTestInstertBook() throws Exception {
		// OK
		try {
			Book book = new Book();
			
			StringBuilder sb= new StringBuilder();
			sb.append("HELLO WORLD");
			
			book.setMirror_1(sb.toString().getBytes());
			book.setTittle("TEST");
			log.debug(book);
			new Crud().insert(book);
			log.debug(book);
		} catch (final Throwable e) {
			SessionConncection.getManagerConncection().rollbackAll();
			log.error(e.getMessage(), e);
		}
		SessionConncection.getManagerConncection().commitAll();
	}
	
}
