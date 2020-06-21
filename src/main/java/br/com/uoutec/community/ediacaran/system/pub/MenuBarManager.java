package br.com.uoutec.community.ediacaran.system.pub;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

public interface MenuBarManager extends PublicBean{

	void registerMenuBar(String name, MenuBar menuBar) throws MenuBarManagerException;
	
	MenuBar getMenuBar(String name) throws MenuBarManagerException;
	
	void removeMenuBar(String name) throws MenuBarManagerException;
	
}
