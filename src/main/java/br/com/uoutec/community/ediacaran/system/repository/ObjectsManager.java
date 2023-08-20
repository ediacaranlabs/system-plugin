package br.com.uoutec.community.ediacaran.system.repository;

import java.util.List;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.community.ediacaran.system.repository.ObjectsManagerDriver.ObjectsManagerDriverListener;

public interface ObjectsManager extends PublicBean {

	ObjectMetadata registerObject(String id, Locale locale, Object object);
	
	ObjectMetadata registerObjectIfNotExist(String id, Locale locale, Object object);
	
	void unregisterObject(String id, Locale locale);
	
	ObjectEntry getObjects(String id);
	
	Object getObject(String id);
	
	Object getObject(String id, Locale locale);
	
	Object getObject(ObjectMetadata omd);
	
	List<ObjectMetadata> listMetadata(String path, String name, Locale locale, boolean recursive, Filter filter);
	
	List<Object> list(String path, String name, Locale locale, boolean recursive, Filter filter);
	
	List<ObjectEntry> listObjects(String path, String name, boolean recursive, Filter filter);
	
	void registerDriver(ObjectsManagerDriver driver) throws ObjectsManagerDriverException;
	
	void unregisterDriver(ObjectsManagerDriver driver);
	
	ObjectsManagerDriver getDriver(String name);
	
	void addListener(ObjectListener listener);

	void removeListener(ObjectListener listener);

	void addListener(String driverName, ObjectsManagerDriverListener listener);

	void removeListener(String driverName, ObjectsManagerDriverListener listener);
	
	void flush();
	
}
