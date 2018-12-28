package br.com.uoutec.community.ediacaran.system.pub;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;

import br.com.uoutec.application.se.ApplicationBootstrapProvider;
import br.com.uoutec.community.ediacaran.EdiacaranBootstrap;
import br.com.uoutec.community.ediacaran.PluginManager;
import br.com.uoutec.community.ediacaran.plugins.PluginMetadata;
import br.com.uoutec.community.ediacaran.plugins.PluginPropertyValue;

public class PluginELResolver extends ELResolver{

	private static final String PLUGINS = "plugins";
	
	private PluginManager pluginManager;

	public PluginELResolver() {
		EdiacaranBootstrap bootstrap = 
				(EdiacaranBootstrap) ApplicationBootstrapProvider.getBootstrap();
		this.pluginManager = bootstrap.getPluginManager();
	}
	
	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if(base == null && PLUGINS.equals(property)) {
			context.setPropertyResolved(true);
			return pluginManager;
		}
		else
		if(base instanceof PluginManager) {
			PluginMetadata pm = pluginManager.findById(String.valueOf(property));
			context.setPropertyResolved(true);
			return pm;
		}
		else
		if(base instanceof PluginPropertyValue) {
			context.setPropertyResolved(true);
			if(property.equals("value")) {
				return ((PluginPropertyValue)base).getValue();
			}
			else
			if(property.equals("values")) {
				List<String> list = ((PluginPropertyValue)base).getValues();
				return list == null? null : list.toString();
			}
		}
		
		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return null;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		context.setPropertyResolved(true);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return true;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return null;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return null;
	}

}
