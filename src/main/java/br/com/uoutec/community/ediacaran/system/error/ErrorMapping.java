package br.com.uoutec.community.ediacaran.system.error;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;

@Singleton
public class ErrorMapping implements PublicBean{

	public static final String PERMISSION_PREFIX = "app.error.";
	
	private Map<ExceptionMessageMappingkey, Map<Class<?>,ErrorResolver>> map;
	
	private String defaultError;
	
	public ErrorMapping(){
		this.map = new TreeMap<ExceptionMessageMappingkey, Map<Class<?>,ErrorResolver>>(new CMP());
		this.defaultError = "unknown error";
	}

	public synchronized void registerError(Class<?> type, String action, 
			String error, ErrorResolver resolver
			) throws ErrorMessageMappingException{
		this.registerError(type, action, error, Throwable.class, resolver);
	}
	
	public synchronized void registerError(Class<?> type, String action, 
			String error, Class<? extends Throwable> t, ErrorResolver resolver
			) throws ErrorMessageMappingException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "register"));

		ExceptionMessageMappingkey key = new ExceptionMessageMappingkey(type, action, error);
		
		
		Map<Class<?>,ErrorResolver> map = this.map.get(key);
		
		if(map == null){
			map = new HashMap<Class<?>, ErrorMapping.ErrorResolver>();
			this.map.put(key, map);
		}
		
		if(map.containsKey(t)){
			throw new ErrorMessageMappingException("mapping has been added: " + key);
		}
		
		map.put(t, resolver);
		
	}

	public synchronized void removeError(Class<?> type, String action, 
			String error) throws ErrorMessageMappingException{
		this.removeError(type, action, error, Throwable.class);
	}
	
	public synchronized void removeError(Class<?> type, String action, 
			String error, Class<? extends Throwable> t) throws ErrorMessageMappingException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "unregister"));
		
		ExceptionMessageMappingkey key = new ExceptionMessageMappingkey(type, action, error);
		
		Map<Class<?>,ErrorResolver> map = this.map.get(key);
		
		if(map != null){
			
			map.remove(t);
			
			if(map.isEmpty()){
				this.map.remove(key);
			}
		}
	}
	
	public String getDefaultError() {
		return defaultError;
	}

	public void setDefaultError(String defaultError) {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "defaultError"));
		
		this.defaultError = defaultError;
	}

	ErrorResolver getErrorResolver(Class<?> type, String action, String error, Throwable t){
		ExceptionMessageMappingkey key  = new ExceptionMessageMappingkey(type, action, error);
		Map<Class<?>,ErrorResolver> map = this.map.get(key);
		
		if(map != null){
			ErrorResolver r = map.get(t.getClass());
			
			if(r == null){
				r = map.get(Throwable.class);
			}
			
			return r;
		}
		
		return null;
	}
	
	private class CMP implements Comparator<ExceptionMessageMappingkey>{

		@Override
		public int compare(ExceptionMessageMappingkey o1,
				ExceptionMessageMappingkey o2) {
			
			if(o1.type != o2.type){
				return o1.type.hashCode() > o2.type.hashCode()? 1 : -1;
			}
			
			int acmp = o1.action.compareTo(o2.action);
			
			if(acmp != 0){
				return acmp;
			}
			
			acmp = o1.error.compareTo(o2.error);
			
			if(acmp != 0){
				return acmp;
			}
			
			return 0;
		}
		
	}
	private class ExceptionMessageMappingkey {
		
		public Class<?> type;
		
		public String action;
		
		public String error;

		public ExceptionMessageMappingkey(Class<?> type, String action,
				String error) {
			this.type = type;
			this.action = action;
			this.error = error;
		}

		@Override
		public String toString() {
			return "[type=" + type + ", action="
					+ action + ", error=" + error + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((action == null) ? 0 : action.hashCode());
			result = prime * result + ((error == null) ? 0 : error.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			ExceptionMessageMappingkey other = (ExceptionMessageMappingkey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (action == null) {
				if (other.action != null)
					return false;
			} else if (!action.equals(other.action))
				return false;
			if (error == null) {
				if (other.error != null)
					return false;
			} else if (!error.equals(other.error))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

		private ErrorMapping getOuterType() {
			return ErrorMapping.this;
		}
		
	}

	public interface ErrorResolver {
		
		String getError(Class<?> type, String action, String error, Throwable t);
		
	}
}
