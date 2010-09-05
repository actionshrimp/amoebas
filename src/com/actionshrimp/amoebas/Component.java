package com.actionshrimp.amoebas;

public class Component {
	
	protected static AmoebasGame mGame;
	
	public static void boot(AmoebasGame game) {
		mGame = game;
	}
	
	public static void shutdown() {
		mGame = null;
	}
	
	protected GameObject mOwner;
	
	public Component(GameObject owner){
		mOwner = owner;
	}
	
	public void register() {
	}
	
	public void update() {	
	}
	
}