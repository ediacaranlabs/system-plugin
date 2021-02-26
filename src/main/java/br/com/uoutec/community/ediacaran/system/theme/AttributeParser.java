package br.com.uoutec.community.ediacaran.system.theme;

public interface AttributeParser {

	String toName(String value, Object component);
	
	Object toValue(Object value, Object component);
	
}
