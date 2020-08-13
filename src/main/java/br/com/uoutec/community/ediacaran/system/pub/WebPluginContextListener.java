package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.Configuration;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginContextEvent;
import br.com.uoutec.community.ediacaran.plugins.PluginContextListener;
import br.com.uoutec.community.ediacaran.plugins.PluginException;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.plugins.PluginNode;
import br.com.uoutec.community.ediacaran.system.EdiacaranWebApplicationContext;
import br.com.uoutec.community.ediacaran.system.WebPlugin;
import br.com.uoutec.community.ediacaran.web.EdiacaranWebInvoker;

public class WebPluginContextListener implements PluginContextListener{

	public static String WEB_APP_CONTEXT  = "web_context";
	
	private static EntityContextPlugin currentEntityContextPlugin;
	
	public WebPluginContextListener() {
		WebPlugin.class.getName();
	}
	
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
		PluginNode pluginNode = evt.getPluginNode();
		Plugin plugin         = (Plugin) pluginNode.getExtend().get(PluginInitializer.PLUGIN);
 
		Configuration config = new Configuration();
		
		config.setProperty(BrutosConstants.RENDER_VIEW_CLASS,        "br.com.uoutec.community.ediacaran.system.pub.TemplateRenderView");
		config.setProperty(BrutosConstants.CONTROLLER_MANAGER_CLASS, "br.com.uoutec.community.ediacaran.system.pub.EdiacaranControllerManager");
		config.setProperty(BrutosConstants.OBJECT_FACTORY_CLASS,     "br.com.uoutec.community.ediacaran.system.pub.WebPluginObjectFactory");
		config.setProperty(BrutosConstants.INVOKER_CLASS,            "br.com.uoutec.community.ediacaran.user.pub.LanguageWebInvoker");
		config.setProperty(BrutosConstants.ACTION_RESOLVER,          "br.com.uoutec.community.ediacaran.system.pub.EdiacaranWebActionResolver");
		config.setProperty(BrutosConstants.VIEW_RESOLVER_PREFIX,     WebPluginUtil.getPublicPath(pluginNode.getPluginMetadata().getPath().getBase()));

		EdiacaranWebApplicationContext appContext = new EdiacaranWebApplicationContext(plugin);
		appContext.setConfiguration(config);
		appContext.flush();
		
		evt.getPluginNode().getExtend().put(WEB_APP_CONTEXT, appContext);
		
		EdiacaranWebInvoker.register(evt.getPluginNode().getPluginMetadata(), appContext);
	}

	@Override
	public void pluginDestroyed(PluginContextEvent evt) {
		Plugin plugin = (Plugin) evt.getPluginNode().getExtend().get(PluginInitializer.PLUGIN);
		if(plugin instanceof WebPlugin) {
			evt.getPluginNode().getExtend().remove(WEB_APP_CONTEXT);
		}
		EdiacaranWebInvoker.unregister(evt.getPluginNode().getPluginMetadata());
	}

	public static EntityContextPlugin getCurrentEntityContextPlugin() {
		return currentEntityContextPlugin;
	}

}
