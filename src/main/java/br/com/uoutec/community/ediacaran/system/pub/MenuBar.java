package br.com.uoutec.community.ediacaran.system.pub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuBar {

	private List<Menu> list;
	private Map<String, Menu> map;
	
	public MenuBar(){
		this.list = new ArrayList<Menu>();
		this.map = new HashMap<String, Menu>();
	}
	
	public List<Menu> getItens(){
		return this.list;
	}
	
	public void addMenu(Menu menu){
		synchronized (this) {
			if(map.containsKey(menu.getName())) {
				throw new IllegalStateException("menu exist: " + menu.getName());
			}
			
			this.list.add(menu);
			this.map.put(menu.getName(), menu);
			
			Collections.sort(this.list, new Comparator<Menu>(){

				public int compare(Menu o1, Menu o2) {
					return o2.getOrder() - o1.getOrder();
				}
				
			});
			
		}
	}

	public Menu getMenu(String name){
		return map.get(name);
	}
	
	public void removeMenu(Menu menu){
		synchronized (this) {
			Menu m = this.map.get(menu.getName());
			
			if(m == null) 
				return;
			
			list.remove(m);
			map.remove(m.getName());
		}
	}
	
}
