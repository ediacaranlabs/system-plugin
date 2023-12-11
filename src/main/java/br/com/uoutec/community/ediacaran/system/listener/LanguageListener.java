package br.com.uoutec.community.ediacaran.system.listener;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.community.ediacaran.system.i18n.LanguageRegistry;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.Plugin;
import br.com.uoutec.ediacaran.core.plugins.PluginInitializer;
import br.com.uoutec.ediacaran.core.plugins.PluginNode;
import br.com.uoutec.ediacaran.core.plugins.PluginPath;

@Singleton
public class LanguageListener implements EdiacaranEventListener{

	private static final Logger logger = LoggerFactory.getLogger(LanguageListener.class);

	private LanguageRegistry languageRegistry;
	
	@Inject
	public LanguageListener(LanguageRegistry languageRegistry) {
		this.languageRegistry = languageRegistry;
	}
	
	@Override
	public void onEvent(EdiacaranEventObject event) {
	
		if(event.getSource() instanceof PluginInitializer) {
			
			if("installing".equals(event.getType())){
				ContextSystemSecurityCheck.doPrivileged(()->{
					startPlugin((PluginNode)event.getData());
					return null;
				});
			}
			else
			if("destroying".equals(event.getType())){
				ContextSystemSecurityCheck.doPrivileged(()->{
					stopPlugin((PluginNode)event.getData());
					return null;
				});
			}
			
		}
		
	}

	private void startPlugin(PluginNode node) {
		try {
			loadLanguages((Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(AccessControlException ex) {
			logger.warn("don't have permission to load language", ex);
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
	}
	
	@SuppressWarnings("unchecked")
	protected void loadLanguages(Plugin plugin
			) throws IOException {

		PluginPath pp = plugin.getConfiguration()
				.getMetadata().getPath();
		Path base = pp.getBase().getPath("i18n");
		
		if(base.exists() && base.isDirectory()) {
			Path packages = base.getPath("language.properties");
			Properties p = new Properties();
			
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

	protected void loadLanguages(String packageID, Path path, LanguageRegistry languageRegistry) throws IOException {
		
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
	
}
