package com.rapid.arq.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author xe34068
 *
 */

@Controller
@Configuration 
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
	
	public Object getBean(String nameBean) {
		
		
		return context.getBean(nameBean);
	}	
}
