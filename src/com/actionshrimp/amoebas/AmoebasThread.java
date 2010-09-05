package com.actionshrimp.amoebas;

import android.view.MotionEvent;

class AmoebasThread implements Runnable {
	
	private AmoebasActivity mActivity;
	private InputHandler mInputHandler;
	public volatile GameObjectManager mManager;
	public Camera mCamera;
	
//	private Vector2 lastDragPos;
	
	private boolean finished = false;
	private long prevTime;
	
	public AmoebasThread(AmoebasActivity activity) {
		mActivity = activity;
		mCamera = new Camera(0.0f, 0.0f, 600.0f, 300.0f);
		activity.mRenderer.registerCamera(mCamera);
		mInputHandler = new InputHandler(mActivity);
	}
	
	public void run() {
		
		mManager = new GameObjectManager();

		mManager.addObject(new Amoeba(new Vector2(-200.0f, -100.0f)));
		mManager.addObject(new Amoeba(new Vector2(150.0f, -100.0f)));
		mManager.addObject(new Amoeba(new Vector2(-100.0f, -60.0f)));
		mManager.addObject(new Amoeba(new Vector2(200.0f, -30.0f)));
		mManager.addObject(new Amoeba(new Vector2(30.0f, 0.0f)));
		mManager.addObject(new Amoeba(new Vector2(-150.0f, 20.0f)));
		mManager.addObject(new Amoeba(new Vector2(80.0f, 50.0f)));
		mManager.addObject(new Amoeba(new Vector2(-120.0f, 75.0f)));
		
		prevTime = System.nanoTime();
		
		while(!finished) {
			
			long curTime = System.nanoTime();
			double dt = (curTime - prevTime)/1000000000.0f;
			prevTime = curTime;
			mManager.update(dt);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onTouchEvent(MotionEvent e) {
		mInputHandler.onTouchEvent(e);
	}
	
	public void finish() {
		finished = true;
	}
	
}