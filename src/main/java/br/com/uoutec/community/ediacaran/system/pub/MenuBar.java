package br.com.uoutec.community.ediacaran.system.pub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuBar {

	private List<Menu> list;
	
	public MenuBar(){
		this.list = new ArrayList<Menu>();
	}
	
	public List<Menu> getItens(){
		return this.list;
	}
	
	public void addMenu(Menu menu){
		if(this.list.contains(menu)){
			this.list.remove(menu);
		}
		
		this.list.add(menu);
		
		Collections.sort(this.list, new Comparator<Menu>(){

			public int compare(Menu o1, Menu o2) {
				return o2.getOrder() - o1.getOrder();
			}
			
		});
	}

	public Menu getMenu(String name){
		Menu item = new Menu();
		item.setName(name);
		int index = this.list.indexOf(item);
		
		if(index != -1){
			return this.list.get(index);
		}
		else{
			return null;
		}
	}
	
	public void removeMenu(Menu menu){
		this.list.remove(menu);
		
		Collections.sort(this.list, new Comparator<Menu>(){

			public int compare(Menu o1, Menu o2) {
				return o2.getOrder() - o1.getOrder();
			}
			
		});
	}
	
}
