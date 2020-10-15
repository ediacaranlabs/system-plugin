package br.com.uoutec.community.ediacaran.system.tema;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface TemaRegistry extends PublicBean{

	void registerTemplate(String name, String template, String context, String packageName);
	
	void registerTemplate(String name, String template, String packageName, TagTemplate tagTemplate);
	
	Tema getCurrentTema();
	
	Tema getTema(String name);
	
	void unregisterTema(String name);
	
}
