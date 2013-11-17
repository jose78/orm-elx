package com.rapid.arq.server.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value= BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class BuilderSessionInformation {

	private static Integer cont= 0;
	private boolean strategy;
	
	
	public BuilderSessionInformation() {
		cont++;
		strategy = cont % 2==0;
	}
	
	
	public boolean isStrategy() {
		return strategy;
	}	
	
}
