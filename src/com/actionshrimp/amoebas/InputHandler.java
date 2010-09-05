package com.actionshrimp.amoebas;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class InputHandler {
	
	private AmoebasActivity mActivity;
	
	private GestureDetector mDetector;
	private ScaleGestureDetector mScaleDetector;
	
	public InputHandler(AmoebasActivity activity) {
		mActivity = activity;
		mDetector = new GestureDetector(activity, new SingleListener());
		mScaleDetector = new ScaleGestureDetector(activity, new ScaleListener());
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		Log.d("EVENT", "registered event:" + Integer.toString((e.getPointerCount())));
		if (e.getPointerCount() > 1) {
			return mScaleDetector.onTouchEvent(e);
		} else {
			return mDetector.onTouchEvent(e);
		}
	}
	
	private class SingleListener extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onDown(MotionEvent e) {
			Log.d("onDown",e.toString());
			return false;
		}
		
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Amoeba a = new Amoeba(mActivity.mGame.mCamera.gameCoordinates(e.getX(),e.getY()));
			Log.d("SHRIMP", "adding amoeba");
			mActivity.mGame.mManager.addObject(a);
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.d("onFling",e1.toString());
			//mActivity.mCamera.pan(velocityX, velocityY);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.d("onLongPress",e.toString());
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Vector2 x = new Vector2(distanceX, distanceY);
			if (x.length2() < 5000.0) { 
				mActivity.mGame.mCamera.pan(x);
			}
			return true;
		}
		
	}
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.d("onScale",detector.toString());
			mActivity.mGame.mCamera.zoom(detector.getScaleFactor());
			return true;
		}
	
	}
	
}