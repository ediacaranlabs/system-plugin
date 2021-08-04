package br.com.uoutec.community.ediacaran.system.theme;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeImp implements Theme{

	private static final Logger logger = LoggerFactory.getLogger(Theme.class);
	
	public String name;
	
	public String context;
	
	public String path;
	
	public ConcurrentMap<String, ThemePackage> packages;
	

	public ThemeImp(String name, String context, String path, ConcurrentMap<String, ThemePackage> packages) {
		this.name = name;
		this.packages = packages;
		this.path = path;
		this.context = context;
	}

	@Override
	public void applyTagTemplate(String template, String packageName, ComponentVars componentVars, Map<String, Object> extVars, Writer out) throws ThemeException {
		
		ThemePackage temaPackage = getPackage(packageName);
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		Component p = tagTemplates.get(template);
		
		if(p == null) {
			throw new ThemeException("template not found: " + template);
		}
		
		Map<String, Object> vars = new HashMap<String, Object>();

		try {
			vars.putAll(
				componentVars.prepareVars(
					p.getPropertiesParse(), p.getProperties(), 
					p.getAttributesParser(), 
					p.getEmptyAttributes(), 
					p.getAttributes()
				)
			);
		}
		catch(Throwable ex) {
			throw new ThemeException("unable to get tag properties: " + template + "[package=" + temaPackage.getName() + "]", ex);
		}
			
			
		if(extVars != null) {
			vars.putAll(extVars);
		}

		try {
			p.applyTagTemplate(vars, out);
		}
		catch(Throwable ex) {
			throw new ThemeException("unable to load template tag: " + template + "[package=" + temaPackage.getName() + "]", ex);
		}
	}

	@Override
	public void applyTagTemplate(String template, String packageName, Writer out, Object... vars) throws ThemeException {
		
		ThemePackage temaPackage = getPackage(packageName);
		
		ConcurrentMap<String, Component> tagTemplates = temaPackage.getTagTemplates();
		
		Component p = tagTemplates.get(template);
		
		if(p == null) {
			throw new ThemeException("template not found: " + template);
		}
		
		p.applyTagTemplate(out, vars);
	}

	@Override
	public String getBasePath() {
		return path;
	}
	
	@Override
	public String getContext() {
		return context;
	}

	@Override
	public String getTemplate(String name) {
		ThemePackage temaPackage = getPackage(name);
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
	
	private ThemePackage getPackage(String name) throws ThemeException {
		
		if(name == null) {
			name = "front";
		}
		
		ThemePackage temaPackage = packages.get(name);
		
		if(temaPackage == null) {
			
			temaPackage = packages.get("front");
			
			if(temaPackage == null) {
				throw new ThemeException("tema package not found: " + this.name + "/" + name);
			}
		}
		
		return temaPackage;
	}

	@Override
	public List<PublicResource> getResourcesByType(String type, String packageName) {
		
		ThemePackage temaPackage = getPackage(packageName);
		
		List<PublicResource> resourcesType = temaPackage.getResources().get(type);
		
		if(resourcesType == null && logger.isTraceEnabled()) {
			logger.trace("resource type {} not found [package={}, theme={}]", type, packageName, name);
		}
		
		return resourcesType;
	}
	
}
