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
	
	private ConcurrentMap<String, ThemeEntry> temas;
	
	private PluginData pluginData;
	
	public ThemeRegistryImp() {
	}
	
	@Inject
	public ThemeRegistryImp(PluginData pluginData) {
		this.temas = new ConcurrentHashMap<String, ThemeEntry>();
		this.pluginData = pluginData;
	}
	
	//registryTema("default", "front", "/plugins/ediacaran/front", "/default_template/front");
	@Override
	public synchronized void registerTemplate(String name, String packageName, String context, String template) throws ThemeException{
		//TODO: security
		
		ThemeEntry entry = temas.get(name);
		
		if(entry == null) {
			
			entry = new ThemeEntry();
			entry.name = name;
			entry.context = context;
			entry.packages = new ConcurrentHashMap<String, TemaPackage>();
			entry.tema = new ThemeImp(name, context, template, entry.packages);
			
			if(logger.isTraceEnabled()) {
				logger.trace("tema created: {}[template={}, context={}, package={}]", name, template, context);
			}
			
		}
		
		if(packageName != null) {
			
			if(entry.packages.containsKey(packageName)) {
				throw new ThemeException("tema package has been added: " + name + "/" + packageName);
			}
			
			TemaPackage temaPackage = new TemaPackage(packageName, template, new ConcurrentHashMap<String, Component>());
			entry.packages.put(packageName, temaPackage);
			
			if(logger.isTraceEnabled()) {
				logger.trace("tema added: {}[template={}, context={}, package={}]", name, template, context, packageName);
			}
			
		}
		
	}

	@Override
	public synchronized void registerTemplate(String name, String packageName, String template, Component tagTemplate) throws ThemeException{
		//TODO: security

		ThemeEntry entry = temas.get(name);
		
		if(entry == null) {
			throw new ThemeException("tema not found: " + name);
		}
		
		TemaPackage temaPackage = entry.packages.get(packageName);
		
		if(temaPackage == null) {
			throw new ThemeException("tema package not found: " + name + "/" + packageName);
		}
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		if(tagTemplates.put(template, tagTemplate) == null){
			
			if(logger.isTraceEnabled()) {
				logger.trace("added tag template: {}[template={}, package={}]", name, template, packageName);
			}
			
		}
		else
		if(logger.isTraceEnabled()) {
			logger.trace("overridden tag template: {}[template={}, package={}]", name, template, packageName);
		}
		
	}
	
	@Override
	public Theme getCurrentTema() {
		return getTema(pluginData.getPropertyValue("template"));
	}
	
	@Override
	public Theme getTema(String name) {
		
		ThemeEntry entry = temas.get(name);
		
		if(entry == null) {
			throw new ThemeException("tema not found: " + name);
		}
		
		return entry.tema;
	}

	@Override
	public void unregisterTema(String name) {
		//TODO: security
		
		if(temas.remove(name) != null) {
			if(logger.isWarnEnabled()) {
				logger.warn("tema {} removed", name);
			}
		}
		else
		if(logger.isTraceEnabled()) {
			logger.trace("tema {} not found", name);
		}
		
	}

	private static class ThemeEntry {
		
		public String name;
		
		public String context;
		
		public ConcurrentMap<String, TemaPackage> packages;
		
		public Theme tema;
		
	}

}
