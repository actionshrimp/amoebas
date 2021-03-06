package com.actionshrimp.amoebas;

import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class AmoebasRenderer implements Renderer {
	
	private LinkedList<DrawableComponent> mDrawList;
	private final Object mDrawListLock;
	
	private Camera mCamera;
	public Vector2 screenSize;
	
	public AmoebasRenderer() {
		mDrawList = new LinkedList<DrawableComponent>();
		mDrawListLock = new Object();
	}
	
	public void registerCamera(Camera c) {
		mCamera = c;
	}
	
	public void addComponent(DrawableComponent c) {
		synchronized(mDrawListLock) {
			if (!(mDrawList.contains(c))){
				mDrawList.add(c);
			}
		}
	}
	
	public void removeComponent(DrawableComponent c) {
		synchronized(mDrawListLock) {
			mDrawList.remove(c);	
		}
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		//gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
//		gl.glEnable(GL10.GL_LINE_SMOOTH);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		
		//gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
	}

	/**
	 * Here we do our drawing
	 */
	public void onDrawFrame(GL10 gl) {
		//Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	

		setView(gl, -mCamera.w.x/2.0f, mCamera.w.x/2.0f, mCamera.w.y/2.0f, -mCamera.w.y/2.0f);
		
		gl.glTranslatef(-mCamera.x.x, -mCamera.x.y, 0.0f);
		
		synchronized(mDrawListLock) {
			for (DrawableComponent c : mDrawList) {
				c.preDraw(gl);
				c.draw(gl);
				c.postDraw(gl);
			}
		}
	}

	
	public void setView(GL10 gl, float left, float right, float bottom, float top) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrthof(left, right, bottom, top,-1.0f,1.0f);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);		// Select The Modelview Matrix
		gl.glLoadIdentity();					// Reset The Modelview Matrix
	}
	
	/**
	 * If the surface changes, reset the view
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		mCamera.screenSize = new Vector2(width, height);
		
		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		//setView(gl, -width/2.0f, width/2.0f, height/2.0f, -height/2.0f);	
	}

}
