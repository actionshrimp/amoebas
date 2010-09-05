package com.actionshrimp.amoebas;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class AmoebasActivity extends Activity implements SensorEventListener {

	/** The OpenGL View */
	private GLSurfaceView mSurfaceView;
	private AmoebasGame mGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Amoebas", "onCreate");
		
		//Create an Instance with this Activity
		mSurfaceView = new GLSurfaceView(this);
		
		//Set the GLSurface as View to this Activity
		setContentView(mSurfaceView);
		
		//Create the game
		mGame = new AmoebasGame();
		mGame.registerGLSurface(mSurfaceView);
		
		//Allow the game to start handling input
		mGame.startInputDetection(this);
		
		mGame.boot();
	
	}

	@Override
	protected void onDestroy() {
		Log.d("Amoebas", "onDestroy");
		mGame.stop(); 
		mSurfaceView = null;
		mGame = null;
		super.onDestroy();
	}
	
	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mSurfaceView.onResume();
		mGame.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mSurfaceView.onPause();
		mGame.onPause();
	}

	public long mLastTouchTime;
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		mGame.onTouchEvent(e);
        
        final long time = System.currentTimeMillis();
        if (e.getAction() == MotionEvent.ACTION_MOVE && time - mLastTouchTime < 32) {
                // Sleep so that the main thread doesn't get flooded with UI events.
                try {
                    Thread.sleep(32);
                } catch (InterruptedException ex) {
                    // No big deal if this sleep is interrupted.
                }
                //amoebasGame.getRenderer().waitDrawingComplete();
        }
        mLastTouchTime = time;
		
		return true;
	}

	public void onSensorChanged(SensorEvent e) {		
	}
	
	public void onAccuracyChanged(Sensor s, int accuracy) {
	}
	
}