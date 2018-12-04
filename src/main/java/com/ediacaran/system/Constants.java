package com.ediacaran.system;

import org.brandao.brutos.annotation.ScopeType;

public interface Constants {

	public static final String PROD                             = "production";
	
	public static final String CONTEXT_PATH_VAR                 = "context_path";
	
	public static final String LOCALE_VAR                       = "locale";

	public static final String LANGUAGE_VAR                     = "lang";

	public static final String COUNTRY_VAR                      = "country";
	
	public static final String DATABASE_TX                      = "DATABASE_TX";
	
	public static final String SYSTEM_USER_BEAN_NAME 			= "system_user";

	public static final String USER_HOST 						= "user_host";
	
	public static final String SYSTEM_USER_SCOPE				= ScopeType.SESSION;
	
	/* Vars */
	
	public static final String APP_CONFIGURATION                = br.com.uoutec.application.ApplicationContext.CONFIGURATION_VARNAME;//"app_config";
	
}
