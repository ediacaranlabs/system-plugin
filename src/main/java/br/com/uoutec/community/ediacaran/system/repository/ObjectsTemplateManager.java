package br.com.uoutec.community.ediacaran.system.repository;

import java.util.List;
import java.util.Map;

public interface ObjectsTemplateManager extends ObjectsManager{

	ObjectTemplate getTemplateById(String driverName, String id);
	
	ObjectTemplate getTemplate(String driverName, Object object);
	
	List<ObjectTemplate> getTemplates(String driverName);

	Map<String,ObjectTemplate> getTemplatesIdMap(String driverName);
	
}
