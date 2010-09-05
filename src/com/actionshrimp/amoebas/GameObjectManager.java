package com.actionshrimp.amoebas;

import java.util.LinkedList;

import android.util.Log;

public class GameObjectManager extends GameObject {
	
	private LinkedList<GameObject> mObjects;
	
	public GameObjectManager() {
		super();
		mObjects = new LinkedList<GameObject>();
	}
	
	public synchronized void addObject(GameObject o) {
			if (!(mObjects.contains(o))) {
				Log.d("SHRIMP", "added amoeba");
				mObjects.add(o);
			}
	}
	
	public synchronized void removeObject(GameObject o) {
			mObjects.remove(o);
	}
	
	public synchronized void update(double dt) {
			for(GameObject o : mObjects) {
				o.update(dt);
			}
	}
	
}