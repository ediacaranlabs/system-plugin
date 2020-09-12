package br.com.uoutec.community.ediacaran.system.pub;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginProperties;
import br.com.uoutec.community.ediacaran.plugins.PluginsProperties;

public class PluginELResolver extends ELResolver{

	private static final String PLUGINS = "plugins";
	
	private PluginsProperties pluginsProperties;

	public PluginELResolver() {
		this.pluginsProperties = EntityContextPlugin.getEntity(PluginsProperties.class);
	}
	
	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		try {
			return getVal(context, base, property);
		}
		catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	public Object getVal(ELContext context, Object base, Object property) throws PluginException {
		if(base == null && PLUGINS.equals(property)) {
			context.setPropertyResolved(true);
			return pluginsProperties.get;
		}
		else
		if(base instanceof PluginsProperties) {
			PluginProperties pm = pluginsProperties.getPluginProperties(String.valueOf(property));
			context.setPropertyResolved(true);
			return pm;
		}
		else
		if(base instanceof PluginProperties) {
			context.setPropertyResolved(true);
			return ((PluginProperties)base).getString(String.valueOf(property));
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
