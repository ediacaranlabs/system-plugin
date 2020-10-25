package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class TemplateListVarParser  extends AbstractVarParser{

	private String template;
	
	private List<Object[]> vars;
	
	private String packageName;
	
	private ComponentVars componentVars;
	
	private Theme theme;
	
	public TemplateListVarParser(String template, String packageName, ComponentVars componentVars, Theme theme) {
		this(template, packageName, componentVars, theme, new ArrayList<Object[]>());
	}

	public TemplateListVarParser(String template, String packageName, ComponentVars componentVars, Theme theme, List<Object[]> vars) {
		this.template = template;
		this.vars = vars;
		this.packageName = packageName;
		this.componentVars = componentVars;
		this.theme = theme;
	}
	
	public TemplateListVarParser clear() {
		vars.clear();
		return this;
	}
	
	public TemplateListVarParser add(Object ... value) {
		vars.add(value);
		return this;
	}

	public TemplateListVarParser remove(Object ... value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void parse(Writer writter) throws ThemeException {
		for(Object[] o: vars) {
			theme.applyTagTemplate(template, packageName, writter, o);
		}
	}

	@Override
	public String parse() throws IOException {
		throw new UnsupportedOperationException();
	}
}
