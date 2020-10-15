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
public class TemaRegistryImp implements TemaRegistry, PublicBean{

	private static final Logger logger = LoggerFactory.getLogger(TemaRegistry.class);
	
	private ConcurrentMap<String, TemaEntry> temas;
	
	private PluginData pluginData;
	
	@Inject
	public TemaRegistryImp(PluginData pluginData) {
		this.temas = new ConcurrentHashMap<String, TemaEntry>();
		this.pluginData = pluginData;
	}
	
	//registryTema("default", "front", "/default_template/front", "/plugins/ediacaran/front");
	//registryTema("default/front", "/default_template/front", "/plugins/ediacaran/front");
	@Override
	public synchronized void registerTemplate(String name, String template, String context, String packageName) throws TemaException{
		//TODO: security
		
		TemaEntry entry = temas.get(name);
		
		if(entry == null) {
			
			entry = new TemaEntry();
			entry.name = name;
			entry.path = template;
			entry.context = context;
			entry.packages = new ConcurrentHashMap<String, TemaPackage>();
			entry.tema = new TemaImp(name, context, template, entry.packages);
			
			if(logger.isTraceEnabled()) {
				logger.trace("tema created: {}[template={}, context={}, package={}]", name, template, context);
			}
			
		}
		
		if(packageName != null) {
			
			if(entry.packages.containsKey(packageName)) {
				throw new TemaException("tema package has been added: " + name + "/" + packageName);
			}
			
			TemaPackage temaPackage = new TemaPackage(packageName, "/" + packageName, new ConcurrentHashMap<String, TagTemplate>());
			entry.packages.put(packageName, temaPackage);
			
			if(logger.isTraceEnabled()) {
				logger.trace("tema added: {}[template={}, context={}, package={}]", name, entry.path, context, packageName);
			}
			
		}
		
	}

	@Override
	public synchronized void registerTemplate(String name, String template, String packageName, TagTemplate tagTemplate) throws TemaException{
		//TODO: security

		TemaEntry entry = temas.get(name);
		
		if(entry == null) {
			throw new TemaException("tema not found: " + name);
		}
		
		TemaPackage temaPackage = entry.packages.get(packageName);
		
		if(temaPackage == null) {
			throw new TemaException("tema package not found: " + name + "/" + packageName);
		}
		
		ConcurrentMap<String, TagTemplate> tagTemplates = temaPackage.getTagTemplates();
		
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
	public Tema getCurrentTema() {
		return getTema(pluginData.getPropertyValue("template"));
	}
	
	@Override
	public Tema getTema(String name) {
		
		TemaEntry entry = temas.get(name);
		
		if(entry == null) {
			throw new TemaException("tema not found: " + name);
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

	private static class TemaEntry {
		
		public String name;
		
		public String context;
		
		public String path;
		
		public ConcurrentMap<String, TemaPackage> packages;
		
		public Tema tema;
		
	}

}