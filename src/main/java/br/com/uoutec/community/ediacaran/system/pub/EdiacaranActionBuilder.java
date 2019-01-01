package br.com.uoutec.community.ediacaran.system.pub;

import java.lang.reflect.Field;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ComponentBuilder;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ParameterBuilder;
import org.brandao.brutos.ParametersBuilder;
import org.brandao.brutos.RestrictionBuilder;
import org.brandao.brutos.ResultActionBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ThrowSafeBuilder;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebThrowSafeBuilder;

import br.com.uoutec.application.Configuration;

public class EdiacaranActionBuilder extends WebActionBuilder{

	private Configuration config;

	public EdiacaranActionBuilder(Configuration config, ActionBuilder builder, ControllerManager manager) {
		super(builder);
		this.config = config;
		this.fixBugApplicationContextNullPointerException(manager);
	}

	private void fixBugApplicationContextNullPointerException(ControllerManager manager) {
		try {
			Field f = WebActionBuilder.class.getDeclaredField("webApplicationContext");
			f.setAccessible(true);
			f.set(this, manager.getApplicationContext());
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public ActionBuilder addAlias(String id) {
		id = config.getValue(id);
		return super.addAlias(id);
	}

	@Override
	public ActionBuilder addAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return super.addAlias(id, requestMethodType);
	}

	@Override
	public ActionBuilder removeAlias(String id) {
		id = config.getValue(id);
		return super.removeAlias(id);
	}

	@Override
	public ActionBuilder removeAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return super.removeAlias(id, requestMethodType);
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
	public void setRequestMethod(RequestMethodType value) {
		super.setRequestMethod(value);
	}

	@Override
	public RequestMethodType getRequestMethod() {
		return super.getRequestMethod();
	}

	@Override
	public ActionBuilder setView(String value, boolean viewResolved) {
		value = 
				viewResolved ? 
					value : 
				applicationContext.getViewResolver().getView(this.controllerBuilder, this, null, value);

		this.action.setView(value);
		this.action.setResolvedView(viewResolved);
		
		return this;
	}

	@Override
	public ActionBuilder addRequestType(DataType value) {
		return super.addRequestType(value);
	}

	@Override
	public ActionBuilder removeRequestType(DataType value) {
		return super.removeRequestType(value);
	}

	@Override
	public ActionBuilder addResponseType(DataType value) {
		return super.addResponseType(value);
	}

	@Override
	public ActionBuilder removeResponseType(DataType value) {
		return super.removeResponseType(value);
	}

	@Override
	public ActionBuilder setResponseStatus(int value) {
		return super.setResponseStatus(value);
	}

	@Override
	public ActionBuilder setResponseError(Class<? extends Throwable> type, int value) {
		return super.setResponseError(type, value);
	}

	@Override
	public int getResponseStatus() {
		return super.getResponseStatus();
	}

	@Override
	public int getResponseError(Class<? extends Throwable> type) {
		return super.getResponseError(type);
	}

	@Override
	public ActionBuilder removeAlias(ActionID id) {
		return super.removeAlias(id);
	}

	@Override
	public ParametersBuilder buildParameters() {
		return super.buildParameters();
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
		return super.addThrowable(target, executor, view, dispatcher, resolvedView, resultId, resultRendered);
	}

	@Override
	public ControllerBuilder getControllerBuilder() {
		return super.getControllerBuilder();
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
	public ActionBuilder setDispatcherType(String value) {
		return super.setDispatcherType(value);
	}

	@Override
	public ActionBuilder setDispatcherType(DispatcherType value) {
		return super.setDispatcherType(value);
	}

	@Override
	public DispatcherType getDispatcherType() {
		return super.getDispatcherType();
	}

	@Override
	public ActionBuilder setExecutor(String value) {
		return super.setExecutor(value);
	}

	@Override
	public String getExecutor() {
		return super.getExecutor();
	}

	@Override
	public ActionBuilder setResult(String value) {
		return super.setResult(value);
	}

	@Override
	public String getResult() {
		return super.getResult();
	}

	@Override
	public ActionBuilder setResultRendered(boolean value) {
		return super.setResultRendered(value);
	}

	@Override
	public boolean isResultRendered() {
		return super.isResultRendered();
	}

	@Override
	public int getParametersSize() {
		return super.getParametersSize();
	}

	@Override
	public ParameterBuilder getParameter(int index) {
		return super.getParameter(index);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, Class<?> classType) {
		return super.setResultAction(name, enumProperty, classType);
	}

	@Override
	public ResultActionBuilder setNullresultAction() {
		return super.setNullresultAction();
	}

	@Override
	public ResultActionBuilder setResultAction(String name, String temporalProperty, Class<?> classType) {
		return super.setResultAction(name, temporalProperty, classType);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, Type typeDef) {
		return super.setResultAction(name, typeDef);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, Class<?> classType) {
		return super.setResultAction(name, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String mapping, Class<?> classType) {
		return super.setResultActionMapping(mapping, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String name, String mapping, Class<?> classType) {
		return super.setResultActionMapping(name, mapping, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String name, String mapping, ScopeType scope,
			Class<?> classType) {
		return super.setResultActionMapping(name, mapping, scope, classType);
	}

	@Override
	public BeanBuilder buildParameter(Class<?> classType) {
		return super.buildParameter(classType);
	}

	@Override
	public BeanBuilder buildParameter(String name, Class<?> classType) {
		return super.buildParameter(name, classType);
	}

	@Override
	public BeanBuilder buildResultAction(Class<?> classType, Class<?> beanType) {
		return super.buildResultAction(classType, beanType);
	}

	@Override
	public BeanBuilder buildResultAction(String name, Class<?> classType, Class<?> beanType) {
		return super.buildResultAction(name, classType, beanType);
	}

	@Override
	public ResultActionBuilder setStaticResultAction(Class<?> classType, Object value) {
		return super.setStaticResultAction(classType, value);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, Class<?> classType) {
		return super.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, classType);
	}

	@Override
	public ResultActionBuilder setGenericResultAction(String name, Class<?> classType) {
		return super.setGenericResultAction(name, classType);
	}

	@Override
	public ResultActionBuilder setGenericResultAction(String name) {
		return super.setGenericResultAction(name);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, Object classType) {
		return super.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, classType);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, boolean generic, Object classType) {
		return super.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, generic,
				classType);
	}

	@Override
	public ResultActionBuilder getResultAction() {
		return super.getResultAction();
	}

	@Override
	public RestrictionBuilder addRestriction(RestrictionRules ruleId, Object value) {
		return super.addRestriction(ruleId, value);
	}

	@Override
	public RestrictionBuilder setMessage(String message) {
		return super.setMessage(message);
	}

	@Override
	public ComponentBuilder getParentBuilder() {
		return super.getParentBuilder();
	}
	
	
}
