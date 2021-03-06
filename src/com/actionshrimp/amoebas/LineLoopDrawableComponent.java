package com.actionshrimp.amoebas;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class LineLoopDrawableComponent extends DrawableComponent {

	private float mLineThickness;
	
	private LinkedList<Vector2> mVertices;
	private int mVertCount = 0;				//Count of vertices to save re-allocation
	private FloatBuffer mBuf;				//The buffer itself
	
	private boolean mInitialised = false;	//Used to check the vertices are initialised before drawing can take place
	
	public LineLoopDrawableComponent(GameObject owner, Vector2 position, LinkedList<Vector2> initVertices, float lineThickness) {
		super(owner, position);
		
		//Set the line thickness
		mLineThickness = lineThickness;
		
		//Initialise the vertex buffers
		mVertices = initVertices;
		update(); mInitialised = true;
	}
	
	public synchronized void update() {
		if (mVertCount != mVertices.size()) {
			
			//Store the vert count for later
			mVertCount = mVertices.size();
			
			//Allocate new vert buffer
			ByteBuffer byteBuf = ByteBuffer.allocateDirect((mVertCount) * 2 * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			mBuf = byteBuf.asFloatBuffer();
		}
		
		for(Vector2 v : mVertices) {
			mBuf.put(v.x);
			mBuf.put(v.y);
		}
		mBuf.position(0);
	}
	
	public synchronized void draw(GL10 gl) {
		if (mInitialised) {
			
				//Set the width
				gl.glLineWidth(mLineThickness);
				
				//Point to our vertex buffer
				gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mBuf);
				
				//Enable vertex buffer
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				
				//Draw the vertices as triangle strip
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, mVertCount);
		}
	}
	
}