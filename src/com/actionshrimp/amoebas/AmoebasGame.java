package com.actionshrimp.amoebas;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class AmoebasGame {

	private Thread mThread;
	private AmoebasThread mGameThread;
	
	public GameObjectManager mManager;
	
	private GLSurfaceView mSurfaceView;
	public AmoebasRenderer mRenderer;
	public Camera mCamera;
	
	private InputHandler mInputHandler;
	
	private boolean mRunning;
	private boolean mInitialized;
	private boolean mInputReady;
	
	public AmoebasGame() {
			mRunning = false;
			mInitialized = false;
			mInputReady = false;
		
			mRenderer = new AmoebasRenderer();
			mInputHandler = new InputHandler(this);
			mGameThread = new AmoebasThread(this);
			mManager = new GameObjectManager();
	}
	
	public void boot() {
		if (!mInitialized) {
			
			Component.boot(this);
			
			mCamera = new Camera(0.0f, 0.0f, 600.0f, 300.0f);
			mRenderer.registerCamera(mCamera);
					
			mManager.addObject(new Amoeba(new Vector2(-200.0f, -100.0f)));
			mManager.addObject(new Amoeba(new Vector2(150.0f, -100.0f)));
			mManager.addObject(new Amoeba(new Vector2(-100.0f, -60.0f)));
			mManager.addObject(new Amoeba(new Vector2(200.0f, -30.0f)));
			mManager.addObject(new Amoeba(new Vector2(30.0f, 0.0f)));
			mManager.addObject(new Amoeba(new Vector2(-150.0f, 20.0f)));
			mManager.addObject(new Amoeba(new Vector2(80.0f, 50.0f)));
			mManager.addObject(new Amoeba(new Vector2(-120.0f, 75.0f)));
			
			start();			
		}
	}
	
	public void registerGLSurface(GLSurfaceView gl) {
		mSurfaceView = gl;
		mSurfaceView.setRenderer(mRenderer);
	}
	
	public void startInputDetection (Context c) {
		mInputHandler.registerContext(c);
		mInputReady = true;
	}
	
	public void onTouchEvent(MotionEvent e) {
		if (mInputReady) {
			mInputHandler.onTouchEvent(e);
		}
	}
	
	public void start() {
		if (!mRunning) {
			assert mThread == null;
			mThread = new Thread(mGameThread);
			mThread.setName("AmoebasGame");
			mThread.start();
			mRunning = true;
		} else {
			mGameThread.resumeGame();
		}
	}
	
	public void stop() {
		if (mRunning) {
			mGameThread.stopGame();
			Component.shutdown();
			try {
				mThread.join ();
			} catch (InterruptedException e) {
				mThread.interrupt();
			}
			
			mThread = null;
			mRunning = false;
		}
	}
	
	public void onPause() {
		mGameThread.pauseGame();
	}
	
	public void onResume() {
		mGameThread.resumeGame();
	}
	
	
}
