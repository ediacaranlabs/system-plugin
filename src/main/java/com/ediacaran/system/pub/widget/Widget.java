package com.ediacaran.system.pub.widget;

public class Widget {

	private String name;
	
	private String resource;
	
	private int order;

	public Widget(String name, String resource, int order) {
		this.name = name;
		this.resource = resource;
		this.order = order;
	}

	public String getResource() {
		return resource;
	}

	public String getName() {
		return name;
	}

	public int getOrder() {
		return order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Widget other = (Widget) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
