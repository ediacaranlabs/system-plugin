package br.com.uoutec.community.ediacaran.system;

import br.com.uoutec.community.ediacaran.core.system.AbstractPluginInstaller;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginListenerRegistry;
import br.com.uoutec.community.ediacaran.system.pub.WebPluginContextListener;

public class PluginInstaller extends AbstractPluginInstaller{

	public static final String PACKAGE        = "community";
	
	public static final String PROVIDER       = "ediacaran";
	
	public static final String PLUGIN         = "system";
	
	public void install() throws PluginException {
		super.install();
		PluginListenerRegistry plr = EntityContextPlugin.getEntity(PluginListenerRegistry.class);
		plr.registerListener(new WebPluginContextListener());
	}
	
}
