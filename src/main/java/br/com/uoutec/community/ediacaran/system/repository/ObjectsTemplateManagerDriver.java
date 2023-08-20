package br.com.uoutec.community.ediacaran.system.repository;

import java.util.List;
import java.util.Map;

public interface ObjectsTemplateManagerDriver extends ObjectsManagerDriver{

	void registerTemplate(ObjectTemplate template);
	
	void unregisterTemplate(String id);
	
	ObjectTemplate getTemplateByName(String id);
	
	ObjectTemplate getTemplate(Object object);
	
	List<ObjectTemplate> getTemplates();

	Map<String,ObjectTemplate> getTemplatesIdMap();
	
}
