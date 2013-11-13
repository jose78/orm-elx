package com.rapid.arq.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author xe34068
 * 
 */

@Controller
public class RapidContext {

	@Autowired
	private ApplicationContext appContext;

	public Object getBean(String nameBean) {		
		return appContext.getBean(nameBean);
	}
}
