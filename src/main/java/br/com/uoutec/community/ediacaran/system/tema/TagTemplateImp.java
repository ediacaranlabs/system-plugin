package br.com.uoutec.community.ediacaran.system.tema;

import java.io.Writer;
import java.util.Map;
import java.util.Set;

import org.apache.jasper.compiler.AttributeParser;

public class TagTemplateImp implements TagTemplate {

	private StringPattern sp;

	private Set<String> attributes;
	
	private Set<String> emptyAttributes;
	
	private Map<String, AttributeParser> attributesParser;

	private Set<String> properties;

	private Map<String, AttributeParser> propertiesParse;
	
	public TagTemplateImp(String template, Set<String> attributes, Set<String> emptyAttributes,
			Map<String, AttributeParser> attributesParser, Set<String> properties,
			Map<String, AttributeParser> propertiesParse) {
		this.sp = new StringPattern(template);
		this.attributes = attributes;
		this.emptyAttributes = emptyAttributes;
		this.attributesParser = attributesParser;
		this.properties = properties;
		this.propertiesParse = propertiesParse;
	}

	@Override
	public void applyTagTemplate(Map<String, Object> vars, Writer out) throws TemaException {
		try {
			sp.toWriter(out, vars);
		}
		catch(Throwable e) {
			throw new TemaException(e);
		}
	}

	@Override
	public void applyTagTemplate(Writer out, Object... vars) throws TemaException {
		try {
			sp.toWriter(out, vars);
		}
		catch(Throwable e) {
			throw new TemaException(e);
		}
	}

	@Override
	public Set<String> getAttributes() {
		return attributes;
	}

	@Override
	public Set<String> getEmptyAttributes() {
		return emptyAttributes;
	}

	@Override
	public Map<String, AttributeParser> getAttributesParser() {
		return attributesParser;
	}

	@Override
	public Set<String> getProperties() {
		return properties;
	}

	@Override
	public Map<String, AttributeParser> getPropertiesParse() {
		return propertiesParse;
	}

}
