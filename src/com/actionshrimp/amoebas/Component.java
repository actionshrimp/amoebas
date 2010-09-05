package com.actionshrimp.amoebas;

public class Component {
	
	protected GameObject mOwner;
	
	public Component(GameObject owner){
		mOwner = owner;
	}
	
	public void register() {
	}
	
	public void update() {	
	}
	
}