package br.com.uoutec.community.ediacaran.system.tema;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeImp implements Theme{

	private static final Logger logger = LoggerFactory.getLogger(Theme.class);
	
	public String name;
	
	public String context;
	
	public String path;
	
	public ConcurrentMap<String, TemaPackage> packages;
	

	public ThemeImp(String name, String context, String path, ConcurrentMap<String, TemaPackage> packages) {
		this.name = name;
		this.packages = packages;
		this.path = path;
	}

	@Override
	public void applyTagTemplate(String template, String packageName, ComponentVars componentVars, Map<String, Object> extVars, Writer out) throws ThemeException {
		
		TemaPackage temaPackage = getPackage(packageName);
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		Component p = tagTemplates.get(template);
		
		if(p == null) {
			throw new ThemeException("template not found: " + template);
		}
		
		Map<String, Object> vars = new HashMap<String, Object>();
		
		vars.putAll(
			componentVars.prepareVars(
				p.getPropertiesParse(), p.getProperties(), 
				p.getAttributesParser(), 
				p.getEmptyAttributes(), 
				p.getAttributes()
			)
		);
			
			
		if(extVars != null) {
			vars.putAll(extVars);
		}
		
		p.applyTagTemplate(vars, out);
	}

	@Override
	public void applyTagTemplate(String template, String packageName, Writer out, Object... vars) throws ThemeException {
		
		TemaPackage temaPackage = getPackage(packageName);
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		Component p = tagTemplates.get(template);
		
		if(p == null) {
			throw new ThemeException("template not found: " + template);
		}
		
		p.applyTagTemplate(out, vars);
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public String getTemplate(String name) {
		TemaPackage temaPackage = getPackage(name);
		return path + temaPackage.getPath();
	}

	/*
	@Override
	public Set<String> getAttributes(Object tag, String packageName) {
		return null;
	}

	@Override
	public Set<String> getEmptyAttributes(Object tag, String packageName) {
		return null;
	}

	@Override
	public Map<String, AttributeParser> getAttributesParser(Object tag, String packageName) {
		return null;
	}

	@Override
	public Set<String> getProperties(Object tag, String packageName) {
		return null;
	}

	@Override
	public Map<String, AttributeParser> getPropertiesParse(Object tag, String packageName) {
		return null;
	}
	 */
	
	private TemaPackage getPackage(String name) throws ThemeException {
		
		if(name == null) {
			name = "front";
		}
		
		TemaPackage temaPackage = packages.get(name);
		
		if(temaPackage == null) {
			
			temaPackage = packages.get("front");
			
			if(temaPackage == null) {
				throw new ThemeException("tema package not found: " + this.name + "/" + name);
			}
		}
		
		return temaPackage;
	}

	@Override
	public ConcurrentMap<String, PublicResource> getResourcesByType(String type, String packageName) {
		
		TemaPackage temaPackage = getPackage(packageName);
		
		ConcurrentMap<String, PublicResource> resourcesType = temaPackage.getResources().get(type);
		
		if(resourcesType == null && logger.isTraceEnabled()) {
			logger.trace("resource type {} not found [package={}, theme={}]", type, packageName, name);
		}
		
		return resourcesType;
	}
	
}
