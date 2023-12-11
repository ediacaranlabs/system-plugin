package br.com.uoutec.community.ediacaran.system.event;

import java.util.List;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;
import br.com.uoutec.entity.registry.Registry;


public interface SystemEventRegistry extends Registry, PublicBean{

	public static final String PERMISSION_PREFIX = "app.registry.event.";
	
	void registerSystemEvent(SystemEvent entity) throws SystemEventRegistryException;

	void removeSystemEvent(SystemEvent entity) throws SystemEventRegistryException;
	
	List<SystemEvent> getSystemEventByType(SystemEventType type, Integer first, 
			Integer max) throws SystemEventRegistryException;
}
