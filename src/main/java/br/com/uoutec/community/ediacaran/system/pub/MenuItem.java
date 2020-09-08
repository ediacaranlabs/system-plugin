package br.com.uoutec.community.ediacaran.system.pub;

import java.io.Serializable;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.core.system.registry.LanguageManager;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.i18n.MessageLocale;

public class MenuItem implements Serializable{

	private static final long serialVersionUID = -5651914208489395269L;

	private String name;
	
	private String icon;

	private String resourceBundle;
	
	private String template;
	
	private String resource;

	private int order;

	public MenuItem() {
	}
	
	public MenuItem(String name, String icon, String resourceBundle,
			String template, String resource, int order) {
		super();
		this.name = name;
		this.icon = icon;
		this.resourceBundle = resourceBundle;
		this.template = template;
		this.resource = resource;
		this.order = order;
	}

	public String getFullName(){
		Locale locale = MessageLocale.getLocale();
		LanguageManager lang = EntityContextPlugin.getEntity(LanguageManager.class);
		return lang.parse(resourceBundle, this.template, locale);
		//return MessageBundleUtils.getMessageResourceString(this.resourceBundle, this.template, locale, getClass().getClassLoader());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getRawResource() {
		return resource;
	}
	
	public String getResource() {
		VarParser varParser = EntityContextPlugin.getEntity(VarParser.class);
		return varParser.getValue(resource);
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
