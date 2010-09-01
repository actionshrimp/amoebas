package com.actionshrimp.amoebas;

public class Camera extends GameObject {
	
	public Vector2 x;
	public Vector2 w;
	
	public Vector2 screenSize;
	
	private float scale = 1.0f;
	
	public Camera(float xPos, float yPos, float width, float height) {
		x = new Vector2(xPos, yPos);
		w = new Vector2(width, height);
	}
	
	public void move(float xPos, float yPos) {
		x.set(xPos, yPos);
	}
	
	public void pan(Vector2 panVector) {
		panVector.multiply(scale);
		x.add(panVector);
	}
	
	public void zoom(float scaleFactor) {
		scale /= scaleFactor;
		w.set(w.x/scaleFactor, w.y/scaleFactor);		
	}
	
	//Transforms screen coordinate to game coordinates
	public Vector2 gameCoordinates(float screenX, float screenY){
		return new Vector2(x.x + (screenX - screenSize.x/2.0f)*scale, x.y + (screenY - screenSize.y/2.0f)*scale);
	}
	
}