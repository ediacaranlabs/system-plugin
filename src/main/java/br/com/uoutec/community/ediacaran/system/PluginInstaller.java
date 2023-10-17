package br.com.uoutec.community.ediacaran.system;

import br.com.uoutec.community.ediacaran.AbstractPlugin;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.system.concurrent.ListThreadPoolExecutor;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManager;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManagerException;
import br.com.uoutec.community.ediacaran.system.entity.EntityInheritanceListener;
import br.com.uoutec.community.ediacaran.system.listener.FilterListener;
import br.com.uoutec.community.ediacaran.system.listener.LanguageListener;

public class PluginInstaller extends AbstractPlugin{

	public static final String PACKAGE        = "community";
	
	public static final String PROVIDER       = "ediacaran";
	
	public static final String PLUGIN         = "system";
	
	@Override
	public void install() throws Throwable {
		registryThreadGroups();
		registerListeners();
	}

	private void registryThreadGroups() throws ThreadGroupManagerException{
		ThreadGroupManager threadGroupManager = EntityContextPlugin.getEntity(ThreadGroupManager.class);
		threadGroupManager.registryThreadGroup("default", new ListThreadPoolExecutor(20, 5));
	}
	
	private void registerListeners() {
		EdiacaranListenerManager ediacaranListenerManager = EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		//ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(FilterListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}
	
	@Override
	public void uninstall() throws Throwable {
		unregisterListeners();
		removeThreadGroups();
	}

	private void removeThreadGroups(){
		ThreadGroupManager threadGroupManager = EntityContextPlugin.getEntity(ThreadGroupManager.class);
		threadGroupManager.removeThreadGroup("default");
	}
	
	public void unregisterListeners() throws Throwable {
		EdiacaranListenerManager ediacaranListenerManager = EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(EntityInheritanceListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(FilterListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(LanguageListener.class));
	}
	
}
