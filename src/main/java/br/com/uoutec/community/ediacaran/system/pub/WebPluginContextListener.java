package br.com.uoutec.community.ediacaran.system.pub;

import java.io.File;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.Configuration;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginContextEvent;
import br.com.uoutec.community.ediacaran.plugins.PluginContextListener;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.system.EdiacaranWebApplicationContext;
import br.com.uoutec.community.ediacaran.system.WebPlugin;

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
			e.printStackTrace();
			//throw new PluginException(e);
		}
	}
	
	public void pluginInitialized0(PluginContextEvent evt) throws Throwable {
		Plugin plugin = (Plugin) evt.getPluginNode().getExtend().get(PluginInitializer.PLUGIN);
		ClassLoader classLoader = (ClassLoader) evt.getPluginNode().getExtend().get(PluginInitializer.CLASS_LOADER);
		File pluginPath  = 
				evt.getPluginNode().getPluginMetadata().getPath().getBase();
		File supplierPath = pluginPath.getParentFile();
		File path = supplierPath.getParentFile();
		String publicPath = "/plugins/" + path.getName() + "/" + supplierPath.getName() + "/" + pluginPath.getName();
 
		Configuration config = 
				(Configuration)ClassUtil.getInstance(
						ClassUtil.get(Configuration.class.getName(), classLoader));
		
		config.setProperty(BrutosConstants.RENDER_VIEW_CLASS,        "br.com.uoutec.community.ediacaran.system.pub.TemplateRenderView");
		config.setProperty(BrutosConstants.CONTROLLER_MANAGER_CLASS, "br.com.uoutec.community.ediacaran.system.pub.EdiacaranControllerManager");
		config.setProperty(BrutosConstants.OBJECT_FACTORY_CLASS,     "br.com.uoutec.community.ediacaran.system.pub.WebPluginObjectFactory");
		config.setProperty(BrutosConstants.INVOKER_CLASS,            "br.com.uoutec.community.ediacaran.user.pub.LanguageWebInvoker");
		config.setProperty(BrutosConstants.VIEW_RESOLVER_PREFIX,     publicPath);
		
		EdiacaranWebApplicationContext appContext = new EdiacaranWebApplicationContext(plugin);
		ClassLoader cl = appContext.getClassloader();
		appContext.setConfiguration(config);
		appContext.flush();
		
		evt.getPluginNode().getExtend().put(WEB_APP_CONTEXT, appContext);
	}

	@Override
	public void pluginDestroyed(PluginContextEvent evt) {
		Plugin plugin = (Plugin) evt.getPluginNode().getExtend().get(PluginInitializer.PLUGIN);
		if(plugin instanceof WebPlugin) {
			evt.getPluginNode().getExtend().remove(WEB_APP_CONTEXT);
		}
	}

	public static EntityContextPlugin getCurrentEntityContextPlugin() {
		return currentEntityContextPlugin;
	}

}