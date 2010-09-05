package com.actionshrimp.amoebas;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class TriangleFanDrawableComponent extends DrawableComponent {

	private LinkedList<Vector2> mVertices;
	private int mVertCount = 0;				//Count of vertices to save re-allocation
	private FloatBuffer mBuf;				//The buffer itself
	
	private boolean mInitialised = false;	//Used to check the vertices are initialised before drawing can take place
	
	public TriangleFanDrawableComponent(GameObject owner, Vector2 position, LinkedList<Vector2> initVertices) {
		super(owner, position);
			
		//Initialise the vertex buffers
		mVertices = initVertices;
		update(); mInitialised = true;
	}
	
	public synchronized void update() {
		if (mVertCount != mVertices.size()) {
			
			//Store the vert count for later
			mVertCount = mVertices.size();
			
			//Allocate new vert buffer
			ByteBuffer byteBuf = ByteBuffer.allocateDirect((mVertCount + 2) * 2 * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			mBuf = byteBuf.asFloatBuffer();
		}
		
		mBuf.put(0);
		mBuf.put(0);
		
		for(Vector2 v : mVertices) {
			mBuf.put(v.x);
			mBuf.put(v.y);
		}
		
		mBuf.put(mVertices.getFirst().x);
		mBuf.put(mVertices.getFirst().y);
		
		mBuf.position(0);
	}
		
	
	public synchronized void draw(GL10 gl) {
		if (mInitialised) {
				//Point to our vertex buffer
				gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mBuf);
				
				//Enable vertex buffer
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
								
				//Draw the vertices as triangle strip
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, mVertCount+2);
		}
	}
	
}