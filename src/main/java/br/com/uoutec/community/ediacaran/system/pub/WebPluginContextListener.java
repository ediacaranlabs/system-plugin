package br.com.uoutec.community.ediacaran.system.pub;

import br.com.uoutec.community.ediacaran.ContextManager;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginContextEvent;
import br.com.uoutec.community.ediacaran.plugins.PluginContextListener;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.system.WebPlugin;

public class WebPluginContextListener implements PluginContextListener{

	public static String WEB_APP_CONTEXT  = "web_context";
	
	@Override
	public void pluginInitialized(PluginContextEvent evt) {
		try {
			Plugin plugin = (Plugin) evt.getPluginNode().getExtend().get(PluginInitializer.PLUGIN);
			if(plugin instanceof WebPlugin) {
				pluginInitialized0(evt);
			}
		}
		catch(Throwable e) {
			throw new PluginException(e);
		}
	}
	
	public void pluginInitialized0(PluginContextEvent evt) throws Throwable {
		ContextManager contextManager = EntityContextPlugin.getEntity(ContextManager.class);
		contextManager.registerContext(evt.getPluginNode().getPluginConfiguration().getMetadata());
	}

	@Override
	public void pluginDestroyed(PluginContextEvent evt) {
		ContextManager contextManager = EntityContextPlugin.getEntity(ContextManager.class);
		contextManager.unregisterContext(evt.getPluginNode().getPluginConfiguration().getMetadata());
	}

}
