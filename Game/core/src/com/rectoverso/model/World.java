package com.rectoverso.model;

import java.util.ArrayList;

public class World {

	private String name;
	private int number;
	private boolean locked = true;
	private ArrayList<Level> levels;
	
	public World (int number, String name ){
		levels = new ArrayList<Level>();
		this.number = number;
		this.name = name;
	}
	
	public void addLevel(Level level){
		this.levels.add(level);
	}
	
	public ArrayList<Level> getLevels(){
		return levels;
	}
	
	public boolean isLocked(){
		return locked;
	}

	public void setLocked(boolean lock){
		if(this.locked && !lock){
			unlock();
		}
		else if (!this.locked && lock){
			lock();
		}
		
	}
	private void unlock(){
		this.locked = false;
		//quand on débloque un monde, logiquement, le premier niveau l'est aussi
		levels.get(0).setLocked(false);
	}
	private void lock(){
		this.locked = true;
		//Quitter à bloquer un monde, on bloque aussi les niveaux qui s'y trouve
		for (Level lvl : levels){
			lvl.setLocked(true);
		}
	}
	
	public String getName(){
		return name;
	}
	public int getNumber(){
		return number;
	}
}
