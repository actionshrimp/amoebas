package com.actionshrimp.amoebas;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class DrawableComponent extends Component {
		
	private static LinkedList<DrawableComponent> mDrawList = new LinkedList<DrawableComponent>();
	
	public static void drawAll(GL10 gl) {
		
		synchronized(mDrawList) {
			for (DrawableComponent c : mDrawList) {
				c.preDraw(gl);
				c.draw(gl);
				c.postDraw(gl);
			}
		}
		
	}
	
	private Vector2 x;
	private float[] rgba = {1.0f,1.0f,1.0f,1.0f};
	
	public DrawableComponent(GameObject owner, Vector2 position) {
		super(owner);
		setPosition(position);
	}
		
	public void register() {
		Component.mGame.mRenderer.addComponent(this);
	}
	
	public void update() {
	}
		
	public void setPosition(Vector2 position) {
		x = position;
	}
	
	public void setColor(float r, float g, float b, float a) {
		rgba[0] = r; rgba[1] = g; rgba[2] = b; rgba[3] = a;
	}
	
	public void preDraw(GL10 gl) {
		
		gl.glPushMatrix();
		gl.glTranslatef(x.x, x.y, 0.0f);
		
		//Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
		
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