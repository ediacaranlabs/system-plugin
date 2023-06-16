package br.com.uoutec.community.ediacaran.system.listener;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.EdiacaranEventListener;
import br.com.uoutec.community.ediacaran.EdiacaranEventObject;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.plugins.PluginNode;
import br.com.uoutec.community.ediacaran.system.util.EntityInheritanceManager;
import br.com.uoutec.community.ediacaran.system.util.EntityInheritanceManager.EntityInheritanceUtilLoader;

@Singleton
public class EntityInheritanceListener implements EdiacaranEventListener{

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
			loadEntityInheritance((Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
		try {
			destroyEntityInheritance((Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void loadEntityInheritance(Plugin plugin) {
		EntityInheritanceManager eiu = EntityContextPlugin.getEntity(EntityInheritanceManager.class);
		EntityInheritanceUtilLoader loader = new EntityInheritanceUtilLoader();
		eiu.loadEntities(loader.loadEntities(plugin.getPackagesNames()));
	}

	protected void destroyEntityInheritance(Plugin plugin) {
		EntityInheritanceManager eiu = EntityContextPlugin.getEntity(EntityInheritanceManager.class);
		EntityInheritanceUtilLoader loader = new EntityInheritanceUtilLoader();
		eiu.removeEntities(loader.loadEntities(plugin.getPackagesNames()));
	}
	
}
