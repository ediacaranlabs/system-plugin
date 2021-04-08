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
import br.com.uoutec.community.ediacaran.plugins.PluginConfigurationMetadata;
import br.com.uoutec.community.ediacaran.plugins.PluginPath;
import br.com.uoutec.community.ediacaran.system.theme.Component;
import br.com.uoutec.community.ediacaran.system.theme.ThemeException;
import br.com.uoutec.community.ediacaran.system.theme.ThemeRegistry;

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
		
		PluginConfigurationMetadata pmd = pluginConfiguration.getMetadata();
		
		PluginPath pp = pmd.getPath();
		File base = pp.getBase();
		base = new File(base, "themes");
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
				if(path.length == 1) {
					themeRegistry.registerTheme(path[0], contextManager.getContext(pmd), value);
				}
				
			}

			names = (Enumeration<String>) p.propertyNames();
			
			while(names.hasMoreElements()) {
				
				String name = names.nextElement();
				String value = p.getProperty(name);
				
				String[] path = name.split("/");
				
				if(path.length == 2) {
					themeRegistry.registerPackageTheme(path[0], path[1], value);
					
				}
			}
			
			
			names = (Enumeration<String>) p.propertyNames();

			while(names.hasMoreElements()) {
				
				String name = names.nextElement();
				String value = p.getProperty(name);
				
				String[] path = name.split("/");
				
				if(path.length == 3) {
					
					if("resources".equals(path[2])) {
						String[] resources = value.split("\\,");
						
						for(String resource: resources) {
							String rValue = p.getProperty(path[0] + "/" + path[1] + "/resources" + resource);
							
							if(rValue == null) {
								throw new ThemeException("resource not found: " + resource);
							}

							String[] tmp = resource.split("/");
							String resourceName = String.join("/", Arrays.copyOf(tmp, tmp.length - 1));
							String type = tmp[tmp.length - 1];
							themeRegistry.registerResource(path[0], path[1], resourceName, type, rValue);
							
						}
					}
					
				}
			}

			names = (Enumeration<String>) p.propertyNames();

			while(names.hasMoreElements()) {
				
				String name = names.nextElement();
				String value = p.getProperty(name);
				
				String[] path = name.split("/");
				
				if(path.length > 3) {
					
					if("tags".equals(path[2])) {
						String[] tmp = Arrays.copyOfRange(path, 3, path.length);
						String template = "/" + String.join("/", tmp);
						Component c = (Component)ClassUtil.getInstance(value);
						c.loadConfiguration();
						c.loadTemplate();
						themeRegistry.registerComponentTemplate(path[0], path[1], template, c);
					}
					
				}
			}			
			/*
			names = (Enumeration<String>) p.propertyNames();

			while(names.hasMoreElements()) {
				
				String name = names.nextElement();
				String value = p.getProperty(name);
				
				String[] path = name.split("/");
				
				if(path.length > 3) {
					
					if("resources".equals(path[2])) {
						String[] tmp = Arrays.copyOfRange(path, 3, path.length - 1);
						String resource = "/" + String.join("/", tmp);
						String type = path[path.length - 1];
						themeRegistry.registerResource(path[0], path[1], resource, type, value);
					}
					else
					if("tags".equals(path[2])) {
						String[] tmp = Arrays.copyOfRange(path, 3, path.length);
						String template = "/" + String.join("/", tmp);
						Component c = (Component)ClassUtil.getInstance(value);
						c.loadConfiguration();
						c.loadTemplate();
						themeRegistry.registerComponentTemplate(path[0], path[1], template, c);
					}
					
				}
			}
			*/
		}
		
	}
	
}
