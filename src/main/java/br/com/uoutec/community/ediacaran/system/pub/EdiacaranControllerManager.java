package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebControllerManager;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.mapping.WebControllerID;
import org.brandao.brutos.web.util.WebUtil;

import br.com.uoutec.application.Configuration;
import br.com.uoutec.application.se.ApplicationBootstrapProvider;
import br.com.uoutec.community.ediacaran.ServerBootstrap;

public class EdiacaranControllerManager 
	extends WebControllerManager{

	private Configuration config;
	
	public EdiacaranControllerManager() {
		super();
		ServerBootstrap sb = 
				(ServerBootstrap) ApplicationBootstrapProvider.getBootstrap();
		this.config = sb.getConfiguration();
	}

	public ControllerBuilder addController(String id, 
			RequestMethodType requestMethodType, String view, 
			DispatcherType dispatcherType, boolean resolvedView, String name,
			Class<?> classType, String actionId, ActionType actionType) {

		view     = StringUtil.adjust(view);
		actionId = StringUtil.adjust(actionId);
		name     = StringUtil.adjust(name);
		
		actionId = actionId == null?
				getApplicationContext().getActionParameterName() :
				actionId;

		requestMethodType = requestMethodType == null?
				((WebApplicationContext)getApplicationContext()).getRequestMethod() :
				requestMethodType;
				
		actionType = actionType == null? 
				getApplicationContext().getActionType() :
					actionType;
		
		id = config.getValue(StringUtil.adjust(id));
		
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
    	
    	WebController controller = new WebController(this.getApplicationContext());
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
				this, this.config);

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
