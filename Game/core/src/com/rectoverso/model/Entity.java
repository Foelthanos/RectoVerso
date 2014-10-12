package com.rectoverso.model;

import com.badlogic.gdx.math.Vector2;

public class Entity {

	protected Vector2 position = new Vector2();
	protected Vector2 velocity = new Vector2();
	
	public Entity(Vector2 position, Vector2 velocity){
		this.position = position;
		this.velocity = velocity;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public void setPosition(Vector2 position){
		this.position = position;
	}
}
