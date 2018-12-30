package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ComponentBuilder;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.PropertyBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ThrowSafeBuilder;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebControllerBuilder;
import org.brandao.brutos.web.WebThrowSafeBuilder;

import br.com.uoutec.application.Configuration;

public class EdiacaranControllerBuilder extends WebControllerBuilder{

	private Configuration config;
	
	private WebControllerBuilder builder;
	
    public EdiacaranControllerBuilder(Configuration config, 
    		WebControllerBuilder builder, EdiacaranConstrollerManager manager){
    	super(builder, manager);
        this.config = config;
        this.builder = builder;
    }
	
	@Override
	public ControllerBuilder addAlias(String id) {
		id = config.getValue(id);
		return builder.addAlias(id);
	}

	@Override
	public ControllerBuilder addAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return builder.addAlias(id, requestMethodType);
	}

	@Override
	public ControllerBuilder removeAlias(String id) {
		id = config.getValue(id);
		return builder.removeAlias(id);
	}

	@Override
	public ControllerBuilder removeAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return builder.removeAlias(id, requestMethodType);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, boolean resultRendered, String view,
			DispatcherType dispatcher, boolean resolvedView, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, resultId, resultRendered, view, dispatcher, resolvedView, executor);
	}

	@Override
	public ActionBuilder addAction(String id, RequestMethodType requestMethodType, String resultId,
			boolean resultRendered, String view, DispatcherType dispatcher, boolean resolvedView, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, requestMethodType, resultId, resultRendered, view, dispatcher, resolvedView, executor);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(Class<?> target, String view, String id, DispatcherType dispatcher,
			boolean resolvedView) {
		id = config.getValue(id);
		return builder.addThrowable(target, view, id, dispatcher, resolvedView);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(int responseError, String reason, Class<?> target, String view, String id,
			DispatcherType dispatcher, boolean resolvedView) {
		id = config.getValue(id);
		return builder.addThrowable(responseError, reason, target, view, id, dispatcher, resolvedView);
	}

	@Override
	public WebThrowSafeBuilder addThrowable(Class<?> target, String executor, int responseError, String reason,
			String view, DispatcherType dispatcher, boolean resolvedView, String resultId, boolean resultRendered) {
		resultId = config.getValue(resultId);
		return builder.addThrowable(target, executor, responseError, reason, view, dispatcher, resolvedView, resultId,
				resultRendered);
	}

	@Override
	public ControllerBuilder setDefaultAction(String id) {
		id = config.getValue(id);
		return builder.setDefaultAction(id);
	}

	@Override
	public ControllerBuilder setDefaultAction(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return builder.setDefaultAction(id, requestMethodType);
	}

	@Override
	public ControllerBuilder setView(String value, boolean resolvedView) {
		return builder.setView(value, resolvedView);
	}

	@Override
	public ControllerBuilder addRequestType(DataType value) {
		return builder.addRequestType(value);
	}

	@Override
	public ControllerBuilder removeRequestType(DataType value) {
		return builder.removeRequestType(value);
	}

	@Override
	public ControllerBuilder addResponseType(DataType value) {
		return builder.addResponseType(value);
	}

	@Override
	public ControllerBuilder removeResponseType(DataType value) {
		return builder.removeResponseType(value);
	}

	@Override
	public ControllerBuilder setResponseStatus(int value) {
		return builder.setResponseStatus(value);
	}

	@Override
	public int getResponseStatus() {
		return builder.getResponseStatus();
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String id) {
		id = config.getValue(id);
		return builder.addThrowable(target, id);
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String view, boolean resolvedView, String id,
			DispatcherType dispatcher) {
		id = config.getValue(id);
		return builder.addThrowable(target, view, resolvedView, id, dispatcher);
	}

	@Override
	public ThrowSafeBuilder addThrowable(Class<?> target, String executor, String view, DispatcherType dispatcher,
			boolean resolvedView, String resultId, boolean resultRendered) {
		resultId = config.getValue(resultId);
		return builder.addThrowable(target, executor, view, dispatcher, resolvedView, resultId, resultRendered);
	}

	@Override
	public BeanBuilder buildMappingBean(String name, Class<?> target) {
		return builder.buildMappingBean(name, target);
	}

	@Override
	public BeanBuilder buildMappingBean(String name, String parentBeanName, Class<?> target) {
		return builder.buildMappingBean(name, parentBeanName, target);
	}

	@Override
	public ActionBuilder addAction(String id) {
		id = config.getValue(id);
		return builder.addAction(id);
	}

	@Override
	public ActionBuilder addAction(String id, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String executor, String view, boolean resolvedView) {
		id = config.getValue(id);
		return builder.addAction(id, executor, view, resolvedView);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, String view, boolean resolvedView, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, resultId, view, resolvedView, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, String view, boolean resolvedView,
			DispatcherType dispatcher, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, resultId, view, resolvedView, dispatcher, executor);
	}

	@Override
	public ActionBuilder addAction(String id, String resultId, boolean resultRendered, String view,
			boolean resolvedView, DispatcherType dispatcher, String executor) {
		id = config.getValue(id);
		return builder.addAction(id, resultId, resultRendered, view, resolvedView, dispatcher, executor);
	}

	@Override
	public InterceptorBuilder addInterceptor(String name) {
		return builder.addInterceptor(name);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty) {
		return builder.addProperty(propertyName, id, scope, enumProperty);
	}

	@Override
	public PropertyBuilder addNullProperty(String propertyName) {
		return builder.addNullProperty(propertyName);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, String temporalProperty) {
		return builder.addProperty(propertyName, id, scope, temporalProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, Type type) {
		return builder.addProperty(propertyName, id, scope, type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, EnumerationType enumProperty) {
		return builder.addProperty(propertyName, id, enumProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope) {
		return builder.addProperty(propertyName, id, scope);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, String temporalProperty) {
		return builder.addProperty(propertyName, id, temporalProperty);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, Type type) {
		return builder.addProperty(propertyName, id, type);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String mapping) {
		return builder.addPropertyMapping(propertyName, mapping);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String id, String mapping) {
		return builder.addPropertyMapping(propertyName, id, mapping);
	}

	@Override
	public PropertyBuilder addPropertyMapping(String propertyName, String id, String mapping, FetchType fetchType) {
		return builder.addPropertyMapping(propertyName, id, mapping, fetchType);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id) {
		return builder.addProperty(propertyName, id);
	}

	@Override
	public PropertyBuilder addStaticProperty(String propertyName, Object value) {
		return builder.addStaticProperty(propertyName, value);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, Type type) {
		return builder.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, type);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id, Class<?> classType) {
		return builder.addGenericProperty(propertyName, id, classType);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id, Class<?> classType, FetchType fetchType) {
		return builder.addGenericProperty(propertyName, id, classType, fetchType);
	}

	@Override
	public PropertyBuilder addGenericProperty(String propertyName, String id) {
		return builder.addGenericProperty(propertyName, id);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, Object classType, Type type) {
		return builder.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, classType,
				type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, boolean generic, Object classType,
			Type type) {
		return builder.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, generic,
				classType, type);
	}

	@Override
	public PropertyBuilder addProperty(String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
			String temporalProperty, String mapping, Object value, boolean nullable, boolean generic, Object classType,
			FetchType fetchType, Type type) {
		return builder.addProperty(propertyName, id, scope, enumProperty, temporalProperty, mapping, value, nullable, generic,
				classType, fetchType, type);
	}

	@Override
	public BeanBuilder buildProperty(String propertyName, Class<?> clazz) {
		return builder.buildProperty(propertyName, clazz);
	}

	@Override
	public Class<?> getClassType() {
		return builder.getClassType();
	}

	@Override
	public Bean getBean(String name) {
		return builder.getBean(name);
	}

	@Override
	public String getId() {
		return builder.getId();
	}

	@Override
	public ControllerBuilder setName(String value) {
		return builder.setName(value);
	}

	@Override
	public String getName() {
		return builder.getName();
	}

	@Override
	public String getView() {
		return builder.getView();
	}

	@Override
	public ControllerBuilder setActionId(String value) {
		return builder.setActionId(value);
	}

	@Override
	public String getActionId() {
		return builder.getActionId();
	}

	@Override
	public ControllerBuilder setDispatcherType(String value) {
		return builder.setDispatcherType(value);
	}

	@Override
	public ControllerBuilder setDispatcherType(DispatcherType value) {
		return builder.setDispatcherType(value);
	}

	@Override
	public DispatcherType getDispatcherType() {
		return builder.getDispatcherType();
	}

	@Override
	public ControllerBuilder setActionType(ActionType actionType) {
		return builder.setActionType(actionType);
	}

	@Override
	public ActionType getActionType() {
		return builder.getActionType();
	}

	@Override
	public PropertyBuilder getProperty(String name) {
		return builder.getProperty(name);
	}

	@Override
	public boolean isResolvedView() {
		return builder.isResolvedView();
	}

	@Override
	public ComponentBuilder getParentBuilder() {
		return builder.getParentBuilder();
	}
    
	
}
