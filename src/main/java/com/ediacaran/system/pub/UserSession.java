package com.ediacaran.system.pub;

import org.brandao.brutos.Invoker;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebScopeType;

import com.ediacaran.system.Constants;
import com.ediacaran.system.entity.SystemUser;

public class UserSession {

	public static void signin(SystemUser user){
		Scope scope = 
				Invoker.getCurrentApplicationContext().getScopes().get(WebScopeType.SESSION);
			
		scope.put(Constants.SYSTEM_USER_BEAN_NAME, user);
	}

	public static SystemUser getCurrentUser(){
		Scope scope = 
				Invoker.getCurrentApplicationContext().getScopes().get(WebScopeType.SESSION);
			
		return (SystemUser) scope.get(Constants.SYSTEM_USER_BEAN_NAME);
	}
	
	public static void signout(){
		Invoker
			.getCurrentApplicationContext()
			.getScopes()
			.get(WebScopeType.SESSION)
			.put(Constants.SYSTEM_USER_BEAN_NAME, null);
	}
	
}
