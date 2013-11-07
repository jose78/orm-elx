package com.rapid.arq.server;

import org.springframework.beans.factory.annotation.Configurable;
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
		try{
			context = new ClassPathXmlApplicationContext("/application-context.xml");
			System.out.println(context);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Object getBean(String nameBean) {
		return context.getBean(nameBean);
	}	
}
