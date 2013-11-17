package com.rapid.arq.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rapid.arq.server.service.RenameContractService;
import com.rapid.arq.server.util.BuilderSessionInformation;
import com.rapid.arq.server.util.Constant;

@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class FactoryService {

	@Autowired
	private RapidContext context;
	@Autowired
	private BuilderSessionInformation builderSessionInformation;

	public RenameContractService getRanameContract() {
		try {
			boolean isStrategy = builderSessionInformation.isStrategy();
			StringBuilder nameBean = new StringBuilder();
			if (isStrategy) {
				nameBean.append(Constant.BEAN_SERVICE_RENAME_STRATEGY);
			} else {
				nameBean.append(Constant.BEAN_SERVICE_RENAME_TACTIC);
			}

			RenameContractService service = (RenameContractService) context
					.getBean(nameBean.toString());

			return service;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
