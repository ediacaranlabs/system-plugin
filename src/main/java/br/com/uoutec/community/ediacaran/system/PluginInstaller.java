package br.com.uoutec.community.ediacaran.system;

import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.core.system.listener.EntityInheritanceListener;
import br.com.uoutec.community.ediacaran.core.system.listener.FilterListener;
import br.com.uoutec.community.ediacaran.core.system.listener.LanguageListener;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.system.theme.listener.WebEdiacaranListener;

public class PluginInstaller extends AbstractPlugin{

	public static final String PACKAGE        = "community";
	
	public static final String PROVIDER       = "ediacaran";
	
	public static final String PLUGIN         = "system";

	@Override
	public void install() throws Throwable {
		EdiacaranListenerManager elm = 
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		elm.addListener(EntityContextPlugin.getEntity(WebEdiacaranListener.class));
		elm.addListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		elm.addListener(EntityContextPlugin.getEntity(FilterListener.class));
		elm.addListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}

	@Override
	public void uninstall() throws Throwable {
		EdiacaranListenerManager elm = 
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		elm.removeListener(EntityContextPlugin.getEntity(WebEdiacaranListener.class));
		elm.removeListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		elm.removeListener(EntityContextPlugin.getEntity(FilterListener.class));
		elm.removeListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}
	
}
