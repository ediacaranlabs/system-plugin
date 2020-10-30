package br.com.uoutec.community.ediacaran.system.tema;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface ThemeRegistry extends PublicBean{

	void registerTheme(String name, String context, String template) throws ThemeException;
	
	void registerPackageTheme(String name, String packageName, String template) throws ThemeException;
	
	void registerComponentTemplate(String name, String packageName, String template, Component tagTemplate) throws ThemeException;
	
	Theme getCurrentTema();
	
	Theme getTema(String name);
	
	void unregisterTema(String name);
	
}
