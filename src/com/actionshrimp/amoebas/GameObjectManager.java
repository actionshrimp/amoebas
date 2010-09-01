package com.actionshrimp.amoebas;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameObjectManager extends GameObject {
	
	private LinkedList<GameObject> mObjects;
	private ConcurrentLinkedQueue<GameObject> mWaitingAdd = new ConcurrentLinkedQueue<GameObject>();
	private ConcurrentLinkedQueue<GameObject> mWaitingDel = new ConcurrentLinkedQueue<GameObject>();
	
	public GameObjectManager() {
		super();
		mObjects = new LinkedList<GameObject>();
	}
	
	public void addObject(GameObject o) {
		if (!mObjects.contains(o) && !mWaitingAdd.contains(o)) {
			mWaitingAdd.add(o);
		}
	}
	
	public void removeObject(GameObject o) {
		mWaitingDel.add(o);
	}
	
	public void update(double dt) {
		
		mObjects.removeAll(mWaitingDel);
		mWaitingDel.clear();
		
		mObjects.addAll(mWaitingAdd);
		mWaitingAdd.clear();
		
		for(GameObject o : mWaitingDel) {
			mObjects.remove(o);
		}
		
		for(GameObject o : mObjects) {
			o.update(dt);
		}
	}
	
}