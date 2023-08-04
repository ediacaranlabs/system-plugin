package br.com.uoutec.community.ediacaran.system.event;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface EventRegistry extends PublicBean{

	void info(String group, String subgroup, String message);
	
	void warn(String group, String subgroup, String message, Throwable ex);

	void warn(String group, String subgroup, String message);
	
	void error(String group, String subgroup, String message);
	
	void error(String group, String subgroup, String message, Throwable ex);
	
}
