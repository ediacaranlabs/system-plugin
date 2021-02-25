package br.com.uoutec.community.ediacaran.system.tema;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.community.ediacaran.plugins.PluginData;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class ThemeRegistryImp implements ThemeRegistry, PublicBean{

	private static final Logger logger = LoggerFactory.getLogger(ThemeRegistry.class);
	
	private ConcurrentMap<String, ThemeEntry> themes;
	
	private PluginData pluginData;
	
	public ThemeRegistryImp() {
	}
	
	@Inject
	public ThemeRegistryImp(PluginData pluginData) {
		this.themes = new ConcurrentHashMap<String, ThemeEntry>();
		this.pluginData = pluginData;
	}
	
	@Override
	public synchronized void registerTheme(String name, String context, String template) throws ThemeException{
		//TODO: security
		
		ThemeEntry entry = themes.get(name);
		
		if(entry != null) {
			throw new ThemeException("theme has been added: " + name);
			
		}	

		entry = new ThemeEntry();
		entry.name = name;
		entry.context = context;
		entry.packages = new ConcurrentHashMap<String, TemaPackage>();
		entry.tema = new ThemeImp(name, context, template, entry.packages);
		
		if(logger.isTraceEnabled()) {
			logger.trace("thema created: {}[template={}, context={}]", name, template, context);
		}
		
		themes.put(name, entry);
		
		
	}

	@Override
	public synchronized void registerPackageTheme(String name, String packageName, String template) throws ThemeException{
		//TODO: security
		
		ThemeEntry entry = themes.get(name);
		
		if(entry == null) {
			throw new ThemeException("theme not found: " + name);
		}
		
		if(packageName == null) {
			throw new ThemeException("theme package not found: " + name + "/" + packageName);
		}
			
		if(entry.packages.containsKey(packageName)) {
			throw new ThemeException("theme package has been added: " + name + "/" + packageName);
		}
		
		TemaPackage temaPackage = new TemaPackage(
				packageName, 
				template, 
				new ConcurrentHashMap<String, Component>(),
				new ConcurrentHashMap<String, ConcurrentMap<String, PublicResource>>());
		
		entry.packages.put(packageName, temaPackage);
		
		if(logger.isTraceEnabled()) {
			logger.trace("package added: {}[theme={}, template={}]", packageName, name, template);
		}
			
		
	}
	
	@Override
	public synchronized void registerComponentTemplate(String name, String packageName, String template, Component tagTemplate) throws ThemeException{
		//TODO: security

		ThemeEntry entry = themes.get(name);
		
		if(entry == null) {
			throw new ThemeException("theme not found: " + name);
		}
		
		TemaPackage temaPackage = entry.packages.get(packageName);
		
		if(temaPackage == null) {
			throw new ThemeException("theme package not found: " + name + "/" + packageName);
		}
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		if(tagTemplates.put(template, tagTemplate) == null){
			
			if(logger.isTraceEnabled()) {
				logger.trace("added component template: {}[template={}, package={}]", name, template, packageName);
			}
			
		}
		else
		if(logger.isTraceEnabled()) {
			logger.trace("overridden component template: {}[template={}, package={}]", name, template, packageName);
		}
		
	}

	@Override
	public synchronized void registerResource(String name, String packageName, String resource, String type, String path) throws ThemeException{
		//TODO: security

		ThemeEntry entry = themes.get(name);
		
		if(entry == null) {
			throw new ThemeException("theme not found: " + name);
		}
		
		TemaPackage temaPackage = entry.packages.get(packageName);
		
		if(temaPackage == null) {
			throw new ThemeException("theme package not found: " + name + "/" + packageName);
		}
		
		ConcurrentMap<String, ConcurrentMap<String, PublicResource>> map = temaPackage.getResources();
		
		ConcurrentMap<String, PublicResource> resources = map.get(type);
		
		if(resources == null) {
			resources = new ConcurrentHashMap<String, PublicResource>();
			map.put(type, resources);
		}
		
		if(resources.put(resource, new PublicResource(resource, path)) == null){
			
			if(logger.isTraceEnabled()) {
				logger.trace("added resource: {}[resource={}, package={}]", name, resource, packageName);
			}
			
		}
		else
		if(logger.isTraceEnabled()) {
			logger.trace("overridden resource: {}[template={}, package={}]", name, resource, packageName);
		}
		
	}
	
	@Override
	public Theme getCurrentTheme() {
		return getTheme(pluginData.getPropertyValue("theme"));
	}
	
	@Override
	public Theme getTheme(String name) {
		
		ThemeEntry entry = themes.get(name);
		
		if(entry == null) {
			throw new ThemeException("theme not found: " + name);
		}
		
		return entry.tema;
	}

	@Override
	public void unregisterTema(String name) {
		//TODO: security
		
		if(themes.remove(name) != null) {
			if(logger.isWarnEnabled()) {
				logger.warn("theme {} removed", name);
			}
		}
		else
		if(logger.isTraceEnabled()) {
			logger.trace("theme {} not found", name);
		}
		
	}

	private static class ThemeEntry {
		
		public String name;
		
		public String context;
		
		public ConcurrentMap<String, TemaPackage> packages;
		
		public Theme tema;
		
	}

}
