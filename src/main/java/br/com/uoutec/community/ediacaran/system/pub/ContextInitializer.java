package br.com.uoutec.community.ediacaran.system.pub;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.uoutec.community.ediacaran.system.Constants;

public class ContextInitializer implements ServletContextListener,
	HttpSessionListener, ServletRequestListener{

	public ContextInitializer(){
	}
	
	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	public void requestInitialized(ServletRequestEvent arg0) {
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
	}

}
