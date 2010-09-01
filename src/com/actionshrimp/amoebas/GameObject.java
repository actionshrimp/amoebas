package com.actionshrimp.amoebas;

import java.util.LinkedList;

public class GameObject {
	
	private LinkedList<Component> components;
	
	public GameObject() {
		components = new LinkedList<Component>();
	}
	
	public void registerComponent(Component c){
		if (!components.contains(c)) {
			components.add(c);
		}
	}
	
	public void removeComponent(Component c) {
		components.remove(c);
	}
	
	public void update(double dt) {
		for(Component c : components) {
			c.update();
		}
	}
	
}