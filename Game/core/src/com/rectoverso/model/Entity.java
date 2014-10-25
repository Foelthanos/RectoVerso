package com.rectoverso.model;

import com.badlogic.gdx.math.Vector2;
import com.rectoverso.model.Map.Side;

public class Entity {

	protected Vector2 position = new Vector2();
	protected Vector2 velocity = new Vector2();
	protected Vector2 spawnCoordinates = new Vector2();
	protected Side spawnSide;
	
	public Entity(Vector2 spawnCoordinates, Vector2 velocity , Side spawnSide){
		this.spawnCoordinates = spawnCoordinates;
		this.velocity = velocity;
		this.spawnSide = spawnSide;
		setToSpawnPosition();
	}
	public void setToSpawnPosition(){
		this.position = new Vector2(64-16 + spawnCoordinates.x * 64 - spawnCoordinates.y * 64,
				64-16 - spawnCoordinates.x * 32 - spawnCoordinates.y * 32);
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public void setPosition(Vector2 position){
		this.position = position;
	}
}
