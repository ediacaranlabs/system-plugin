package br.com.uoutec.community.ediacaran.system.tema;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface TemaRegistry extends PublicBean{

	void registerTemplate(String name, String packageName, String context, String template);
	
	void registerTemplate(String name, String packageName, String template, TagTemplate tagTemplate);
	
	Tema getCurrentTema();
	
	Tema getTema(String name);
	
	void unregisterTema(String name);
	
}
