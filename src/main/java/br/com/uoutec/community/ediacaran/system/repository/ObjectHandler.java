package br.com.uoutec.community.ediacaran.system.repository;

public interface ObjectHandler {

	String getType();
	
	Object toObject(Object data);
	
	Object toData(Object object);
	
	boolean accept(Object o);
	
	boolean accept(String type);
	
}
