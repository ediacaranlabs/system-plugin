package br.com.uoutec.community.ediacaran.system.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.community.ediacaran.system.repository.FileManager.FileValue;

public class FileObjectsManagerDriver extends AbstractObjectsManagerDriver {

	private FileManager fileManager;
	
	public FileObjectsManagerDriver(FileManager fileManager, String name) {
		super(name);
		this.fileManager = fileManager;
	}
	
	/* list */
	
	protected List<ObjectMetadata> listAction(String path, String name, Locale locale, boolean recursive, Filter filter){
		
		List<FileMetadata> list = fileManager.list(path, recursive, (e)->{
			ObjectMetadata o = toObjectMetadata(e);
			return filter.accept(path, name, locale, recursive, o);
		});
		
		List<ObjectMetadata> r = new ArrayList<ObjectMetadata>();
		list.stream().forEach((e->{
			r.add(toObjectMetadata(e));
		}));
		
		return r;
	}
	
	/* get */
	
	protected ObjectValue getAction(ObjectMetadata omd) {

		try {
			FileObjectMetadata fomd = (FileObjectMetadata)omd;
			ObjectHandler handler = getObjectHandler(fomd.getType());
			
			FileMetadata fmd = toFileMetadata(omd);
			FileValue fv = fileManager.get(fmd);
			return fv == null? null : new FileObjectsManagerDriverValue(fv.getFile(), handler.toObject(fv.getObject()));
		}
		catch(Throwable e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/* persist */
	
	protected ObjectValue persistAction(String path, String name, Locale locale, Object obj) throws ObjectsManagerDriverException  {
		ObjectHandler handler = getObjectHandler(obj);
		
		try {
			FileMetadata fmd = toFileMetadata(path, name, locale, handler.getType());
			Path file = fileManager.persist(fmd, handler.toData(obj));
			return new FileObjectsManagerDriverValue(file, obj);
		}
		catch(IOException e) {
			throw new ObjectsManagerDriverException(e);
		}
	}
	
	/* delete */
	
	protected void deleteAction(ObjectMetadata omd) throws ObjectsManagerDriverException {
		try {
			FileMetadata fmd = toFileMetadata(omd);
			fileManager.delete(fmd);
		}
		catch(IOException e) {
			throw new ObjectsManagerDriverException(e);
		}
	}
	
	/* private */
	
	private ObjectMetadata toObjectMetadata(FileMetadata fmd) {
		return fmd == null? 
				null : 
				new FileObjectMetadata(
						new PathMetadata(this.getName(), fmd.getPath(), fmd.getName()), 
						fmd.getType(), 
						(Locale)fmd.getExtMetadata("locale")
				);
	}

	private FileMetadata toFileMetadata(String path, String name, Locale locale, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("locale", locale);
		return new FileMetadata(path, name, type, map);
	}
	
	private FileMetadata toFileMetadata(ObjectMetadata omd) {
		
		FileObjectMetadata fomd = (FileObjectMetadata)omd;
		PathMetadata pmd = fomd.getPathMetadata();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("locale", omd.getLocale());
		
		return new FileMetadata(pmd.getPath(), pmd.getId(), fomd.getType(), map); 
	}

	public static class FileObjectMetadata extends ObjectMetadata{

		private String type;
		
		public FileObjectMetadata(PathMetadata pathMetadata, String type, Locale locale) {
			super(pathMetadata, locale);
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
		
	}
	
	public static class FileObjectsManagerDriverValue implements ObjectValue {
		
		private Path file;
		
		private long lastModified;
		
		private Object object;

		public FileObjectsManagerDriverValue(Path file, Object object) {
			this.file = file;
			this.lastModified = object == null? -1 : file.lastModified();
			this.object = object;
		}
		
		public boolean isValid() {
			return lastModified == file.lastModified();
		}

		public Path getFile() {
			return file;
		}

		public long getLastModified() {
			return lastModified;
		}

		public Object getObject() {
			return object;
		}
		
	}

	@Override
	public boolean isCacheable() {
		return false;
	}	
	
}
