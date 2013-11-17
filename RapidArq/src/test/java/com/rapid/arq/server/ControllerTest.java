package com.rapid.arq.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rapid.arq.server.service.RenameContractService;
import com.spring.service.MyServiceImpl;

//@Configurable(autowire=Autowire.BY_TYPE)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml"})
public class ControllerTest {
	
	@Autowired
	private MyServiceImpl serviceImpl;
	
	@Autowired
	private FactoryService factoryService22;
	
	@Test
	public void testRename() throws Exception {
		
		RenameContractService service =factoryService22.getRanameContract();
		factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);factoryService22.getRanameContract().renameContract(null);
		factoryService22.getRanameContract().renameContract(null);
		
	}		
	
	@Test
	public void testService() throws Exception {
		System.out.println(serviceImpl.getRepository() + serviceImpl.getRepositoryStr());
		System.out.println(serviceImpl.getRepository() + serviceImpl.getRepositoryStr());
		System.out.println(serviceImpl.getRepository() + serviceImpl.getRepositoryStr());
		System.out.println(serviceImpl.getRepository() + serviceImpl.getRepositoryStr());
		
	}
	
}
