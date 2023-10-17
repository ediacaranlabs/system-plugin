package br.com.uoutec.community.ediacaran.system.entity;

import java.util.List;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.EdiacaranEventListener;
import br.com.uoutec.community.ediacaran.EdiacaranEventObject;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.Plugin;
import br.com.uoutec.community.ediacaran.plugins.PluginInitializer;
import br.com.uoutec.community.ediacaran.plugins.PluginNode;

@Singleton
public class EntityInheritanceListener implements EdiacaranEventListener {

	private static final String LIST = EntityInheritanceListener.class.getName() + ".List";
	
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
			loadEntityInheritance(node, (Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
		try {
			destroyEntityInheritance(node, (Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void loadEntityInheritance(PluginNode node, Plugin plugin) {
		EntityInheritanceManager eiu = EntityContextPlugin.getEntity(EntityInheritanceManager.class);
		EntityInheritanceLoader loader = new EntityInheritanceLoader();
		
		List<Class<?>> list = loader.loadEntities(plugin.getPackagesNames());
		node.setVar(LIST, list);
		
		eiu.loadEntities(loader.loadEntities(plugin.getPackagesNames()));
	}

	@SuppressWarnings("unchecked")
	protected void destroyEntityInheritance(PluginNode node, Plugin plugin) {
		EntityInheritanceManager eiu = EntityContextPlugin.getEntity(EntityInheritanceManager.class);
		
		List<Class<?>> list = node.getVar(LIST, List.class);
		
		if(list!= null) {
			eiu.removeEntities(list);
			node.setVar(LIST, null);
		}
		
	}
	
}
