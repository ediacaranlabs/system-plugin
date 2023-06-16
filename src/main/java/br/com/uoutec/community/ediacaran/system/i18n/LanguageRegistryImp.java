package br.com.uoutec.community.ediacaran.system.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PluginProperty;
import br.com.uoutec.community.ediacaran.plugins.PluginPropertyOption;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.i18n.MessageBundleConfiguration;
import br.com.uoutec.i18n.MessageBundleImp;

@Singleton
public class LanguageRegistryImp implements LanguageRegistry{

	private MessageBundleConfiguration languageManagerConfiguration;
	
	@Inject
	private PluginType pluginType;
	
	public LanguageRegistryImp() {
		this.languageManagerConfiguration = new MessageBundleImp() {
			
			public Locale getDefaultLocale() {
				return LanguageRegistryImp.this.getDefaultLocale();
			}


			public Locale[] getSupportedLocales() {
				return LanguageRegistryImp.this.getSupportedLocales();
			}

		};
	}
	
	@Override
	public void registerResourceBundle(ResourceBundle resourceBundle, Locale e, String id) {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			String packID = id.replaceAll("\\/", ".");
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + packID + "." + e.toString() + ".register"));
		}
		
		languageManagerConfiguration.installI18nResource(resourceBundle, e, id);
	}

	@Override
	public void unregisterResourceBundle(Locale e, String id) {
		
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			String packID = id.replaceAll("\\/", ".");
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + packID + "." + e.toString() + ".unregister"));
		}

		languageManagerConfiguration.uninstallI18nResource(e, id);
	}

	@Override
	public Locale getDefaultLocale() {
		String value = pluginType.getConfiguration().getString("default_locales");
		return value == null? Locale.getDefault() : PluginLanguageUtils.toLocale(value);
	}

	@Override
	public Locale[] getSupportedLocales() {
		
		List<String> strs = 
				pluginType.getConfiguration().getStrings("locales");
		
		if(strs == null){
			return null;
		}
		
		List<Locale> locales = new ArrayList<Locale>(); 
		
		strs.forEach(e->{
			locales.add(PluginLanguageUtils.toLocale(e)); 
		});
		
		return locales.stream().toArray(Locale[]::new);
	}

	@Override
	public Map<Locale,String> getSupportedLocalesName() {
		
		PluginProperty localesProperty = pluginType.getConfiguration().getPropertyMetadata("locales");
		List<PluginPropertyOption> locales = localesProperty.getOptions();

		Map<Locale, String> map = new HashMap<Locale, String>();
		
		locales.forEach(e->{
			map.put(PluginLanguageUtils.toLocale(e.getValue()), e.getDescription());
		});
		
		return map;
	}
	
	@Override
	public ResourceBundle getResourceBundle(String packageID, Locale locale) {
		return languageManagerConfiguration.getResourceBundle(packageID, locale);
	}

	@Override
	public String getString(Class<?> type, String key) {
		return languageManagerConfiguration.getMessageResourceString(type, key);
	}

	@Override
	public String getString(Class<?> type, String key, Locale locale) {
		return languageManagerConfiguration.getMessageResourceString(type, key, locale);
	}

	@Override
	public String getString(Class<?> type, String key, Object[] params) {
		return languageManagerConfiguration.getMessageResourceString(type, key, params);
	}

	@Override
	public String getString(Class<?> type, String key, Object[] params, Locale locale) {
		return languageManagerConfiguration.getMessageResourceString(type, key, params, locale);
	}

	@Override
	public String getString(String packageID, String key) {
		return languageManagerConfiguration.getMessageResourceString(packageID, key);
	}

	@Override
	public String getString(String packageID, String key, Locale locale) {
		return languageManagerConfiguration.getMessageResourceString(packageID, key, locale);
	}

	@Override
	public String getString(String packageID, String key, Object[] params) {
		return languageManagerConfiguration.getMessageResourceString(packageID, key, params);
	}

	@Override
	public String getString(String packageID, String key, Object[] params, Locale locale) {
		return languageManagerConfiguration.getMessageResourceString(packageID, key, params, locale);
	}

}