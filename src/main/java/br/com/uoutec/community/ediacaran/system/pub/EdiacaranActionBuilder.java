package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.ComponentBuilder;
import org.brandao.brutos.ControllerBuilder;
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

	private WebActionBuilder builder;

	public EdiacaranActionBuilder(Configuration config, WebActionBuilder builder) {
		super(builder);
		this.config = config;
		this.builder = builder;
	}

	@Override
	public ActionBuilder addAlias(String id) {
		id = config.getValue(id);
		return builder.addAlias(id);
	}

	@Override
	public ActionBuilder addAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return builder.addAlias(id, requestMethodType);
	}

	@Override
	public ActionBuilder removeAlias(String id) {
		id = config.getValue(id);
		return builder.removeAlias(id);
	}

	@Override
	public ActionBuilder removeAlias(String id, RequestMethodType requestMethodType) {
		id = config.getValue(id);
		return builder.removeAlias(id, requestMethodType);
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
	public void setRequestMethod(RequestMethodType value) {
		builder.setRequestMethod(value);
	}

	@Override
	public RequestMethodType getRequestMethod() {
		return builder.getRequestMethod();
	}

	@Override
	public ActionBuilder setView(String value, boolean viewResolved) {
		return builder.setView(value, viewResolved);
	}

	@Override
	public ActionBuilder addRequestType(DataType value) {
		return builder.addRequestType(value);
	}

	@Override
	public ActionBuilder removeRequestType(DataType value) {
		return builder.removeRequestType(value);
	}

	@Override
	public ActionBuilder addResponseType(DataType value) {
		return builder.addResponseType(value);
	}

	@Override
	public ActionBuilder removeResponseType(DataType value) {
		return builder.removeResponseType(value);
	}

	@Override
	public ActionBuilder setResponseStatus(int value) {
		return builder.setResponseStatus(value);
	}

	@Override
	public ActionBuilder setResponseError(Class<? extends Throwable> type, int value) {
		return builder.setResponseError(type, value);
	}

	@Override
	public int getResponseStatus() {
		return builder.getResponseStatus();
	}

	@Override
	public int getResponseError(Class<? extends Throwable> type) {
		return builder.getResponseError(type);
	}

	@Override
	public ActionBuilder removeAlias(ActionID id) {
		return builder.removeAlias(id);
	}

	@Override
	public ParametersBuilder buildParameters() {
		return builder.buildParameters();
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
		return builder.addThrowable(target, executor, view, dispatcher, resolvedView, resultId, resultRendered);
	}

	@Override
	public ControllerBuilder getControllerBuilder() {
		return builder.getControllerBuilder();
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
	public ActionBuilder setDispatcherType(String value) {
		return builder.setDispatcherType(value);
	}

	@Override
	public ActionBuilder setDispatcherType(DispatcherType value) {
		return builder.setDispatcherType(value);
	}

	@Override
	public DispatcherType getDispatcherType() {
		return builder.getDispatcherType();
	}

	@Override
	public ActionBuilder setExecutor(String value) {
		return builder.setExecutor(value);
	}

	@Override
	public String getExecutor() {
		return builder.getExecutor();
	}

	@Override
	public ActionBuilder setResult(String value) {
		return builder.setResult(value);
	}

	@Override
	public String getResult() {
		return builder.getResult();
	}

	@Override
	public ActionBuilder setResultRendered(boolean value) {
		return builder.setResultRendered(value);
	}

	@Override
	public boolean isResultRendered() {
		return builder.isResultRendered();
	}

	@Override
	public int getParametersSize() {
		return builder.getParametersSize();
	}

	@Override
	public ParameterBuilder getParameter(int index) {
		return builder.getParameter(index);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, Class<?> classType) {
		return builder.setResultAction(name, enumProperty, classType);
	}

	@Override
	public ResultActionBuilder setNullresultAction() {
		return builder.setNullresultAction();
	}

	@Override
	public ResultActionBuilder setResultAction(String name, String temporalProperty, Class<?> classType) {
		return builder.setResultAction(name, temporalProperty, classType);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, Type typeDef) {
		return builder.setResultAction(name, typeDef);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, Class<?> classType) {
		return builder.setResultAction(name, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String mapping, Class<?> classType) {
		return builder.setResultActionMapping(mapping, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String name, String mapping, Class<?> classType) {
		return builder.setResultActionMapping(name, mapping, classType);
	}

	@Override
	public ResultActionBuilder setResultActionMapping(String name, String mapping, ScopeType scope,
			Class<?> classType) {
		return builder.setResultActionMapping(name, mapping, scope, classType);
	}

	@Override
	public BeanBuilder buildParameter(Class<?> classType) {
		return builder.buildParameter(classType);
	}

	@Override
	public BeanBuilder buildParameter(String name, Class<?> classType) {
		return builder.buildParameter(name, classType);
	}

	@Override
	public BeanBuilder buildResultAction(Class<?> classType, Class<?> beanType) {
		return builder.buildResultAction(classType, beanType);
	}

	@Override
	public BeanBuilder buildResultAction(String name, Class<?> classType, Class<?> beanType) {
		return builder.buildResultAction(name, classType, beanType);
	}

	@Override
	public ResultActionBuilder setStaticResultAction(Class<?> classType, Object value) {
		return builder.setStaticResultAction(classType, value);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, Class<?> classType) {
		return builder.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, classType);
	}

	@Override
	public ResultActionBuilder setGenericResultAction(String name, Class<?> classType) {
		return builder.setGenericResultAction(name, classType);
	}

	@Override
	public ResultActionBuilder setGenericResultAction(String name) {
		return builder.setGenericResultAction(name);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, Object classType) {
		return builder.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, classType);
	}

	@Override
	public ResultActionBuilder setResultAction(String name, EnumerationType enumProperty, String temporalProperty,
			String mapping, Type typeDef, Object value, boolean nullable, boolean generic, Object classType) {
		return builder.setResultAction(name, enumProperty, temporalProperty, mapping, typeDef, value, nullable, generic,
				classType);
	}

	@Override
	public ResultActionBuilder getResultAction() {
		return builder.getResultAction();
	}

	@Override
	public RestrictionBuilder addRestriction(RestrictionRules ruleId, Object value) {
		return builder.addRestriction(ruleId, value);
	}

	@Override
	public RestrictionBuilder setMessage(String message) {
		return builder.setMessage(message);
	}

	@Override
	public ComponentBuilder getParentBuilder() {
		return builder.getParentBuilder();
	}
	
	
}
