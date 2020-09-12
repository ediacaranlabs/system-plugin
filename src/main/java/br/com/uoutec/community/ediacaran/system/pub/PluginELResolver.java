package br.com.uoutec.community.ediacaran.system.pub;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginProperties;
import br.com.uoutec.community.ediacaran.plugins.PluginsSupplier;
import br.com.uoutec.community.ediacaran.plugins.PluginsSuppliers;

public class PluginELResolver extends ELResolver{

	private static final String PLUGINS = "plugins";
	
	private PluginsSuppliers pluginsSuppliers;

	public PluginELResolver() {
		this.pluginsSuppliers = EntityContextPlugin.getEntity(PluginsSuppliers.class);
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
			return pluginsSuppliers;
		}
		else
		if(base instanceof PluginsSuppliers) {
			PluginsSupplier ps = ((PluginsSuppliers)base).getSupplier(String.valueOf(property));
			context.setPropertyResolved(true);
			return ps;
		}
		else
		if(base instanceof PluginsSupplier) {
			PluginProperties pm = ((PluginsSupplier)base).getProperties(String.valueOf(property));
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
