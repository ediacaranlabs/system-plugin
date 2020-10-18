package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TemplateVarParser  extends AbstractVarParser{

	private String template;
	
	private String packageName;
	
	private Theme tema;
	
	private Map<String, Object> vars;
	
	private ComponentVars componentVars;
	
	public TemplateVarParser(String template, String packageName, ComponentVars componentVars, Theme tema) {
		this.template = template;
		this.vars = new HashMap<String, Object>();
		this.packageName = packageName;
		this.tema = tema;
		this.componentVars = componentVars;
	}
	
	public TemplateVarParser clear() {
		vars.clear();
		return this;
	}
	
	public TemplateVarParser put(String key, Object value) {
		vars.put(key, value);
		return this;
	}

	public TemplateVarParser remove(String key) {
		vars.remove(key);
		return this;
	}
	
	@Override
	public void parse(Writer writter) throws TemaException {
		tema.applyTagTemplate(template, packageName, componentVars, vars, writter);
	}

	@Override
	public String parse() throws IOException {
		throw new UnsupportedOperationException();
	}
}
