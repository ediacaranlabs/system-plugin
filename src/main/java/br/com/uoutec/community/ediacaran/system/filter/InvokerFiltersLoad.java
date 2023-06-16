package br.com.uoutec.community.ediacaran.system.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.filter.Filter;
import br.com.uoutec.filter.FilterException;
import br.com.uoutec.filter.invoker.Invoker;
import br.com.uoutec.filter.invoker.InvokerFilterManager;
import br.com.uoutec.filter.invoker.annotation.FilterPriority;
import br.com.uoutec.filter.invoker.annotation.FilterScope;

public class InvokerFiltersLoad {

	private static final Logger logger = LoggerFactory.getLogger(InvokerFiltersLoad.class);
	
	public void configure(List<Class<?>> types, InvokerFilterManager invokerManager) throws FilterException{
		Map<Class<?>, Set<FilterType>> filtersGroup = this.group(types);
		this.register(filtersGroup, invokerManager);
	}
	
	private Map<Class<?>, Set<FilterType>> group(List<Class<?>> types) throws FilterException{
		
		Map<Class<?>, Set<FilterType>> filtersGroup = new HashMap<Class<?>, Set<FilterType>>();
		
		for(Class<?> type: types){
			FilterScope filterScope = type.getAnnotation(FilterScope.class);
			
			if(filterScope == null){
				throw new FilterException("@FilterTarget not found: " + type.getName());
			}
			
			for(Class<?> target: filterScope.value()){
				
				Set<FilterType> set = filtersGroup.get(target);
				
				if(set == null){
					set = new HashSet<FilterType>();
					filtersGroup.put(target, set);
				}
				
				FilterPriority fp = type.getAnnotation(FilterPriority.class);
				int priority = fp == null? 0 : fp.value();
				set.add(new FilterType(type, priority));
				
			}
		}
		
		return filtersGroup;
	}

	private void register(Map<Class<?>, Set<FilterType>> filtersGroup, InvokerFilterManager invokerManager) throws FilterException{
		
		for(Entry<Class<?>, Set<FilterType>> entry: filtersGroup.entrySet()){
			
			for(FilterType f: entry.getValue()){
				Class<?> fType = f.type;
				invokerManager.put(entry.getKey(), this.toObject(fType), f.order);
				
				if(logger.isTraceEnabled()) {
					logger.trace("added filter: " + fType + "[" + entry.getKey() + "]");
				}
				
			}
		}
		
		invokerManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	protected Filter<Invoker> toObject(Class<?> type) throws FilterException{
		try{
			return (Filter<Invoker>)type.newInstance();
		}
		catch(Throwable e){
			throw new FilterException(e);
		}
	}
	
	private static class FilterType{
		
		public Class<?> type;
		
		public int order;

		public FilterType(Class<?> type, int order) {
			super();
			this.type = type;
			this.order = order;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			FilterType other = (FilterType) obj;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
		
	}
	
}
