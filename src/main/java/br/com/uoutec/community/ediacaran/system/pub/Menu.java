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
import br.com.uoutec.community.ediacaran.core.system.registry.MessageBundle;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.i18n.MessageLocale;

public class Menu implements Serializable{

	private static final long serialVersionUID = -110898947175676961L;

	private String name;
	
	private String icon;
	
	private String resource;
	
	private String body;
	
	private String resourceBundle;
	
	private String badge;
	
	private String badgeStyle;
	
	private String template;
	
	private List<Menu> itens;
	
	private Map<String, Menu> map;
	
	private int order;

	public Menu(){
		this(null, null, null, null, null, new ArrayList<Menu>(), null, null, null, 0);
	}

	public Menu(String name){
		this(name, null, null, null, null, new ArrayList<Menu>(), null, null, null, 0);
	}
	
	public Menu(String name, String icon, String resource,
			String resourceBundle, String template, String badge, String badgeStyle, String body, int order) {
		this(name, icon, resource, resourceBundle, template, new ArrayList<Menu>(),
				badge, badgeStyle, body, order);
	}
	
	public Menu(String name, String icon, String resource,
			String resourceBundle, String template, List<Menu> itens,
			String badge, String badgeStyle, String body, int order) {
		super();
		this.name = name;
		this.icon = icon;
		this.resource = resource;
		this.resourceBundle = resourceBundle;
		this.template = template;
		this.itens = itens;
		this.order = order;
		this.badge = badge;
		this.badgeStyle = badgeStyle;
		
		this.map = new HashMap<String, Menu>();
		
		if(itens != null) {
			for(Menu i: itens) {
				addItem(i);
			}
		}
	}

	public String getFullName(){
		
		if(resourceBundle != null) {
			Locale locale = MessageLocale.getLocale();
			MessageBundle lang = EntityContextPlugin.getEntity(MessageBundle.class);
			return lang.getMessageResourceString(this.resourceBundle, this.template, locale);
		}
		
		return name;
	}
	
	public String getBody() {
		return body;
	}

	public Menu setBody(String body) {
		this.body = body;
		return this;
	}

	public String getName() {
		return name;
	}

	public Menu setName(String name) {
		this.name = name;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public Menu setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public String getRawResource() {
		return resource;
	}
	
	public String getResource() {
		VarParser varParser = EntityContextPlugin.getEntity(VarParser.class);
		return varParser.getValue(resource);
	}

	public Menu setResource(String resource) {
		this.resource = resource;
		return this;
	}

	public String getResourceBundle() {
		return resourceBundle;
	}

	public Menu setResourceBundle(String resourceBundle) {
		this.resourceBundle = resourceBundle;
		return this;
	}

	public String getTemplate() {
		return template;
	}

	public Menu setTemplate(String template) {
		this.template = template;
		return this;
	}

	public int getOrder() {
		return order;
	}

	public Menu setOrder(int order) {
		this.order = order;
		return this;
	}

	public String getBadgeStyle() {
		return badgeStyle;
	}

	public Menu setBadgeStyle(String badgeStyle) {
		this.badgeStyle = badgeStyle;
		return this;
	}

	public List<Menu> getItens() {
		return itens;
	}

	public String getBadge() {
		return badge;
	}

	public Menu setBadge(String badge) {
		this.badge = badge;
		return this;
	}

	public Menu addItem(Menu item){
		synchronized (this) {
			if(map.containsKey(item.getName())) {
				throw new IllegalStateException("menu exist: " + item.getName());
			}
			
			this.itens.add(item);
			this.map.put(item.getName(), item);
			
			Collections.sort(this.itens, new Comparator<Menu>(){

				public int compare(Menu o1, Menu o2) {
					return o2.getOrder() - o1.getOrder();
				}
				
			});
			
			return this;
		}
	}

	public Menu getItem(String name){
		return map.get(name);
	}
	
	public Menu removeItem(String name){
		synchronized (this) {
			Menu m = map.get(name);
			
			if(m == null) 
				return this;
			
			itens.remove(m);
			map.remove(m.getName());
			
			return this;
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
