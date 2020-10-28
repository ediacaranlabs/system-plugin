package br.com.uoutec.community.ediacaran.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.brandao.brutos.ClassUtil;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.core.system.AbstractPluginInstaller;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginMetadata;
import br.com.uoutec.community.ediacaran.plugins.PluginPath;
import br.com.uoutec.community.ediacaran.system.tema.Component;
import br.com.uoutec.community.ediacaran.system.tema.ThemeRegistry;

public abstract class AbstractWebPluginInstaller 
	extends AbstractPluginInstaller
	implements WebPlugin{

	public void install() throws Throwable {
		super.install();
		loadThemes();
	}
	
	
	@SuppressWarnings("unchecked")
	protected void loadThemes() throws Throwable {
		
		ThemeRegistry themeRegistry = EntityContextPlugin.getEntity(ThemeRegistry.class);
		ContextManager contextManager = EntityContextPlugin.getEntity(ContextManager.class);
		
		PluginMetadata pmd = super.getMetadata();
		
		PluginPath pp = metadata.getPath();
		File base = pp.getBase();
		File packages = new File(base, "themes.properties");
		packages = packages.getAbsoluteFile();
		
		if(packages.exists() && !packages.isDirectory()) {
			Properties p = new Properties();
			
			try (InputStream i = new FileInputStream(packages)){
				p.load(i);
			}
			
			Enumeration<String> names = (Enumeration<String>) p.propertyNames();
			
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				String value = p.getProperty(name);
				
				String[] path = name.split("/");
				if(path.length == 2) {
					themeRegistry.registerTemplate(path[0], path[1], contextManager.getContext(pmd), value);
				}
				else
				if(path.length >= 3) {
					String[] tmp = Arrays.copyOfRange(path, 2, path.length);
					String template = "/" + String.join("/", tmp);
					Component c = (Component)ClassUtil.getInstance(value);
					themeRegistry.registerTemplate(path[0], path[1], template, c);
					
				}
			}
		}
		
	}
	
}
