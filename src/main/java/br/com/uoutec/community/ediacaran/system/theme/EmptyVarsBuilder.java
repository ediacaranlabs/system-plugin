package br.com.uoutec.community.ediacaran.system.theme;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class EmptyVarsBuilder implements ComponentVars{

	@SuppressWarnings("unchecked")
	public static final Map<String, Object> vars = Collections.EMPTY_MAP;
	
	public EmptyVarsBuilder() {
	}
	
	public Map<String, Object> prepareVars(Map<String, AttributeParser> propertyParsers, Set<String> defaultProperties,
			Map<String, AttributeParser> attributeParsers, Set<String> emptyAttributes, Set<String> defaultAttributes){
		return vars;
	}
	
}
