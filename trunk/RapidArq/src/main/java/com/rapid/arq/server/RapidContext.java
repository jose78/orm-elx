package com.rapid.arq.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author xe34068
 *
 */
public class RapidContext {

	private static ApplicationContext context =  null;
	
	static{
		context = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	public static Object getBean(String nameBean) {
		return context.getBean(nameBean);
	}

}
