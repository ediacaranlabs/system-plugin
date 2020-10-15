package br.com.uoutec.community.ediacaran.system.tema;

import java.io.Writer;
import java.util.Map;
import java.util.Set;

import org.apache.jasper.compiler.AttributeParser;

public interface Tema {
	
	void applyTagTemplate(String template, String packageName, Map<String,Object> vars, Writer out) throws TemaException;
	
	void applyTagTemplate(String template, String packageName, Writer out, Object ... vars) throws TemaException;
	
	String getContext();
	
	String getTemplate(String packageName);
	
	Set<String> getAttributes(Object tag, String packageName);
	
	Set<String> getEmptyAttributes(Object tag, String packageName);
	
	Map<String, AttributeParser> getAttributesParser(Object tag, String packageName);

	Set<String> getProperties(Object tag, String packageName);
	
	Map<String, AttributeParser> getPropertiesParse(Object tag, String packageName);
	
}
