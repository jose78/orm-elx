package com.spring.repository;

public class JpaDao {

	private static int cont= 0;
	
	public String getInfo(){
		return "Esto es una salida: ";
	}	
	
	
	public void  getInit(){
		System.out.println("getInit");
	}
	
	
	public void getDestroy(){
		System.out.println("getDestroy");
	}
	
	
	@Override
	public String toString() {	
		cont++;
		return cont + " JpaDao dice:";
	}
	
}
