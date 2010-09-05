package com.actionshrimp.amoebas;

import java.util.LinkedList;

public class Amoeba extends GameObject{
	
	//Blob class parameters
	private static float minR = 30.0f;
	private static float maxR = 60.0f;
	private static float minSubR = 8.0f;
	private static float maxSubR = 6.0f;
	
	private static int minWobbleF = 1;
	private static int maxWobbleF = 3;
	
	private static float minWobbleV = 0.2f;
	private static float maxWobbleV = 3.0f;
	
	//Blob instance parameters
	private float mR; 		//Blob base radius
	private float mSubR; 	//Blob wobble radius
	
	private int xWobbleF;	//Horizontal wobble frequency
	private int yWobbleF;	//Vertical wobble frequency
	
	private float xWobbleV;	//Horizontal wobble offset speed
	private float yWobbleV;	//Vertical wobble offset speed
	
	double mWobbleCnt;	//Wobble tracker
	
	private int mVertCnt = 8;											//Blob vertex count
	private LineDrawableComponent mDrawer;								//Drawing component
	private volatile LinkedList<Vector2> mVertices = new LinkedList<Vector2>();	//List of vertices for internal reference
	
	public Amoeba(Vector2 position) {
		super();			
		
		//Blob base radius
		mR = Amoeba.minR + (Amoeba.maxR - Amoeba.minR)*(float)Math.random();
		//Blob wobble radius
		mSubR = Amoeba.minSubR + (Amoeba.maxSubR - Amoeba.minSubR)*(float)Math.random();
		mSubR = mR / mSubR;
		
		//Wobble frequencies
		xWobbleF = Amoeba.minWobbleF + Math.round((Amoeba.maxWobbleF - Amoeba.minWobbleF)*(float)Math.random());
		yWobbleF = Amoeba.minWobbleF + Math.round((Amoeba.maxWobbleF - Amoeba.minWobbleF)*(float)Math.random());
		
		//Wobble offset speeds
		xWobbleV = Amoeba.minWobbleV + (Amoeba.maxWobbleV - Amoeba.minWobbleV)*(float)Math.random();
		yWobbleV = Amoeba.minWobbleV + (Amoeba.maxWobbleV - Amoeba.minWobbleV)*(float)Math.random();
		
		//Randomize wobble direction
		xWobbleV *= (Math.round(Math.random()) == 1) ? 1 : -1;
		yWobbleV *= (Math.round(Math.random()) == 1) ? 1 : -1;
		
		//Initialize the vertices
		for (int i = 0; i < mVertCnt; i++) {
			mVertices.add(new Vector2(0.0f,0.0f));
		}
		
		//Build an initial membrane for drawing component initialization
		buildMembrane(0);
		
		//Register the drawing component
		mDrawer = new LineDrawableComponent(this, position, mVertices, 2.0f);
		registerComponent(mDrawer);
		
	}
	
	public void setPosition(Vector2 x) {
		mDrawer.setPosition(x);
	}
	
	public float membraneX(double theta) {
		float x = (float)((mR + mSubR*Math.sin(xWobbleF * theta + mWobbleCnt * xWobbleV) + (mSubR/2.0f)*Math.sin(30*theta)) * Math.cos(theta));
		return x;
	}
	
	public float membraneY(double theta) {
		float y = (float)((mR + mSubR*Math.cos(yWobbleF * theta + mWobbleCnt * yWobbleV) + (mSubR/2.0f)*Math.sin(30*theta)) * Math.sin(theta));
		return y;
	}
	
	public void buildMembrane(double dt) {
	
		mWobbleCnt += dt;
		
		int i = 0;
		synchronized(this) {
			for (Vector2 v : mVertices) {
				v.set(
						membraneX(i*2.0*Math.PI/(double)mVertCnt),	
						membraneY(i*2.0*Math.PI/(double)mVertCnt)
						);
				i++;
			}
		}
		
	}

	public void update(double dt) {		
		buildMembrane(dt);
		super.update(dt);		
	}
	
}
