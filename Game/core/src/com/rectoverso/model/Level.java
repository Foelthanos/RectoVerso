package com.rectoverso.model;

public class Level {


	public enum LevelState {
		PRINTED,LOADED
	};
	public enum LevelType {
		normal,secret,movie
	};
	
	private LevelState state = LevelState.PRINTED;
	private int number;
	private LevelType type;
	private String name;
	private String background;
	private boolean locked = true;
	
	public Level(int number, String name , LevelType type, boolean locked){
		this.number = number;
		this.name = name;
		this.type = type;
		this.locked = locked;
		
	}
	public void setLocked(boolean lock){
		if(this.locked && !lock){
			unlock();
		}
		else if (!this.locked && lock){
			lock();
		}
		
	}
	public boolean isLocked(){
		return locked;
	}
	private void unlock(){
		this.locked  = false;
	}
	private void lock(){
		this.locked = true;
	}

	public String getName(){
		return name;
	}
	public int getNumber(){
		return number;
	}
	public LevelType getType(){
		return type;
	}

}
