package br.com.uoutec.community.ediacaran.system.pub;

import java.lang.reflect.Field;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ComponentBuilder;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.PropertyBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ThrowSafeBuilder;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebControllerBuilder;
import org.brandao.brutos.web.WebThrowSafeBuilder;

import br.com.uoutec.application.Configuration;

public class EdiacaranControllerBuilder extends WebControllerBuilder{

	private Configuration config;
	
    public EdiacaranControllerBuilder(Configuration config, 
    		ControllerBuilder builder, ControllerManager manager, ControllerManager.InternalUpdate internalUpdate){
    	super(builder, internalUpdate);
        this.config = config;
        this.fixBugApplicationContextNullPointerException(builder, manager);
    }
	
	private void fixBugApplicationContextNullPointerException(
			ControllerBuilder builder, ControllerManager manager) {
		try {
			Field f = WebControllerBuilder.class.getDeclaredField("webApplicationContext");
			f.setAccessible(true);
			f.set(this, manager.getApplicationContext());
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
	}
    
	@Override
	public ControllerBuilder addAlias(String id) {
		id = config.getValue(id);
		return super.addAlias(id);
	}

	@Override
	public ControllerBuilder addAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return super.addAlias(id, requestMethodType);
	}

	@Override
	public ControllerBuilder removeAlias(String id) {
		id = config.getValue(id);
		return super.removeAlias(id);
	}

