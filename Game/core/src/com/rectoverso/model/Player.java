package com.rectoverso.model;

import com.badlogic.gdx.math.Vector2;
import com.rectoverso.model.Map.Side;

public class Player extends Entity{

	public Player(Vector2 spawnCoordinate, Vector2 velocity, Side side) {
		super(spawnCoordinate, velocity, side);
		// TODO Auto-generated constructor stub
	}
	public Player(int spawnRow, int spawnCol, Vector2 velocity, Side side) {
		
		this(new Vector2(spawnRow,spawnCol), velocity, side);
		// TODO Auto-generated constructor stub
	}

	public enum State {
		IDLE, WALKING;
	}
	
	public enum WalkDirection {
		UP, DOWN, RIGHT, LEFT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT;
	}
	
	public static final float SPEED = 48f;
	public static final float SIZE = 32f;
	private float stateTime = 0;
	private State state = State.IDLE;
	private WalkDirection direction = WalkDirection.DOWN;
	
	public float getFootPositionX(){
		return (this.position.x)+54;
	}
	
	public float getFootPositionY(){
		return (this.position.y)+16;
	}
	
	public Vector2 getFootPosition(){
		return new Vector2((this.position.x)+54, (this.position.y)+16);
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	public State getState(){
		return this.state;
	}
	
	public float getStateTime() {
		return this.stateTime;
	}
	
	public void setWalkDirection(WalkDirection direction){
		this.direction = direction;
	}
	
	public WalkDirection getWalkDirection(){
		return this.direction;
	}
	
	public void update(float delta) {
		this.stateTime += delta;
		this.position.add(this.velocity.scl(delta));
	}

	public void setVelocityX(float speed) {
		// TODO Auto-generated method stub
		this.velocity.x = speed;
	}
	
	public void setVelocityY(float speed) {
		// TODO Auto-generated method stub
		this.velocity.y = speed;
	}
}
