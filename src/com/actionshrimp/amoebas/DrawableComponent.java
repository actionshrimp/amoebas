package com.actionshrimp.amoebas;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class DrawableComponent extends Component {
		
	private static List<DrawableComponent> mDrawList = Collections.synchronizedList(new LinkedList<DrawableComponent>());
	
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
	
	public DrawableComponent(GameObject owner, Vector2 position) {
		super(owner);
		setPosition(position);
	}
		
	public void register() {
		synchronized(mDrawList) {
			mDrawList.add(this);
		}
	}
	
	public void update() {
	}
		
	public void setPosition(Vector2 position) {
		x = position;
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