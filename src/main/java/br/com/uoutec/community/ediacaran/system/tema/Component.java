package br.com.uoutec.community.ediacaran.system.tema;

import java.io.Writer;
import java.util.Map;
import java.util.Set;

public interface Component {

	void applyTagTemplate(Map<String,Object> vars, Writer out) throws ThemeException;
	
	void applyTagTemplate(Writer out, Object ... vars) throws ThemeException;
	
	String getTemplate();
	
	Set<String> getAttributes();
	
	Set<String> getEmptyAttributes();
	
	Map<String, AttributeParser> getAttributesParser();

	Set<String> getProperties();
	
	Map<String, AttributeParser> getPropertiesParse();
	
}
