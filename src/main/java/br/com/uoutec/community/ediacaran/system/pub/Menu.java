package br.com.uoutec.community.ediacaran.system.pub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.core.system.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.i18n.MessageBundleUtils;
import br.com.uoutec.i18n.MessageLocale;

public class Menu implements Serializable{

	private static final long serialVersionUID = -110898947175676961L;

	private String name;
	
	private String icon;
	
	private String resource;
	
	private String resourceBundle;
	
	private String template;
	
	private List<MenuItem> itens;
	
	private Map<String, MenuItem> map;
	
	private int order;

	public Menu(){
		this(null, null, null, null, null, new ArrayList<MenuItem>(), 0);
	}
	
	public Menu(String name, String icon, String resource,
			String resourceBundle, String template, List<MenuItem> itens,
			int order) {
		super();
		this.name = name;
		this.icon = icon;
		this.resource = resource;
		this.resourceBundle = resourceBundle;
		this.template = template;
		this.itens = itens;
		this.order = order;
		this.map = new HashMap<String, MenuItem>();
		
		if(itens != null) {
			for(MenuItem i: itens) {
				addItem(i);
			}
		}
	}

	public String getFullName(){
		Locale locale = MessageLocale.getLocale();
		//PropertyResourceBundle
		return MessageBundleUtils.getMessageResourceString(this.resourceBundle, this.template, locale, getClass().getClassLoader());
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<MenuItem> getItens() {
		return itens;
	}

	public void addItem(MenuItem item){
		synchronized (this) {
			if(map.containsKey(item.getName())) {
				throw new IllegalStateException("menu exist: " + item.getName());
			}
			
			this.itens.add(item);
			this.map.put(item.getName(), item);
			
			Collections.sort(this.itens, new Comparator<MenuItem>(){

				public int compare(MenuItem o1, MenuItem o2) {
					return o2.getOrder() - o1.getOrder();
				}
				
			});
			
		}
	}

	public MenuItem getItem(String name){
		return map.get(name);
	}
	
	public void removeItem(MenuItem item){
		synchronized (this) {
			MenuItem m = map.get(item.getName());
			
			if(m == null) 
				return;
			
			itens.remove(m);
			map.remove(m.getName());
		}
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
		Menu other = (Menu) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
