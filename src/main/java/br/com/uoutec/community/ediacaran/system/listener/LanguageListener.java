package br.com.uoutec.community.ediacaran.system.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.EdiacaranEventListener;
import br.com.uoutec.community.ediacaran.EdiacaranEventObject;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.plugins.PluginNode;
import br.com.uoutec.community.ediacaran.plugins.PluginPath;
import br.com.uoutec.community.ediacaran.system.i18n.LanguageRegistry;

@Singleton
public class LanguageListener implements EdiacaranEventListener{

	private LanguageRegistry languageRegistry;
	
	@Inject
	public LanguageListener(LanguageRegistry languageRegistry) {
		this.languageRegistry = languageRegistry;
	}
	
	@Override
	public void onEvent(EdiacaranEventObject event) {
	
		if(event.getSource() instanceof PluginInitializer) {
			
			if("installing".equals(event.getType())){
				startPlugin((PluginNode)event.getData());
			}
			else
			if("destroying".equals(event.getType())){
				stopPlugin((PluginNode)event.getData());
			}
			
		}
		
	}

	private void startPlugin(PluginNode node) {
		try {
			loadLanguages((Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
	}
	
	@SuppressWarnings("unchecked")
	protected void loadLanguages(Plugin plugin
			) throws FileNotFoundException, IOException {

		PluginPath pp = plugin.getConfiguration()
				.getMetadata().getPath();
		File base = new File(pp.getBase(), "i18n");
		
		if(base.exists() && base.isDirectory()) {
			File packages = new File(base, "language.properties");
			Properties p = new Properties();
			
			try (InputStream i = new FileInputStream(packages)){
				p.load(i);
			}
			
			Enumeration<String> names = (Enumeration<String>) p.propertyNames();
			
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				String path = p.getProperty(name);
				
				loadLanguages(name, new File(base, path), languageRegistry);
			}
		}
		
	}

	protected void loadLanguages(String packageID, File path, LanguageRegistry languageRegistry) throws FileNotFoundException, IOException {
		
		if(!path.exists() || !path.isDirectory()) {
			return;
		}
		
		File[] files = path.listFiles();
		
		for(File f: files) {
			
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
					
				try (InputStream i = new FileInputStream(f)){
					PropertyResourceBundle prb = new PropertyResourceBundle(i);
					languageRegistry.registerResourceBundle(prb, lang, packageID + "/" + id);
				}
				
			}
		}
	}
	
}