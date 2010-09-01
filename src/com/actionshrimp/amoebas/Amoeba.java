package com.actionshrimp.amoebas;


public class Amoeba extends GameObject{
	
	double mWobbleCnt;			//Wobble tracker
	private int mVertCnt = 16;	//Blob vertex count
	
	private static float minR = 30.0f;
	private static float maxR = 60.0f;
	private static float minSubR = 6.0f;
	private static float maxSubR = 3.0f;
	
	private static int minWobbleF = 1;
	private static int maxWobbleF = 3;
	
	private static float minWobbleV = 0.2f;
	private static float maxWobbleV = 3.0f;
	
	private float mR; 		//Blob base radius
	private float mSubR; 	//Blob wobble radius
	
	private int xWobbleF;	//Horizontal wobble frequency
	private int yWobbleF;	//Vertical wobble frequency
	
	private float xWobbleV;	//Horizontal wobble offset speed
	private float yWobbleV;	//Vertical wobble offset speed
	
	private LineDrawableComponent mDrawer;
	
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
		
		mDrawer = new LineDrawableComponent(this, buildMembrane());
		mDrawer.update(position);
		mDrawer.setLineThickness(2.0f);
		registerComponent(mDrawer);
		
//		mDrawer.updateVertices(buildMembrane());
//		mDrawer.updateVertices(buildMembrane());
		
	}
	
	public void setPosition(Vector2 x) {
		mDrawer.update(x);
	}
	
	public float membraneX(double theta) {
		float x = (float)((mR + mSubR*Math.sin(xWobbleF * theta + mWobbleCnt * xWobbleV) + (mSubR/2.0f)*Math.sin(30*theta)) * Math.cos(theta));
		return x;
	}
	
	public float membraneY(double theta) {
		float y = (float)((mR + mSubR*Math.cos(yWobbleF * theta + mWobbleCnt * yWobbleV) + (mSubR/2.0f)*Math.sin(30*theta)) * Math.sin(theta));
		return y;
	}
	
	public Vector2[] buildMembrane() {
		
		Vector2[] v = new Vector2[mVertCnt];
		
		for (int i = 0; i < mVertCnt; i++) {
			v[i] = new Vector2(
						membraneX(i*2.0*Math.PI/(double)mVertCnt),
						membraneY(i*2.0*Math.PI/(double)mVertCnt)
					); 
		}
		
		return v;
	}
	

	public void update(double dt) {
		super.update(dt);
		
		mWobbleCnt += dt;
		mDrawer.updateVertices(buildMembrane());
		
	}
	
}
