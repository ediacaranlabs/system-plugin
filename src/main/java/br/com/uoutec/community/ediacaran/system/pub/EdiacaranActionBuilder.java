package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.WebActionBuilder;

public class EdiacaranActionBuilder extends WebActionBuilder{

    public EdiacaranActionBuilder(ActionBuilder builder){
        super(builder);
    }
    
    public EdiacaranActionBuilder(
    		Action methodForm, 
            Controller controller, 
            ValidatorFactory validatorFactory,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        super(methodForm, controller, validatorFactory, 
                controllerBuilder, applicationContext);
    }
	
	public ActionBuilder setView(String view, boolean resolvedView) {

		view = 
			resolvedView ? 
				view : 
				applicationContext.getViewResolver().getView(this.controllerBuilder, this, null, view);

		this.action.setView(view);
		this.action.setResolvedView(resolvedView);
		
		return this;
	}
	
}
