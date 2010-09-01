package com.actionshrimp.amoebas;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class AmoebasActivity extends Activity implements SensorEventListener {

	/** The OpenGL View */
	private GLSurfaceView glSurface;
	
	private Thread mGameThread;
	
	public AmoebasThread mGame;
	public AmoebasRenderer mRenderer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		mRenderer = new AmoebasRenderer();
		
		//Create an Instance with this Activity
		glSurface = new GLSurfaceView(this);
		//Set our own Renderer
		glSurface.setRenderer(mRenderer);
		//Set the GLSurface as View to this Activity
		setContentView(glSurface);
		
		mGame = new AmoebasThread(this);
		mGameThread = new Thread(mGame);
		mGameThread.start();
		
	}

	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glSurface.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurface.onPause();
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