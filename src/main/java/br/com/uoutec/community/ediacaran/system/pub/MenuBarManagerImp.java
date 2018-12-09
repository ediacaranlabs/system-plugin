package br.com.uoutec.community.ediacaran.system.pub;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Singleton;

@Singleton
public class MenuBarManagerImp implements MenuBarManager{

	private ConcurrentMap<String, MenuBar> map;
	
	public MenuBarManagerImp(){
		this.map = new ConcurrentHashMap<String, MenuBar>();
	}
	
	@Override
	public void registerMenuBar(String name, MenuBar menuBar) throws MenuBarManagerException {
		
		if(this.map.containsKey(name)){
			throw new MenuBarManagerException("menu bar has already been registered: " + name);
		}
		
		this.map.put(name, menuBar);
		
	}

	@Override
	public MenuBar getMenuBar(String name) throws MenuBarManagerException {
		
		MenuBar menubar = this.map.get(name);
		
		if(menubar == null){
			throw new MenuBarManagerException("menu bar not found: " + name);
		}
		
		return menubar;
	}

	@Override
	public void removeMenuBar(String name) throws MenuBarManagerException {
		map.remove(name);
	}

}
