package br.com.uoutec.community.ediacaran.system.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.PluginPath;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

public class Plugini18nManager {

	@SuppressWarnings("unchecked")
	public void registerLanguages() throws IOException {
		
		I18nRegistry languageRegistry = EntityContextPlugin.getEntity(I18nRegistry.class);
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);
		
		PluginPath pp = pluginType
				.getConfiguration().getMetadata().getPath();
		
		Path base = pp.getBase().getPath("i18n");
		
		if(base.exists() && base.isDirectory()) {
			
			Path packages = base.getPath("language.properties");
			Properties p = new Properties();
			
			if(!packages.exists()) {
				return;
			}
			
			try (InputStream i = packages.openInputStream() /*new FileInputStream(packages)*/){
				p.load(i);
			}
			
			Enumeration<String> names = (Enumeration<String>) p.propertyNames();
			
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				String path = p.getProperty(name);
				
				loadLanguages(name, base.getPath(path), languageRegistry);
			}
		}

		
	}

	protected void loadLanguages(String packageID, Path path, I18nRegistry languageRegistry) throws IOException {
		
		if(!path.exists() || !path.isDirectory()) {
			return;
		}
		
		Path[] files = path.getFiles();
		
		for(Path f: files) {
			
			if(f.isDirectory()) {
				loadLanguages(packageID + "/" + f.getName(), f, languageRegistry);
			}
			else {
				String name = f.getName();
				
				String id;
				Locale lang;
				
				if(name.matches("^([a-z0-9]+([_-][a-z0-9]+)*)_([a-z]{2}_[A-Z]{2})\\.properties$")) {
					id   = name.substring(0, name.length() - 17);
					lang = Locale.forLanguageTag(name.substring(name.length() - 16, name.length() - 11).replace("_", "-"));
				}
				else
				if(name.matches("^([a-z0-9]+([_-][a-z0-9]+)*)\\.properties$")) {
					id = name.substring(0, name.length() - 11);
					lang = languageRegistry.getDefaultLocale();
				}
				else {
					continue;
				}
					
				try (InputStream i = f.openInputStream() /*new FileInputStream(f)*/){
					PropertyResourceBundle prb = new PropertyResourceBundle(i);
					languageRegistry.registerResourceBundle(prb, lang, packageID + "/" + id);
				}
				
			}
		}
	}
	
	public void unregisterLanguages() {
		
	}
	
}
