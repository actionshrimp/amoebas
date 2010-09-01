package com.actionshrimp.amoebas;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class LineDrawableComponent extends DrawableComponent {

		
	private FloatBuffer[] mBufs = new FloatBuffer[2];	//The buffers themselves
	private int[] mVcnt = {0, 0};					//Count of vertices to save re-allocation
	private int mSelBuf = 0; 							//Tracks which vertex buffer to use
	
	private float mLineThickness = 1.0f;
	
	public LineDrawableComponent(GameObject owner, Vector2[] initVertices) {
		super(owner);
		
		//Initialise the vertex buffers
		for (int i = 0; i < 2; i++) {
			updateVertices(initVertices);
		}
		
	}
	
	public void updateVertices(Vector2[] vertices) {
		if (mVcnt[mSelBuf] != vertices.length) {
			//Store the vert count for later
			mVcnt[mSelBuf] = vertices.length;
			
			//Allocate new vert buffer
			ByteBuffer byteBuf = ByteBuffer.allocateDirect(mVcnt[mSelBuf] * 3 * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			mBufs[mSelBuf] = byteBuf.asFloatBuffer();
		}
		
		for(int v = 0; v < vertices.length; v++) {
			mBufs[mSelBuf].put(vertices[v].x);
			mBufs[mSelBuf].put(vertices[v].y);
			mBufs[mSelBuf].put(0.0f);
		}
		mBufs[mSelBuf].position(0);
		
		mSelBuf = (mSelBuf + 1) % 2;	
	}
	
	public void setLineThickness(float t) {
		mLineThickness = t;
	}
	
	public void draw(GL10 gl) {
		//Select the buffer that isn't being used by the vertex loader
		int drawBuf = (mSelBuf+1)%2;
		
		//Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBufs[drawBuf]);
		
		//Enable vertex buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		//Set the width
		gl.glLineWidth(mLineThickness);
		
		//Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, mVcnt[drawBuf]);
	}
	
}