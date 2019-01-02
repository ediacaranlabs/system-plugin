package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;

import br.com.uoutec.application.Configuration;

public class EdiacaranControllerBuilder extends WebControllerBuilder{

	private Configuration config;
	
    public EdiacaranControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate, Configuration config){
        super( builder, internalUpdate );
        this.config = config;
    }
    
    public EdiacaranControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate, Configuration config ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
        this.config = config;
    }

    
    public ActionBuilder addAction(String id, RequestMethodType requestMethodType, 
    		String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
    	
        ActionType type      = this.controller.getActionType();
        
        id                   = config.getValue(StringUtil.adjust(id));
		
        resultId             = StringUtil.adjust(resultId);
		
		view                 = StringUtil.adjust(view);
		
		executor             = StringUtil.adjust(executor);

		if(StringUtil.isEmpty(id) && !StringUtil.isEmpty(executor)){
			id = type.getActionID(executor);
		}
		
		requestMethodType    = 
			requestMethodType == null?
				((WebController)this.controller).getRequestMethod() :
				requestMethodType;

		dispatcher = dispatcher == null?
				this.controller.getDispatcherType() :
				dispatcher;
				
		WebActionID actionId = new WebActionID(id, requestMethodType);
				
		if (StringUtil.isEmpty(executor) && StringUtil.isEmpty(id)){
			throw new MappingException("executor cannot be empty");
		}
		
    	if(!type.isValidActionId(id))
    		throw new MappingException("invalid action id: " + id);

        if(requestMethodType == null){
        	throw new MappingException("request method type is required");
        }
		
		if (StringUtil.isEmpty(view) && StringUtil.isEmpty(executor))
			throw new MappingException(
					"view must be informed in abstract actions: " + id);

		if (controller.getActionById(actionId) != null)
			throw new MappingException("duplicate action: " + id);

		//criar base da entidade
		WebAction action = new WebAction();
		action.setId(actionId);
		action.setCode(Action.getNextId());
		//action.setResponseStatus(this.webApplicationContext.getResponseStatus());
		action.setResponseStatus(0);
		action.setName(id);
		action.setController(controller);
		action.setResultValidator(validatorFactory.getValidator(new Configuration()));
		action.setParametersValidator(validatorFactory.getValidator(new Configuration()));
		action.setRequestMethod(requestMethodType);
		
		//registrar entidade
		controller.addAction(actionId, action);

		//criar construtor
		WebActionBuilder actionBuilder = 
			new EdiacaranActionBuilder(action, controller, validatorFactory, 
					this, super.applicationContext);

		//definir caracterÃ­sticas opcionais com o construtor 
		actionBuilder
			.setDispatcherType(dispatcher)
			.setExecutor(executor)
			.setResult(resultId)
			.setResultRendered(resultRendered)
			.setView(view, resolvedView);

		getLogger()
				.info(String
						.format("adding action %s on controller %s",
								new Object[] {
										action.getId(),
										this.controller.getClassType()
												.getSimpleName() }));

		return actionBuilder;    	
    }    
	
	public ControllerBuilder setView(String view, boolean resolvedView) {
		
		view = StringUtil.adjust(view);

		view = resolvedView ? view : applicationContext.getViewResolver()
				.getView(this, null, null, view);

		controller.setView(view);

		return this;
	}
    
}
