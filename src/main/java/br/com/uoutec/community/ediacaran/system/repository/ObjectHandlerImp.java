package br.com.uoutec.community.ediacaran.system.repository;


public class ObjectHandlerImp implements ObjectHandler{

	public ObjectHandlerImp() {
	}
	
	@Override
	public String getType() {
		return "json";
	}

	@Override
	public boolean accept(Object o) {
		return true;
	}

	@Override
	public boolean accept(String type) {
		return "json".equals(type);
	}
	
	@Override
	public Object toObject(Object data) {
		return data;
	}
	
	@Override
	public Object toData(Object object) {
		return object;
	}

}
