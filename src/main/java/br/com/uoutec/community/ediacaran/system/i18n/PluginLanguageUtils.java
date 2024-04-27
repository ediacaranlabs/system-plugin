package br.com.uoutec.community.ediacaran.system.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.i18n.MessageBundleThread;

public class PluginLanguageUtils {

	private static final String LOCALE_STR = "([a-z]{2,2})_([A-Z]{2,2})";
	
	private static final Pattern localePattern = Pattern.compile(LOCALE_STR);
	
	private static final String MESSAGE_BUNDLE_PATTERN = "entities/{msg}";
	
    public static String getPluginResourceBundle(Class<?> type){
    	return MESSAGE_BUNDLE_PATTERN
    			.replace("{msg}", type.getName().replace(".", "/").toLowerCase());
    }
    
    public static Locale toLocale(String value) {
    	
    	if(value == null) {
    		return null;
    	}
    	
		Matcher m = localePattern.matcher(value);
		
		if(!m.matches()) {
			return null;
		}
    	
		m.reset().find();
		
		String lang = m.group(1);
		String country = m.group(2);
		
		return new Locale(lang, country);
    }
    
    private static final I18nRegistry getMessageBundle() {
    	return EntityContextPlugin.getEntity(I18nRegistry.class);
    }

    public static Locale getLocale() {
    	return MessageBundleThread.getLocale();
    }
	public static ResourceBundle getResourceBundle(String packageID, Locale locale) {
		return getMessageBundle().getResourceBundle(packageID, locale);
	}
	
	public static String getMessageResourceString(Class<?> type, String key) {
		return getMessageBundle().getString(type, key);
	}
    
	public static String getMessageResourceString(Class<?> type, String key, Locale locale) {
		return getMessageBundle().getString(type, key, locale);
	}

	public static String getMessageResourceString(Class<?> type, String key, Object params[]) {
		return getMessageBundle().getString(type, key, params);
	}
	
	public static String getMessageResourceString(Class<?> type, String key, Object params[], Locale locale) {
		return getMessageBundle().getString(type, key, params, locale);
	}

	public static String getMessageResourceString(String packageID, String key) {
		return getMessageBundle().getString(packageID, key);
	}
    
	public static String getMessageResourceString(String packageID, String key, Locale locale) {
		return getMessageBundle().getString(packageID, key, locale);
	}
    
	public static String getMessageResourceString(String packageID, String key, Object params[]) {
		return getMessageBundle().getString(packageID, key, params);
	}
    
	public static String getMessageResourceString(String packageID, String key, Object params[], Locale locale) {
		return getMessageBundle().getString(packageID, key, params, locale);
	}
    
}
