package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebControllerBuilder;
import org.brandao.brutos.web.WebThrowSafeBuilder;

import br.com.uoutec.application.Configuration;
import br.com.uoutec.application.se.ApplicationBootstrapProvider;
import br.com.uoutec.community.ediacaran.ServerBootstrap;

public class EdiacaranControllerBuilder extends WebControllerBuilder{

	private Configuration config;
	
    public EdiacaranControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate){
        super( builder, internalUpdate );
        this.loadConfig();
    }
    
    public EdiacaranControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
        this.loadConfig();
    }
	
    private void loadConfig() {
		ServerBootstrap sb = 
				(ServerBootstrap) ApplicationBootstrapProvider.getBootstrap();
		this.config = sb.getConfiguration();
    }
    
	public ControllerBuilder addAlias(String id, RequestMethodType requestMethodType) {
    	id = this.config.getValue(id);
		return super.addAlias(id, requestMethodType);
	}
 
	public ControllerBuilder removeAlias(String id, RequestMethodType requestMethodType) {
    	id = this.config.getValue(id);
		return super.removeAlias(id, requestMethodType);
	}
	
    public ActionBuilder addAction(String id, RequestMethodType requestMethodType, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	
    	id = this.config.getValue(id);
    	view = this.config.getValue(view);
    	
    	return super.addAction(id, requestMethodType, 
        		resultId, resultRendered, view, 
                dispatcher, resolvedView, executor);
    }
	
    public WebThrowSafeBuilder addThrowable(Class<?> target, String executor, 
			int responseError, String reason, String view, DispatcherType dispatcher, 
			boolean resolvedView, String resultId, boolean resultRendered){
    	
    	view = this.config.getValue(view);
    	return super.addThrowable(target, executor, 
    			responseError, reason, view, dispatcher, 
    			resolvedView, resultId, resultRendered);
    }
    
	public ControllerBuilder setDefaultAction(String id, RequestMethodType requestMethodType) {
    	id = this.config.getValue(id);
    	return super.setDefaultAction(id, requestMethodType);
	}
    
    public ControllerBuilder setView(String value, boolean resolvedView){
    	value = this.config.getValue(value);
    	return super.setView(value, resolvedView);
    }
	
}
