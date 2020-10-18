package br.com.uoutec.community.ediacaran.system.tema;

import java.util.Map;
import java.util.Set;

public interface ComponentVars {

	Map<String, Object> prepareVars(Map<String, AttributeParser> propertyParsers, Set<String> defaultProperties,
			Map<String, AttributeParser> attributeParsers, Set<String> emptyAttributes, Set<String> defaultAttributes);
}
