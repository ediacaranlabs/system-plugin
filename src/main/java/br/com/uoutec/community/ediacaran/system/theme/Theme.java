package br.com.uoutec.community.ediacaran.system.theme;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface Theme {
	
	void applyTagTemplate(String template, String packageName, ComponentVars componentVars, Map<String, Object> vars, Writer out) throws ThemeException;
	
	void applyTagTemplate(String template, String packageName, Writer out, Object ... vars) throws ThemeException;
	
	String getBasePath();
	
	String getContext();
	
	String getTemplate(String packageName);
	/*
	Set<String> getAttributes(Object tag, String packageName);
	
	Set<String> getEmptyAttributes(Object tag, String packageName);
	
	Map<String, AttributeParser> getAttributesParser(Object tag, String packageName);

	Set<String> getProperties(Object tag, String packageName);
	
	Map<String, AttributeParser> getPropertiesParse(Object tag, String packageName);
	*/
	
	List<PublicResource> getResourcesByType(String type, String packageName);
	
}
