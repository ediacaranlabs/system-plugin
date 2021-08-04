package br.com.uoutec.community.ediacaran.system.theme;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class ThemePackage {

	private String name;
	
	private String path;
	
	private ConcurrentMap<String, Component> tagTemplates;

	private ConcurrentMap<String, List<PublicResource>> resources;
	
	public ThemePackage(String name, String path, ConcurrentMap<String, Component> tagTemplates, 
			ConcurrentMap<String, List<PublicResource>> resources) {
		this.name = name;
		this.path = path;
		this.tagTemplates = tagTemplates;
		this.resources = resources;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public ConcurrentMap<String, Component> getTagTemplates() {
		return tagTemplates;
	}

	public ConcurrentMap<String, List<PublicResource>> getResources() {
		return resources;
	}
	
}
