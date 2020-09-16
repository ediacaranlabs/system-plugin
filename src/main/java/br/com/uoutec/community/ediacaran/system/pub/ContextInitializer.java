package br.com.uoutec.community.ediacaran.system.pub;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import br.com.uoutec.community.ediacaran.core.system.registry.MessageBundle;
import br.com.uoutec.community.ediacaran.core.system.tx.lock.NamedLockThreadContext;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.system.Constants;

public class ContextInitializer implements ServletContextListener,
	HttpSessionListener, ServletRequestListener{

	private NamedLockThreadContext namedLockThreadContext;
	
	public ContextInitializer(){
		this.namedLockThreadContext = new NamedLockThreadContext();
	}
	
	public void requestDestroyed(ServletRequestEvent arg0) {
		namedLockThreadContext.deactivate();
	}

	public void requestInitialized(ServletRequestEvent arg0) {
		this.namedLockThreadContext.activate();
		
		arg0.getServletRequest().setAttribute(
				Constants.CONTEXT_PATH_VAR, 
				arg0.getServletContext().getContextPath());
	}
	
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		arg0.getServletContext().setAttribute("lang-manager", EntityContextPlugin.getEntity(MessageBundle.class));
		JspApplicationContext jspAc = 
				JspFactory.getDefaultFactory()
				.getJspApplicationContext(arg0.getServletContext());
				
		jspAc.addELResolver(new PluginELResolver());
	}

}
