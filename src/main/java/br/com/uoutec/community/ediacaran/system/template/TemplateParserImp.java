package br.com.uoutec.community.ediacaran.system.template;

import java.io.InputStream;

import br.com.uoutec.application.Configuration;
import br.com.uoutec.community.ediacaran.system.util.TemplateUtil;

public class TemplateParserImp implements TemplateParser {

	private TemplateUtil util;
	
	@Override
	public String parser(String path) throws TemplateException{
		return parser(path, null, "UTF-8");
	}
	
	@Override
	public String parserContent(String template, Configuration config) throws TemplateException{
		try {
			return util.parser(template, config);
		}
		catch(Throwable ex) {
			throw new TemplateException(ex);
		}
	}
	
	@Override
	public String parser(String path, String encoding) throws TemplateException{
		return parser(path, null, encoding);
	}
	
	@Override
	public String parser(String path, Configuration config, String encoding) throws TemplateException{
		try {
			return util.parser(path, config, encoding);
		}
		catch(Throwable ex) {
			throw new TemplateException(path, ex);
		}
	}

	@Override
	public InputStream getRaw(String path) throws TemplateException{
		try {
			return util.getRaw(path); 
		}
		catch(Throwable ex) {
			throw new TemplateException(path, ex);
		}
	}
	
}
