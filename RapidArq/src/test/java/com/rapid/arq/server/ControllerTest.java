package com.rapid.arq.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rapid.arq.server.service.RenameContractService;

//@Configurable(autowire=Autowire.BY_TYPE)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml"})
public class ControllerTest {
	
	@Autowired
	private FactoryService factoryService;
	
	@Test
	public void testRename() throws Exception {
		
		RenameContractService service =factoryService.getRanameContract();
		service.renameContract(null);
		
	}		
}
