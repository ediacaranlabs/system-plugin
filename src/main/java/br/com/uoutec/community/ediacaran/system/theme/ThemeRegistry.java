package br.com.uoutec.community.ediacaran.system.theme;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface ThemeRegistry extends PublicBean{

	void registerTheme(String name, String context, String template) throws ThemeException;
	
	void registerPackageTheme(String name, String packageName, String template) throws ThemeException;
	
	void registerComponentTemplate(String name, String packageName, String template, Component tagTemplate) throws ThemeException;
	
	void registerResource(String name, String packageName, String resource, String type, String path) throws ThemeException;
	
	Theme getCurrentTheme();
	
	Theme getTheme(String name);
	
	void unregisterTheme(String name);
	
}
