package br.com.uoutec.community.ediacaran.system.tema;

import java.io.Writer;
import java.util.Map;
import java.util.Set;

import org.apache.jasper.compiler.AttributeParser;

public interface TagTemplate {

	void applyTagTemplate(Map<String,Object> vars, Writer out) throws TemaException;
	
	void applyTagTemplate(Writer out, Object ... vars) throws TemaException;
	
	Set<String> getAttributes();
	
	Set<String> getEmptyAttributes();
	
	Map<String, AttributeParser> getAttributesParser();

	Set<String> getProperties();
	
	Map<String, AttributeParser> getPropertiesParse();
	
}
