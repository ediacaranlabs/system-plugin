package br.com.uoutec.community.ediacaran.system.pub;

public interface MenuBarManager {

	void registerMenuBar(String name, MenuBar menuBar) throws MenuBarManagerException;
	
	MenuBar getMenuBar(String name) throws MenuBarManagerException;
	
	void removeMenuBar(String name) throws MenuBarManagerException;
	
}
