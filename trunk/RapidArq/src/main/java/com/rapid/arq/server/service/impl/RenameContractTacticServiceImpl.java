package com.rapid.arq.server.service.impl;

import org.springframework.stereotype.Service;

import com.rapid.arq.server.dto.ContractDto;
import com.rapid.arq.server.service.RenameContractService;

@Service
public class RenameContractTacticServiceImpl implements RenameContractService{

	public boolean renameContract(ContractDto ContractDto) {
		System.out.println("RenameContractTacticServiceImpl");
		return false;
	}

}