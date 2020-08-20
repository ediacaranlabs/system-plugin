package br.com.uoutec.community.ediacaran.system;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginListenerRegistry;
import br.com.uoutec.community.ediacaran.system.pub.WebPluginContextListener;

public class PluginInstaller extends AbstractWebPluginInstaller{

	public static final String PACKAGE        = "community";
	
	public static final String PROVIDER       = "ediacaran";
	
	public static final String PLUGIN         = "system";
	
	public void install() throws Throwable {
		super.install();
		PluginListenerRegistry plr = EntityContextPlugin.getEntity(PluginListenerRegistry.class);
		plr.registerListener(new WebPluginContextListener());
	}
	
}
