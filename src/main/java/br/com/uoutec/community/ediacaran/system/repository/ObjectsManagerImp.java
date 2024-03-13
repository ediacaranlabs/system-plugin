package br.com.uoutec.community.ediacaran.system.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.community.ediacaran.system.repository.ObjectsManagerDriver.ObjectsManagerDriverListener;

@Singleton
public class ObjectsManagerImp 
	implements ObjectsManager {

	private static final String PATH_FORMAT = "(\\/+[a-z0-9][a-z0-9]+([_-][a-z0-9]+)*)+";
	
	private static final String ID_FORMAT = "[a-z0-9][a-z0-9]+([_-][a-z0-9]+)*";

	private static final String DRIVER_FORMAT = "[a-z0-9]+([_-][a-z0-9]+)*";
	
	private static final String FULLID_FORMAT = "(" + DRIVER_FORMAT + ")(" + PATH_FORMAT + ")?\\/+(" + ID_FORMAT + ")";

	private static final String SEARCH_FORMAT = "(" + DRIVER_FORMAT + ")(" + PATH_FORMAT + ")*";
	
	private Pattern fullIdPattern = Pattern.compile(FULLID_FORMAT);
	
	private Pattern searchPattern = Pattern.compile(SEARCH_FORMAT);
	
	//private Pattern driverPattern = Pattern.compile(DRIVER_FORMAT);
	
	public static final String basePermission = "app.objs.";
	
	public static final String OBJECTS_REPOSITORY = "objects/";
	
	private static final Locale NULL_LOCALE = new Locale("xx", "XX");
	
	private ConcurrentMap<String, ConcurrentMap<Locale,ObjectValue>> objects;
	
	private ReadWriteLock readWriteLock;
	
	private Lock writeLock;
	
	private Lock readLock;
	
	private ConcurrentMap<String,ObjectsManagerDriver> drivers;
	
	private ObjectListenerManager objectListenerManager;
	
	public ObjectsManagerImp() {
		drivers = new ConcurrentHashMap<String,ObjectsManagerDriver>();
		objects = new ConcurrentHashMap<String, ConcurrentMap<Locale,ObjectValue>>();
		this.readWriteLock = new ReentrantReadWriteLock();
		this.writeLock = readWriteLock.writeLock();
		this.readLock = readWriteLock.readLock();
		this.objectListenerManager = new ObjectListenerManager();
		//this.objectFileManager = new ObjectFileManager(new File(System.getProperty("app.base"), OBJECTS_REPOSITORY));
	}
	
	protected boolean isVaidID(String value) {
		return fullIdPattern.matcher(value).matches();
	}
	
	protected boolean isVaidSearch(String value) {
		return searchPattern.matcher(value).matches();
	}
	
	protected String toNormalize(String value) {
		return value.trim().replaceAll("/+", "/");
	}
	
	protected PathMetadata getPathMetadata(String id) {
		
		if(!isVaidID(id)) {
			return null;
		}
		
		id = toNormalize(id);
		
		String[] parts = id.split("/+", 2);
		String driver = parts[0];
		String fullPath =  "/" + parts[1];
		
		int lastIndex = fullPath.lastIndexOf("/");
		
		String path = lastIndex == 0? "/" : fullPath.substring(0, lastIndex);
		String objId = fullPath.substring(lastIndex + 1);
		return new PathMetadata(driver, path, objId);
	}
	
	private PathMetadata getSearchMetadata(String value) {
		
		if(value == null || !isVaidSearch(value)) {
			return null;
		}
		
		value = value.trim();
		
		String[] parts = value.split("/+", 2);
		String driver = parts[0];
		String fullPath = parts.length == 1? null : "/" + parts[1];
		
		return new PathMetadata(driver, fullPath, null);
	}
	
	@Override
	public ObjectMetadata registerObjectIfNotExist(String id, Locale locale, Object object) {
		
		if(!isVaidID(id)) {
			throw new IllegalStateException("invalid format: " + id);
		}
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + id.replace("/", ".") + ".register"));
		
		writeLock.lock();
		try {
			
			objectListenerManager.beforeRegister(id, locale, object);
			
			PathMetadata pmd = getPathMetadata(id);
			ObjectsManagerDriver driver = getDriver(pmd.getDriver());
			
			ObjectMetadata omd = 
					driver.unique(pmd.getPath(), pmd.getId(), locale, false, SearchType.EQUAL_LOCALE);
			
			if(omd == null) {
				omd = regiterObject(id, object, locale, pmd, driver);
			}
			
			objectListenerManager.afterRegister(id, locale, object);
			
			return omd;
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			writeLock.unlock();
		}
		
	}
	
	/* /global/admin/menus/adminmenubar */
	/* /local/index */
	/* /mysql/admin/menus/adminmenubar */
	/* /mysql/users/name/afonso%brandao */
	/* /mysql/users/id/afonso%brandao */
	
	@Override
	public ObjectMetadata registerObject(String id, Locale locale, Object object) {
		
		if(object == null) {
			throw new NullPointerException("object");
		}
		
		if(!isVaidID(id)) {
			throw new IllegalStateException("invalid id: " + id);
		}

		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + id.replace("/", ".") + ".register"));

		writeLock.lock();
		try {
			
			objectListenerManager.beforeRegister(id, locale, object);
			
			PathMetadata pmd = getPathMetadata(id);
			ObjectsManagerDriver driver = getDriver(pmd.getDriver());
			ObjectMetadata o = regiterObject(id, object, locale, pmd, driver);
			
			objectListenerManager.afterRegister(id, locale, object);
			
			return o;
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			writeLock.unlock();
		}
		
		
	}

	private ObjectMetadata regiterObject(String id, Object object, Locale locale, PathMetadata pmd, ObjectsManagerDriver driver) {
		
		ObjectValue obj = persistObject(driver, pmd, locale, object);

		if(driver.isCacheable()) {
			
			ConcurrentMap<Locale,ObjectValue> map = objects.get(id);
			
			if(map == null) {
				map = new ConcurrentHashMap<Locale,ObjectValue>();
				ConcurrentMap<Locale,ObjectValue> current = objects.putIfAbsent(id, map);
				if(current != null) {
					map = current;
				}
			}
			
			if(obj.isValid()) {
				map.put(getSafeLocale(locale), obj);
			}
			
		}

		return new ObjectMetadata(pmd, locale);
	}
	
	@Override
	public void unregisterObject(String id, Locale locale) {

		if(!isVaidID(id)) {
			throw new IllegalStateException("invalid format: " + id);
		}
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + id.replace("/", ".") + ".unregister"));
		
		writeLock.lock();
		try {
			
			objectListenerManager.beforeUnregister(id, locale);
			
			PathMetadata pmd = getPathMetadata(id);
			ObjectsManagerDriver driver = getDriver(pmd.getDriver());
			
			ObjectMetadata omd = driver.unique(pmd.getPath(), pmd.getId(), locale, false, SearchType.EQUAL_LOCALE);

			if(driver.isCacheable()) {
				
				ConcurrentMap<Locale,ObjectValue> map = objects.get(id);
				
				if(map != null) {
					map.remove(getSafeLocale(locale));
					if(map.isEmpty()) {
						objects.remove(id, map);
					}
				}
				
			}
			
			if(omd != null) {
				deleteObject(driver, pmd, omd);
			}
			
			objectListenerManager.afterUnregister(id, locale);
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			writeLock.unlock();
		}
		
	}

	@Override
	public void registerDriver(ObjectsManagerDriver driver) throws ObjectsManagerDriverException {

		String driverName = driver.getName().toLowerCase();
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "driver." + driverName + ".register"));
		
		if(drivers.putIfAbsent(driver.getName().toLowerCase(), driver) != null) {
			throw new ObjectsManagerDriverException("driver exists: " + driver.getName());
		}
		
	}

	@Override
	public void unregisterDriver(ObjectsManagerDriver driver) {
		
		String driverName = driver.getName().toLowerCase();
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "driver." + driverName + ".unregister"));
		
		drivers.remove(driver.getName().toLowerCase(), driver);
	}

	@Override
	public ObjectsManagerDriver getDriver(String driverName) {

		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "driver." + driverName + ".get"));
		
		return drivers.get(driverName);
	}
	
	@Override
	public Object getObject(ObjectMetadata omd) {
		PathMetadata pmd = omd.getPathMetadata();
		ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
		return get(driver, pmd, omd.getLocale());
	}
	
	@Override
	public Object getObject(String id) {
		return getObject(id, null);
	}

	@Override
	public Object getObject(String id, Locale locale) {
		
		if(!isVaidID(id)) {
			throw new IllegalStateException("invalid format: " + id);
		}
		
		readLock.lock();
		try {
			PathMetadata pmd = getPathMetadata(id);
			ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
			
			Object object = get(driver, pmd, locale);
			
			if(object == null && locale != null) {
				object = get(driver, pmd, null);
			}
			
			return object;
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			readLock.unlock();
		}
		
	}

	@Override
	public ObjectEntry getObjects(String id) {
		
		if(!isVaidID(id)) {
			throw new IllegalStateException("invalid format: " + id);
		}
		
		readLock.lock();
		try {
			PathMetadata pmd = getPathMetadata(id);
			ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
			
			List<ObjectMetadata> list = driver.list(pmd.getPath(), pmd.getId(), null, false, SearchType.EQUAL);
			
			Map<Locale,Object> map = new HashMap<Locale, Object>();
			for(ObjectMetadata omd: list) {
				Object o = get(driver, pmd, omd.getLocale());
				if(o != null) {
					map.put(omd.getLocale(), o);
				}
			}
			return map.isEmpty()? null : new ObjectEntry(pmd.getPath(), pmd.getId(), map);
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			readLock.unlock();
		}
			
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> list(String path, String name, Locale locale, boolean recursive, Filter filter) {
		
		readLock.lock();
		try {
			PathMetadata pmd = getSearchMetadata(path);
			
			if(pmd == null) {
				return (List<Object>)Collections.EMPTY_LIST;
			}
			
			ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
			
			/*
			List<ObjectMetadata> list = driver.list(pmd.getPath(), recursive, e->{
				boolean result;
				result =           (name == null? true : e.getId().contains(name));
				result = result && (locale == null? true : locale.equals(e.getLocale()));
				return result;
			});
			*/
			List<ObjectMetadata> list = driver.list(pmd.getPath(), name, locale, recursive, filter);
			
			List<Object> r = new ArrayList<Object>();
			for(ObjectMetadata omd: list) {
				Object o = 
						get(
								driver, 
								omd.getPathMetadata(), 
								omd.getLocale()
						);
				
				if(o != null) {
					r.add(o);
				}
			}
			return r;
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			readLock.unlock();
		}
			
	}

	@Override
	public List<ObjectMetadata> listMetadata(String path, String name, Locale locale, boolean recursive, Filter filter) {
		
		readLock.lock();
		try {
			PathMetadata pmd = getSearchMetadata(path);
			ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
			
			return driver.list(pmd.getFullId(), name, locale, recursive, filter);
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			readLock.unlock();
		}
			
	}
	
	@Override
	public List<ObjectEntry> listObjects(String path, String name, boolean recursive, Filter filter) {
		
		readLock.lock();
		try {
			PathMetadata pmd = getSearchMetadata(path);
			ObjectsManagerDriver driver = getSecureDriver(pmd.getDriver());
			
			List<ObjectMetadata> list = driver.list(pmd.getPath(), name, null, recursive, filter);
			
			Map<String, List<ObjectMetadata>> group = new HashMap<String, List<ObjectMetadata>>();
			
			for(ObjectMetadata omd: list) {
				String id = omd.getPathMetadata().getFullId();
				
				List<ObjectMetadata> g = group.get(id);
				
				if(g == null) {
					g = new ArrayList<ObjectMetadata>();
					group.put(id, g);
				}
				
				g.add(omd);
			}
			
			List<ObjectEntry> r = new ArrayList<ObjectEntry>();

			for(Entry<String,List<ObjectMetadata>> e: group.entrySet()) {
				
				Map<Locale,Object> map = new HashMap<Locale, Object>();
				
				for(ObjectMetadata omd: e.getValue()) {
					
					Object o = get(driver, omd.getPathMetadata(), omd.getLocale());
					
					if(o != null) {
						map.put(omd.getLocale(), o);
					}
				}
				
				if(!map.isEmpty()) {
					ObjectMetadata base = e.getValue().get(0);
					PathMetadata basePMD = base.getPathMetadata();
					r.add(new ObjectEntry(basePMD.getPath(), basePMD.getId(), map));
				}
				
			}
			
			return r;
		}
		catch(ObjectsManagerDriverException e) {
			throw new IllegalStateException(e);
		}
		finally {
			readLock.unlock();
		}
			
	}

	public void addListener(ObjectListener listener) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "listener.register"));
		
		objectListenerManager.registerListener(listener);
	}

	public void removeListener(ObjectListener listener) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "listener.unregister"));
		
		objectListenerManager.unregisterListener(listener);
	}
	
	public void addListener(String driverName, ObjectsManagerDriverListener listener) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "listener.register"));

		ObjectsManagerDriver driver = getSecureDriver(driverName);
		driver.addListener(listener);
		
	}

	public void removeListener(String driverName, ObjectsManagerDriverListener listener) {

		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(basePermission + "listener.unregister"));
		
		ObjectsManagerDriver driver = getSecureDriver(driverName);
		driver.removeListener(listener);
	}
	
	public void flush() {
		writeLock.lock();
		try {
			objects.clear();
		}
		finally {
			writeLock.unlock();
		}		
	}

	/* --private-- */
	
	/* get */
	
	private Object get(ObjectsManagerDriver driver, PathMetadata pmd, Locale locale) {
		
		ObjectValue obj;
		
		if(driver.isCacheable()) {
			
			ConcurrentMap<Locale,ObjectValue> map = objects.get(pmd.getGlobalId());
			
			if(map != null) {
				obj = map.get(getSafeLocale(locale));
				
				if(obj != null && obj.isValid()) {
					return obj.getObject();
				}
			}
			
		}
		
		try {
			obj = loadObject(driver, pmd, locale);
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}
		
		return obj == null? null : obj.getObject(); 
	}
	
	private synchronized ObjectValue loadObject(ObjectsManagerDriver driver, PathMetadata pmd, Locale locale) throws IOException {
		
		if(driver.isCacheable()) {
			ConcurrentMap<Locale,ObjectValue> map = objects.get(pmd.getGlobalId());
			
			if(map != null) {
				ObjectValue obj = map.get(getSafeLocale(locale));
				if(obj != null && obj.isValid()) {
					return obj;
				}
			}
		}
		
		ObjectMetadata omd = driver.unique(pmd.getPath(), pmd.getId(), locale, false, SearchType.EQUAL_LOCALE);
		
		if(omd != null) {
			
			objectListenerManager.beforeLoad(pmd.getGlobalId(), locale);

			ObjectValue object;
			
			try {
				object = getObject(driver, omd);
			}
			catch(ObjectsManagerDriverException e) {
				throw new IllegalStateException(e);
			}
			
			if(driver.isCacheable()) {
				ConcurrentMap<Locale,ObjectValue> map = new ConcurrentHashMap<Locale,ObjectValue>();
				ConcurrentMap<Locale,ObjectValue> current = objects.putIfAbsent(pmd.getGlobalId(), map);
				if(current != null) {
					map = current;
				}
				map.put(getSafeLocale(locale), object);
			}

			objectListenerManager.afterLoad(pmd.getGlobalId(), locale, object.getObject());
			
			return object;
		}
		
		if(driver.isCacheable()) {
			ConcurrentMap<Locale,ObjectValue> map = objects.get(pmd.getGlobalId());
			
			if(map != null ) {
				map.remove(getSafeLocale(locale));
				if(map.isEmpty()) {
					objects.remove(pmd.getGlobalId());
				}
			}
		}
		
		return null;
	}
	
	/* private */
	
	private Locale getSafeLocale(Locale locale) {
		return locale == null? NULL_LOCALE : locale;
	}

	/* get */
	
	private ObjectValue getObject(ObjectsManagerDriver driver, ObjectMetadata omd) throws ObjectsManagerDriverException {
		return driver.get(omd);
	}
	
	/* persist */
	
	private ObjectValue persistObject(ObjectsManagerDriver driver, PathMetadata pmd, Locale locale, Object object) throws ObjectsManagerDriverException {
		return driver.persist(pmd.getPath(), pmd.getId(), locale, object);
	}

	/* delete */
	
	private void deleteObject(ObjectsManagerDriver driver, PathMetadata pmd, ObjectMetadata omd) throws ObjectsManagerDriverException {
		driver.delete(omd);
	}
	
	/* base */
	
	public ObjectsManagerDriver getSecureDriver(String driverName) throws ObjectsManagerDriverException {

		ObjectsManagerDriver driver = drivers.get(driverName);
		
		if(driver == null) {
			throw new ObjectsManagerDriverException("not found: " + driverName);
		}
		
		return driver;
	}	
	
	private static class ObjectListenerManager implements ObjectListener{

		private List<ObjectListener> listeners;
		
		private ReadWriteLock readWriteLock;
		
		public ObjectListenerManager() {
			this.listeners = new LinkedList<ObjectListener>();
			this.readWriteLock = new ReentrantReadWriteLock();
		}
		
		public void registerListener(ObjectListener listener) {
			Lock lock = readWriteLock.writeLock();
			lock.lock();
			try {
				listeners.add(listener);
			}
			finally {
				lock.unlock();
			}
		}

		public void unregisterListener(ObjectListener listener) {
			Lock lock = readWriteLock.writeLock();
			lock.lock();
			try {
				listeners.remove(listener);
			}
			finally {
				lock.unlock();
			}
		}
		
		@Override
		public void beforeLoad(String id, Locale locale) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.beforeLoad(id, locale);
				}
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void afterLoad(String id, Locale locale, Object obj) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.afterLoad(id, locale, obj);
				}
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void beforeRegister(String id, Locale locale, Object object) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.beforeRegister(id, locale, object);
				}
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void afterRegister(String id, Locale locale, Object object) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.afterRegister(id, locale, object);
				}
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void beforeUnregister(String id, Locale locale) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.beforeUnregister(id, locale);
				}
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void afterUnregister(String id, Locale locale) {
			Lock lock = readWriteLock.readLock();
			lock.lock();
			try {
				for(ObjectListener l: listeners) {
					l.afterUnregister(id, locale);
				}
			}
			finally {
				lock.unlock();
			}
		}
		
	}

}
