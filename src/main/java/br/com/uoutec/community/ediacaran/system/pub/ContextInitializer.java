package br.com.uoutec.community.ediacaran.system.pub;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import org.apache.naming.ContextAccessController;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.SessionContext;

import br.com.uoutec.community.ediacaran.core.system.tx.lock.NamedLockThreadContext;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.system.Constants;

public class ContextInitializer implements ServletContextListener,
	HttpSessionListener, ServletRequestListener{

	private NamedLockThreadContext namedLockThreadContext;
	
	private ThreadLocal<RequestContext> requestContext;
	
	//private ThreadLocal<ApplicationContext> applicationContext;
	
	private ThreadLocal<SessionContext> sessionContext;
	
	public ContextInitializer(){
		this.namedLockThreadContext = new NamedLockThreadContext();
		this.requestContext = new ThreadLocal<RequestContext>();
		//this.applicationContext = new ThreadLocal<ApplicationContext>();
		this.sessionContext = new ThreadLocal<SessionContext>();
	}
	
	public void requestDestroyed(ServletRequestEvent arg0) {
		
		//Deactivate request context
		RequestContext requestContext = this.requestContext.get();
		requestContext.deactivate();
		requestContext.invalidate();
		this.requestContext.remove();
		
		this.namedLockThreadContext.deactivate();
	}

	public void requestInitialized(ServletRequestEvent arg0) {
		
		//Active request context
		RequestContext requestContext = EntityContextPlugin.getEntity(RequestContext.class);
		requestContext.activate();
		this.requestContext.set(requestContext);

		this.namedLockThreadContext.activate();
		
		arg0.getServletRequest().setAttribute(
				Constants.CONTEXT_PATH_VAR, 
				arg0.getServletContext().getContextPath());
	}
	
	public void sessionCreated(HttpSessionEvent arg0) {
		
		//Active session context
		SessionContext sessionContext = EntityContextPlugin.getEntity(SessionContext.class);
		sessionContext.activate();
		this.sessionContext.set(sessionContext);
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		
		//Deactivate session context
		SessionContext sessionContext = this.sessionContext.get();
		sessionContext.deactivate();
		sessionContext.invalidate();
		this.sessionContext.remove();
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {

		//Deactivate application context
		//ApplicationContext applicationContext = this.applicationContext.get();
		//applicationContext.invalidate();
		//this.applicationContext.remove();
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		//Active session context
		//ApplicationContext applicationContext = EntityContextPlugin.getEntity(ApplicationContext.class);
		//applicationContext.
		//this.applicationContext.set(applicationContext);
		
		JspApplicationContext jspAc = 
				JspFactory.getDefaultFactory()
				.getJspApplicationContext(arg0.getServletContext());
				
		jspAc.addELResolver(new PluginELResolver());
	}

}
