package com.rapid.arq.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapid.arq.server.service.RenameContractService;
import com.rapid.arq.server.util.Constant;

@Component
public class FactoryService {
	
	@Autowired
	private RapidContext context;
	
	
	private boolean  strategy = false; 
	
	public void setStrategy(boolean strategy) {
		this.strategy = strategy;
	}
	
	private Boolean isStrategy(){
		return strategy;
	}

	
	public boolean executeDetectoTypeUser(){
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	private  <T> T getRename(){
		boolean isStrategy= false;
		StringBuilder nameBean= new StringBuilder();
		if(isStrategy){
			nameBean.append(Constant.BEAN_SERVICE_RENAME_STRATEGY);			
		}else{
			nameBean.append(Constant.BEAN_SERVICE_RENAME_TACTIC);
		}
		
		T service = (T) context.getBean(nameBean.toString());
		
		return service; 
	}
	
	
	public RenameContractService getRanameContract(){
		return 
//				getServiceRename2();
				(RenameContractService) context.getBean(Constant.BEAN_SERVICE_RENAME);
	}
	
}
