package com.rapid.arq.server;

import com.rapid.arq.server.service.RenameContractService;
import com.rapid.arq.server.util.Constant;


public class FactoryService {
	
	
	private Boolean isStrategy(){
		return true;
	}

	
	@SuppressWarnings("unchecked")
	private <T> T getRanameContract(boolean isStrategy){
		StringBuilder nameBean= new StringBuilder();
		if(isStrategy){
			nameBean.append(Constant.BEAN_SERVICE_RENAME_STRATEGY);			
		}else{
			nameBean.append(Constant.BEAN_SERVICE_RENAME_TACTIC);
		}
		
		T service = (T) RapidContext.getBean(nameBean.toString());
		
		return service; 
	}
	
	
	public RenameContractService getRanameContract(){
		return (RenameContractService) RapidContext.getBean(Constant.BEAN_SERVICE_RENAME);
	}
	
}