	@Override
	public ControllerBuilder removeAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return super.removeAlias(id, requestMethodType);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, boolean resultRendered, String view,
			DispatcherType dispatcher, boolean resolvedView, String executor) {
		id = config.getValue(id);
		return new EdiacaranActionBuilder(
				config, 
				super.addAction(id, resultId, resultRendered, view, dispatcher, resolvedView, executor), 
				super.controllerManager
		);
	}

	@Override
	public ActionBuilder addAction(String id, RequestMethodType requestMethodType, String resultId,
			boolean resultRendered, String view, DispatcherType dispatcher, boolean resolvedView, String executor) {
		
		id = config.getValue(id);
		
		return new EdiacaranActionBuilder(
				config, 
				super.addAction(id, requestMethodType, resultId, resultRendered, view, dispatcher, resolvedView, executor), 
				super.controllerManager
		);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(Class<?> target, String view, String id, DispatcherType dispatcher,
			boolean resolvedView) {
		id = config.getValue(id);
		return super.addThrowable(target, view, id, dispatcher, resolvedView);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(int responseError, String reason, Class<?> target, String view, String id,
			DispatcherType dispatcher, boolean resolvedView) {
		id = config.getValue(id);
		return super.addThrowable(responseError, reason, target, view, id, dispatcher, resolvedView);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(Class<?> target, String executor, int responseError, String reason,
			String view, DispatcherType dispatcher, boolean resolvedView, String resultId, boolean resultRendered) {
		resultId = config.getValue(resultId);
		return super.addThrowable(target, executor, responseError, reason, view, dispatcher, resolvedView, resultId,
				resultRendered);
	}

	@Override
	public ControllerBuilder setDefaultAction(String id) {
		id = config.getValue(id);
		return super.setDefaultAction(id);
	}

	@Override
	public ControllerBuilder setDefaultAction(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return super.setDefaultAction(id, requestMethodType);
	}

	@Override
	public ControllerBuilder setView(String value, boolean resolvedView) {
		value = StringUtil.adjust(value);

		value = resolvedView ? value : applicationContext.getViewResolver()
				.getView(this, null, null, value);

		controller.setView(value);

		return this;
	}

	@Override
	public ControllerBuilder addRequestType(DataType value) {
		return super.addRequestType(value);
	}

	@Override
	public ControllerBuilder removeRequestType(DataType value) {
		return super.removeRequestType(value);
	}

	@Override
	public ControllerBuilder addResponseType(DataType value) {
		return super.addResponseType(value);
	}

	@Override
	public ControllerBuilder removeResponseType(DataType value) {
		return super.removeResponseType(value);
	}

	@Override
	public ControllerBuilder setResponseStatus(int value) {
		return super.setResponseStatus(value);
	}

	@Override
	public int getResponseStatus() {
		return super.getResponseStatus();
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String id) {
		id = config.getValue(id);
		return super.addThrowable(target, id);
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String view, boolean resolvedView, String id,
			DispatcherType dispatcher) {
		id = config.getValue(id);
		return super.addThrowable(target, view, resolvedView, id, dispatcher);
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String executor, String view, DispatcherType dispatcher,
			boolean resolvedView, String resultId, boolean resultRendered) {
		resultId = config.getValue(resultId);
		return super.addThrowable(target, executor, view, dispatcher, resolvedView, resultId, resultRendered);
	}

	@Override
	public BeanBuilder buildMappingBean(String name, Class<?> target) {
		return super.buildMappingBean(name, target);
	}

	@Override
	public BeanBuilder buildMappingBean(String name, String parentBeanName, Class<?> target) {
		return super.buildMappingBean(name, parentBeanName, target);
	}

	@Override
	public ActionBuilder addAction(String id) {
		id = config.getValue(id);
		return super.addAction(id);
	}

	@Override
	public ActionBuilder addAction(String id, String executor) {
		id = config.getValue(id);
		return super.addAction(id, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String executor, String view, boolean resolvedView) {
		id = config.getValue(id);
		return super.addAction(id, executor, view, resolvedView);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, String view, boolean resolvedView, String executor) {
		id = config.getValue(id);
		return super.addAction(id, resultId, view, resolvedView, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, String view, boolean resolvedView,
			DispatcherType dispatcher, String executor) {
		id = config.getValue(id);
		return super.addAction(id, resultId, view, resolvedView, dispatcher, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, boolean resultRendered, String view,
			boolean resolvedView, DispatcherType dispatcher, String executor) {
		id = config.getValue(id);
		return super.addAction(id, resultId, resultRendered, view, resolvedView, dispatcher, executor);
	}

	@Override
	public InterceptorBuilder addInterceptor(String name) {
		return super.addInterceptor(name);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty) {
		return super.addProperty(propertyName, id, scope, enumProperty);
	}

	@Override
	public PropertyBuilder addNullProperty(String propertyName) {
		return super.addNullProperty(propertyName);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, String temporalProperty) {
		return super.addProperty(propertyName, id, scope, temporalProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, Type type) {
		return super.addProperty(propertyName, id, scope, type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, EnumerationType enumProperty) {
		return super.addProperty(propertyName, id, enumProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope) {
		return super.addProperty(propertyName, id, scope);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, String temporalProperty) {
		return super.addProperty(propertyName, id, temporalProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, Type type) {
		return super.addProperty(propertyName, id, type);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String mapping) {
		return super.addPropertyMapping(propertyName, mapping);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String id, String mapping) {
		return super.addPropertyMapping(propertyName, id, mapping);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String id, String mapping, FetchType fetchType) {
		return super.addPropertyMapping(propertyName, id, mapping, fetchType);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id) {
		return super.addProperty(propertyName, id);
	}

	@Override
	public PropertyBuilder addStaticProperty(String propertyName, Object value) {
		return super.addStaticProperty(propertyName, value);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, Type type) {
		return super.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, type);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id, Class<?> classType) {
		return super.addGenericProperty(propertyName, id, classType);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id, Class<?> classType, FetchType fetchType) {
		return super.addGenericProperty(propertyName, id, classType, fetchType);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id) {
		return super.addGenericProperty(propertyName, id);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, Object classType, Type type) {
		return super.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, classType,
				type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, boolean generic, Object classType,
			Type type) {
		return super.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, generic,
				classType, type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, boolean generic, Object classType,
			FetchType fetchType, Type type) {
		return super.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, generic,
				classType, fetchType, type);
	}

	@Override
	public BeanBuilder buildProperty(String propertyName, Class<?> clazz) {
		return super.buildProperty(propertyName, clazz);
	}

	@Override
	public Class<?> getClassType() {
		return super.getClassType();
	}

	@Override
	public Bean getBean(String name) {
		return super.getBean(name);
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public ControllerBuilder setName(String value) {
		return super.setName(value);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public String getView() {
		return super.getView();
	}

	@Override
	public ControllerBuilder setActionId(String value) {
		return super.setActionId(value);
	}

	@Override
	public String getActionId() {
		return super.getActionId();
	}

	@Override
	public ControllerBuilder setDispatcherType(String value) {
		return super.setDispatcherType(value);
	}

	@Override
	public ControllerBuilder setDispatcherType(DispatcherType value) {
		return super.setDispatcherType(value);
	}

	@Override
	public DispatcherType getDispatcherType() {
		return super.getDispatcherType();
	}

	@Override
	public ControllerBuilder setActionType(ActionType actionType) {
		return super.setActionType(actionType);
	}

	@Override
	public ActionType getActionType() {
		return super.getActionType();
	}

	@Override
	public PropertyBuilder getProperty(String name) {
		return super.getProperty(name);
	}

	@Override
	public boolean isResolvedView() {
		return super.isResolvedView();
	}

	@Override
	public ComponentBuilder getParentBuilder() {
		return super.getParentBuilder();
	}
    
	
}
