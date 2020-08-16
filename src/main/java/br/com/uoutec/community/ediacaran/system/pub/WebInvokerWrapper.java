package br.com.uoutec.community.ediacaran.system.pub;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ConfigurableRenderView;
import org.brandao.brutos.ConfigurableRequestParser;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.InvokerException;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.RequestParserListener;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.StackRequest;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebInvoker;
import org.jboss.weld.context.RequestContext;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPluginProvider;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPluginRegistry;

public class WebInvokerWrapper extends WebInvoker{

	private WebInvoker wi;
	
	private EntityContextPluginProvider ecpp;
	
	private ClassLoader cl;
	
	public WebInvokerWrapper(WebInvoker wi, EntityContextPluginProvider ecpp, ClassLoader cl) {
		this.wi = wi;
		this.ecpp = ecpp;
		this.cl = cl;
	}
	
	@Override
	public void invoker(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		RequestContext m_requestContext = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			m_requestContext               = ecpp.getEntity(RequestContext.class);
			m_requestContext.activate();
			wi.invoker(request, response, chain);
		}
		finally {
            m_requestContext.invalidate();
            m_requestContext.deactivate();
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
		
	}

	@Override
	public Object invoke(Class<?> controllerClass, String actionId) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.invoke(controllerClass, actionId);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public Object invoke(Class<?> controllerClass, RequestMethodType requestMethodType, String actionId) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.invoke(controllerClass, requestMethodType, actionId);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public Object invoke(Controller controller, ResourceAction action, Object resource, Object[] parameters)
			throws InvokerException {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.invoke(controller, action, resource, parameters);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public CodeGenerator getCodeGenerator() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getCodeGenerator();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setCodeGenerator(CodeGenerator codeGenerator) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setCodeGenerator(codeGenerator);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ObjectFactory getObjectFactory() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getObjectFactory();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setObjectFactory(ObjectFactory objectFactory) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setObjectFactory(objectFactory);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ControllerManager getControllerManager() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getControllerManager();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setControllerManager(ControllerManager controllerManager) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setControllerManager(controllerManager);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ActionResolver getActionResolver() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getActionResolver();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setActionResolver(ActionResolver actionResolver) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setActionResolver(actionResolver);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ConfigurableApplicationContext getApplicationContext() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getApplicationContext();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setApplicationContext(applicationContext);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ConfigurableRenderView getRenderView() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getRenderView();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setRenderView(ConfigurableRenderView renderView) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setRenderView(renderView);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public ConfigurableRequestParser getRequestParser() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getRequestParser();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setRequestParser(ConfigurableRequestParser requestParser) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setRequestParser(requestParser);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public RequestParserListener getRequestParserListener() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getRequestParserListener();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void setRequestParserListener(RequestParserListener requestParserListener) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.setRequestParserListener(requestParserListener);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public RequestInstrument getRequestInstrument() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getRequestInstrument();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public StackRequest getStackRequest() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getStackRequest();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public StackRequest getStackRequest(RequestInstrument value) {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getStackRequest(value);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public StackRequestElement getStackRequestElement() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.getStackRequestElement();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public Object invoke(Controller controller, ResourceAction action, Object[] parameters) throws InvokerException {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.invoke(controller, action, parameters);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public boolean invoke(MutableMvcRequest request, MutableMvcResponse response) throws InvokerException {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			return wi.invoke(request, response);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

	@Override
	public void flush() {
		EntityContextPluginProvider oldEntityContextPluginProvider = null;
		ClassLoader oldClassLoader = null;
		try {
			oldClassLoader                 = Thread.currentThread().getContextClassLoader();
			oldEntityContextPluginProvider = EntityContextPluginRegistry.getEntityContextPluginProvider();
			
			Thread.currentThread().setContextClassLoader(cl);
			EntityContextPluginRegistry.setEntityContextPluginProvider(ecpp);
			wi.flush();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			EntityContextPluginRegistry.setEntityContextPluginProvider(oldEntityContextPluginProvider);
		}
	}

}
