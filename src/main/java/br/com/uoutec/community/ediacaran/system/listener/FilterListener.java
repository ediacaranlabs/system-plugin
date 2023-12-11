package br.com.uoutec.community.ediacaran.system.listener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import br.com.uoutec.application.scanner.DefaultScanner;
import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.community.ediacaran.system.filter.InvokerFilterManagerPublic;
import br.com.uoutec.community.ediacaran.system.filter.InvokerFiltersLoad;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.Plugin;
import br.com.uoutec.ediacaran.core.plugins.PluginInitializer;
import br.com.uoutec.ediacaran.core.plugins.PluginNode;
import br.com.uoutec.filter.FilterException;
import br.com.uoutec.filter.invoker.annotation.FilterScope;

@Singleton
public class FilterListener implements EdiacaranEventListener{

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
			loadFilters((Plugin)node.getExtend().get(PluginInitializer.PLUGIN));
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
	}

	@SuppressWarnings("unchecked")
	protected void loadFilters(Plugin plugin) throws FilterException {
		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		filter.setExpression(Arrays
				.asList(FilterScope.class.getName()));

		DefaultScanner s = new DefaultScanner();
		s.setBasePackage(plugin.getPackagesNames());
		s.addIncludeFilter(filter);
		s.scan();
		
		List<Class<?>> clazzList = s.getClassList();

		InvokerFiltersLoad ifl = new InvokerFiltersLoad();
		
		InvokerFilterManagerPublic invokerFilterManager = 
				EntityContextPlugin.getEntity(InvokerFilterManagerPublic.class);
		
		ifl.configure(clazzList, invokerFilterManager);
	}
	
}
