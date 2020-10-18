package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import br.com.uoutec.community.ediacaran.front.AbstractVarParser;
import br.com.uoutec.community.ediacaran.front.TemplatesManager;
import br.com.uoutec.community.ediacaran.front.TemplatesManagerProvider;

public class TemplateListVarParser  extends AbstractVarParser{

	private String template;
	
	private List<Object[]> vars;
	
	public TemplateListVarParser(String template) {
		this(template, new ArrayList<Object[]>());
	}

	public TemplateListVarParser(String template, List<Object[]> vars) {
		this.template = template;
		this.vars = vars;
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
	public void parse(Writer writter) throws IOException {
		try {
			TemplatesManager tm = TemplatesManagerProvider.getTemplatesManager();
			for(Object[] o: vars) {
				tm.apply(template, writter, o);
			}
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String parse() throws IOException {
		throw new UnsupportedOperationException();
	}
}
