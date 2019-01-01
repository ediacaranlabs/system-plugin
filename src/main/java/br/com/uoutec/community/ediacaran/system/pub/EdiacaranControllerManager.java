package br.com.uoutec.community.ediacaran.system.pub;

import java.util.Iterator;
import java.util.List;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebControllerManager;

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

	@Override
	public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
		super.setApplicationContext(applicationContext);
	}

	@Override
	public ControllerBuilder addController(String id, String view, boolean resolvedView, DispatcherType dispatcherType,
			String name, Class<?> classType, String actionId) {
		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, view, resolvedView, dispatcherType, name, classType, actionId),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, String view, DispatcherType dispatcherType, boolean resolvedView,
			String name, Class<?> classType, String actionId, ActionType actionType) {

		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, view, dispatcherType, resolvedView, name, classType, actionId, actionType),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, RequestMethodType requestMethodType, String view,
			DispatcherType dispatcherType, boolean resolvedView, String name, Class<?> classType, String actionId,
			ActionType actionType) {

		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, requestMethodType, view, dispatcherType, resolvedView, name, classType, actionId,
						actionType),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(Class<?> classtype) {
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(classtype),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, Class<?> classType) {
		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, classType),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, String view, boolean resolvedView, Class<?> classType) {
		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, view, resolvedView, classType),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, String view, boolean resolvedView, String name,
			Class<?> classType, String actionId) {
		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, view, resolvedView, name, classType, actionId),
				this,
				this
		);
	}

	@Override
	public ControllerBuilder addController(String id, String view, boolean resolvedView, DispatcherType dispatcherType,
			String name, Class<?> classType, String actionId, ActionType actionType) {
		id = config.getValue(id);
		return new EdiacaranControllerBuilder(
				config, 
				super.addController(id, view, resolvedView, dispatcherType, name, classType, actionId, actionType),
				this,
				this
		);
	}

	@Override
	public boolean contains(String id) {
		id = config.getValue(id);
		return super.contains(id);
	}

	@Override
	public Controller getController(String id) {
		id = config.getValue(id);
		return super.getController(id);
	}

	@Override
	public Controller getController(Class<?> controllerClass) {
		return super.getController(controllerClass);
	}

	@Override
	public List<Controller> getControllers() {
		return super.getControllers();
	}

	@Override
	public Iterator<Controller> getAllControllers() {
		return super.getAllControllers();
	}

	@Override
	public ControllerBuilder getCurrent() {
		return super.getCurrent();
	}

	@Override
	public void setParent(ControllerManager parent) {
		super.setParent(parent);
	}

	@Override
	public ControllerManager getParent() {
		return super.getParent();
	}

	@Override
	public Logger getLogger() {
		return super.getLogger();
	}

	@Override
	public InterceptorManager getInterceptorManager() {
		return super.getInterceptorManager();
	}

	@Override
	public void setInterceptorManager(InterceptorManager interceptorManager) {
		super.setInterceptorManager(interceptorManager);
	}

	@Override
	public ValidatorFactory getValidatorFactory() {
		return super.getValidatorFactory();
	}

	@Override
	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		super.setValidatorFactory(validatorFactory);
	}

	@Override
	public ConfigurableApplicationContext getApplicationContext() {
		return super.getApplicationContext();
	}

	@Override
	public synchronized void removeController(Class<?> clazz) {
		super.removeController(clazz);
	}

	@Override
	public synchronized void removeController(String name) {
		super.removeController(name);
	}

	@Override
	public void addControllerAlias(Controller controller, ControllerID alias) {
		super.addControllerAlias(controller, alias);
	}

	@Override
	public void removeControllerAlias(Controller controller, ControllerID alias) {
		super.removeControllerAlias(controller, alias);
	}
	
	
}
