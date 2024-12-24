package br.com.uoutec.community.ediacaran.system.util;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import br.com.uoutec.application.bean.Bean;
import br.com.uoutec.application.bean.BeanException;
import br.com.uoutec.application.bean.BeanProperty;
import br.com.uoutec.application.bean.BeanPropertyAnnotation;

public class DataUtil {

	private static final Gson gson;
	
	private static Type dataType = new TypeToken<Map<String, String>>(){}.getType();
	
	static{
		gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT | Modifier.STATIC)
        .registerTypeAdapter(Class.class, new ClassTypeAdapter())
        .create();		
	}
	
	public static String encode(Map<String,String> o){
		return gson.toJson(o);
	}

	public static String encode(Object o){
		return gson.toJson(o);
	}

	public static Map<String,String> encode(Object o, Set<String> excludeFields) {
		
		try{
			Map<String,String> data = new HashMap<String, String>();
			Bean i = new Bean(o);
			
			List<BeanPropertyAnnotation> props = i.getProperties();
			
			for(BeanProperty p: props){
				
				if(p.isTransient() || excludeFields.contains(p.getName())){
					continue;
				}
				
				Object value = p.get(o);
				if(value != null) {
					data.put(p.getName(), gson.toJson(value));
				}
			}
			
			return data;
		}
		catch(Throwable e){
			throw new RuntimeException(e);
		}
	}
	
	public static <Y> Y decode(String v, Class<Y> type){
		return gson.fromJson(v, type);
	}
	
	public static Map<String,String> decode(String v){
		return gson.fromJson(v, dataType);
	}

	public static Object decode(Map<String,String> v, Object o) {
		
		try{
			Bean i = new Bean(o);
			Set<String> remove = new HashSet<>();
			
			for(String name: v.keySet()){
				
				BeanProperty p;
				try{
					p = i.getProperty(name);
				}
				catch(BeanException ex){
					//o objeto não tem mais a propriedade <name>
					continue;
				}
				
				remove.add(name);
				String val = v.get(name);//v.get(name);
				Object vObject = gson.fromJson(val, (Type)p.getDeclaredGenericType());
				p.set(o, vObject);
			}
			
			for(String k: remove) {
				v.remove(k);
			}
			
			return o;
		}
		catch(Throwable e){
			throw new RuntimeException(e);
		}
			
	}

	public static class ClassTypeAdapter 
		implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {
	    @Override
	    public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
	        return new JsonPrimitive(src.getName());
	    }

	    @Override
	    public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	        try {
	            return Class.forName(json.getAsString());
	        } catch (ClassNotFoundException e) {
	            throw new JsonParseException(e);
	        }
	    }
	}
	
}
