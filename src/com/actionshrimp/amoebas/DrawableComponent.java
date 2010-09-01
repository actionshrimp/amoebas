package com.actionshrimp.amoebas;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

public class DrawableComponent extends Component {
	
	private static LinkedList<DrawableComponent> mDrawlist = new LinkedList<DrawableComponent>();
	private static ConcurrentLinkedQueue<DrawableComponent> mWaiting = new ConcurrentLinkedQueue<DrawableComponent>();
	
	public static void drawAll(GL10 gl) {
		
		mDrawlist.addAll(mWaiting);
		mWaiting.clear();
		
		for (DrawableComponent c : mDrawlist) {
			c.preDraw(gl);
			c.draw(gl);
			c.postDraw(gl);
		}
	}
	
	private Vector2 x;
	
	public DrawableComponent(GameObject owner) {
		super(owner);
		mWaiting.add(this);
	}
	
	public void update(Vector2 pos) {
		x = pos;
	}
	
	public void preDraw(GL10 gl) {
		
		gl.glPushMatrix();
		gl.glTranslatef(x.x, x.y, 0.0f);
		
		//Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
	}
	
	public void draw(GL10 gl) {			
	}
	
	public void postDraw(GL10 gl) {
	
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLoadIdentity();
		gl.glPopMatrix();
	
	}
	
}