package br.com.uoutec.community.ediacaran.system;

import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.core.system.listener.EntityInheritanceListener;
import br.com.uoutec.community.ediacaran.core.system.listener.FilterListener;
import br.com.uoutec.community.ediacaran.core.system.listener.LanguageListener;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;

public class PluginInstaller extends AbstractPlugin{

	public static final String PACKAGE        = "community";
	
	public static final String PROVIDER       = "ediacaran";
	
	public static final String PLUGIN         = "system";

	@Override
	public void install() throws Throwable {
		EdiacaranListenerManager ediacaranListenerManager = EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		registerListeners(ediacaranListenerManager);
	}

	private void registerListeners(EdiacaranListenerManager ediacaranListenerManager) {
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(WebEdiacaranListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(FilterListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}
	
	@Override
	public void uninstall() throws Throwable {
		EdiacaranListenerManager ediacaranListenerManager = EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		unregisterListeners(ediacaranListenerManager);
	}

	public void unregisterListeners(EdiacaranListenerManager ediacaranListenerManager) throws Throwable {
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(WebEdiacaranListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(FilterListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}
	
}
