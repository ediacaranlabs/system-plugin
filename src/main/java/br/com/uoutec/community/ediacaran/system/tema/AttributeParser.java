package br.com.uoutec.community.ediacaran.system.tema;

public interface AttributeParser {

	String toName(String value, Object component);
	
	Object toValue(Object value, Object component);
	
}
