package com.spring.service;

import com.spring.repository.JpaDao;

public class MyServiceImpl {
	
	private JpaDao repository;
	private String repositoryStr;
	
	
	public void setRepositoryStr(String repositoryStr) {
		this.repositoryStr = repositoryStr;
	}
	
	public void setRepository(JpaDao repository) {
		
		this.repository = repository;
	}

	public JpaDao getRepository() {
		return repository;
	}
	
	public String getRepositoryStr() {
		return repositoryStr;
	}
	
}
