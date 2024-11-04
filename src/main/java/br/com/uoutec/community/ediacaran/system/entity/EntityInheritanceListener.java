package br.com.uoutec.community.ediacaran.system.entity;

import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.Plugin;
import br.com.uoutec.ediacaran.core.plugins.PluginInitializer;
import br.com.uoutec.ediacaran.core.plugins.PluginNode;

@Singleton
public class EntityInheritanceListener implements EdiacaranEventListener {

	//private static final String LIST = EntityInheritanceListener.class.getName() + ".List";
	
	@Override
	public void onEvent(EdiacaranEventObject event) {
	
		if(event.getSource() instanceof PluginInitializer) {
			
			if("installing".equals(event.getType())){
				startPlugin((PluginNode)event.getData());
			}
			else
			if("destroying".equals(event.getType())){
				stopPlugin((PluginNode)event.getData());
			}
			
		}
		
	}

	private void startPlugin(PluginNode node) {
		try {
			ContextSystemSecurityCheck.doPrivileged(()->
				loadEntityInheritance(node, (Plugin)node.getExtend().get(PluginInitializer.PLUGIN))
			);
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
		try {
			ContextSystemSecurityCheck.doPrivileged(()->
				destroyEntityInheritance(node, (Plugin)node.getExtend().get(PluginInitializer.PLUGIN))
			);
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	protected boolean loadEntityInheritance(PluginNode node, Plugin plugin) {
		EntityInheritanceLoader loader = EntityContextPlugin.getEntity(EntityInheritanceLoader.class);
		loader.loadEntities(plugin.getPackagesNames());
		return true;
	}

	protected boolean destroyEntityInheritance(PluginNode node, Plugin plugin) {
		EntityInheritanceLoader loader = EntityContextPlugin.getEntity(EntityInheritanceLoader.class);
		loader.removeEntities(plugin.getPackagesNames());
		return true;
	}
	
}
