package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebControllerBuilder;
import org.brandao.brutos.web.WebControllerManager;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.mapping.WebControllerID;
import org.brandao.brutos.web.util.WebUtil;

import br.com.uoutec.application.Configuration;
import br.com.uoutec.application.se.ApplicationBootstrapProvider;
import br.com.uoutec.community.ediacaran.ServerBootstrap;

public class EdiacaranConstrollerManager 
	extends WebControllerManager{

	private Configuration config;
	
	private ConfigurableWebApplicationContext webApplicationContext;
	
	public EdiacaranConstrollerManager() {
		super();
		ServerBootstrap sb = 
				(ServerBootstrap) ApplicationBootstrapProvider.getBootstrap();
		this.config = sb.getConfiguration();
		this.webApplicationContext = (ConfigurableWebApplicationContext) applicationContext;
	}
	
    public ControllerBuilder addController(String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId ){
    	
    	id = this.config.getValue(id);
    	view = this.config.getValue(view);
    	
        return addController( id, view, resolvedView,
                dispatcherType, name, classType, actionId, 
                null);
    }

	public ControllerBuilder addController(String id, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {
		
    	id = this.config.getValue(id);
    	view = this.config.getValue(view);
		
		return addController(id, null, view, dispatcherType, 
				resolvedView, name, classType, actionId, actionType);
	}
    
	public ControllerBuilder addController(String id, 
			RequestMethodType requestMethodType, String view, 
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {

		view     = StringUtil.adjust(view);
		actionId = StringUtil.adjust(actionId);
		name     = StringUtil.adjust(name);

    	id       = this.config.getValue(id);
    	view     = this.config.getValue(view);
		
		actionId = actionId == null?
				this.webApplicationContext.getActionParameterName() :
				actionId;

		requestMethodType = requestMethodType == null?
				this.webApplicationContext.getRequestMethod() :
				requestMethodType;
				
		//dispatcherType = dispatcherType == null? 
		//		this.webApplicationContext.getDispatcherType() :
		//			dispatcherType;

		actionType = actionType == null? 
				this.webApplicationContext.getActionType() :
					actionType;
		
		id = StringUtil.adjust(id);
		
        //id = 
    	//	StringUtil.isEmpty(id) && (actionType.isDelegate() || actionType.isComposite())?
    	//			actionType.getControllerID(classType.getSimpleName().toLowerCase()) :
    	//			id;
        id = 
    		StringUtil.isEmpty(id)?
				actionType.getControllerID(classType.getSimpleName()) :
				id;
				
		if (classType == null){
			throw new MappingException("invalid class type: "
					+ classType);
		}

		if (actionType == null) {
			throw new MappingException("action type is required");
		}
		
        if(resolvedView && view != null)
            WebUtil.checkURI(view, true);
		
    	if(!actionType.isValidControllerId(id)){
    		throw new MappingException("invalid controller id: " + id);
    	}
        
    	WebControllerID controllerId = new WebControllerID(id, requestMethodType);
    	
    	WebController controller = new WebController(this.webApplicationContext);
		controller.setClassType(classType);
		controller.setId(controllerId);
		
		// Action
		ActionListener ac = new ActionListener();
		ac.setPreAction(this.getMethodAction("preAction", controller.getClassType()));
		ac.setPostAction(this.getMethodAction("postAction", controller.getClassType()));
		controller.setActionListener(ac);
		controller.setRequestMethod(requestMethodType);
		controller.setDefaultInterceptorList(interceptorManager
				.getDefaultInterceptors());

		this.current = new EdiacaranControllerBuilder(controller, this,
				interceptorManager, validatorFactory, applicationContext,
				this);

		this.current
			.setName(name)
			.setView(view, resolvedView)
			.setActionId(actionId)
			.setDispatcherType(dispatcherType)
			.setActionType(actionType);

		addController(controller.getId(), controller);

		this.getLogger().info(
				String.format("added controller %s",
						new Object[] { classType.getSimpleName() }));
		
		return this.getCurrent();
	}
	
}
