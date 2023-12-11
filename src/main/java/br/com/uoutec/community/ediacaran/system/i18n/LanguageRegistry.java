package br.com.uoutec.community.ediacaran.system.i18n;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface LanguageRegistry extends PublicBean{

	public static final String PERMISSION_PREFIX = "registry.lang.";
	
	public static final String ENTITIES_PACKAGE = "entities";
	
	public static final String TEMPLATE_PACKAGE = "templates";
	
	public static final String SEPARATOR        = "/";
	
	void registerResourceBundle(ResourceBundle resourceBundle, Locale e, String id);
	
	void unregisterResourceBundle(Locale e, String id);
	
	Locale getDefaultLocale();
	
	Locale[] getSupportedLocales();
	
	Map<Locale,String> getSupportedLocalesName();
	
	ResourceBundle getResourceBundle(String packageID, Locale locale);
	
    String getString(Class<?> type, String key);
    
    String getString(Class<?> type, String key, Locale locale);

	String getString(Class<?> type, String key, Object params[]);
	
    String getString(Class<?> type, String key, Object params[], Locale locale);

    String getString(String packageID, String key);
    
    String getString(String packageID, String key, Locale locale);
    
    String getString(String packageID, String key, Object params[]);
    
    String getString(String packageID, String key, Object params[], Locale locale);
    
}
