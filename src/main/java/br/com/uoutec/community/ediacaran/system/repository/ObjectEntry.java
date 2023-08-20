package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Locale;
import java.util.Map;

public class ObjectEntry {
	
	private String path;
	
	private String id;
	
	private Map<Locale,Object> locales;

	public ObjectEntry(String path, String id, Map<Locale, Object> locales) {
		this.path = path;
		this.id = id;
		this.locales = locales;
	}

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}

	public String getFullId() {
		return path + "/" + id;
	}
	
	public Object getObject() {
		return locales.get(null);
	}
	
	public Object getObject(Locale locale) {
		return locales.get(locale);
	}
	
	public Map<Locale, Object> getLocales() {
		return locales;
	}
	
}
