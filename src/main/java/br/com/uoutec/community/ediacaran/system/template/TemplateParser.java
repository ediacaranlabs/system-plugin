package br.com.uoutec.community.ediacaran.system.template;

import java.io.InputStream;

import br.com.uoutec.application.Configuration;

public interface TemplateParser {

	String parser(String path) throws TemplateException;
	
	String parser(String path, String encoding) throws TemplateException;
	
	String parser(String path, Configuration config, String encoding) throws TemplateException;

	InputStream getRaw(String path) throws TemplateException;
	
}
