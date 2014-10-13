package com.rectoverso.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.rectoverso.model.Map.Side;
import com.rectoverso.utils.LevelException;

public class Level {


	public enum LevelState {
		PRINTED,LOADED;
	};
	public enum LevelType {
		NORMAL,SECRET,MOVIE;
	};
	
	private LevelState state;
	private int number;
	private LevelType type;
	private String name;
	private String background;
	private boolean locked;
	private ArrayList<Map> maps = new ArrayList<Map>(2);
	private MergedMap mergedMap;
	private int scaleX,scaleY;
	//private ArrayList<Rooting> routings = new ArrayList<Rooting>(8);
	
	//instancier un niveau vide
	public static Level createEmptyLevel(int size){
		ArrayList<Map> maps = new ArrayList<Map>();
		Map recto = new Map(Map.createEmptyMap(size), new ArrayList<Entity>(),Side.RECTO);
		Map verso = new Map(Map.createEmptyMap(size), new ArrayList<Entity>(),Side.VERSO);
		maps.add(recto);
		maps.add(verso);
		Player player = new Player(new Vector2(0, 0), new Vector2(2, 1));
		try {
			return new Level(0,"",LevelType.NORMAL,true,"",maps,size,size,player);
		} catch (LevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Level(int number, String name, LevelType type, boolean locked){
		this.number = number;
		this.name = name;
		this.type = type;
		this.locked = locked;
		this.state = LevelState.PRINTED;
	}
	
	public Level(int number, String name, LevelType type, boolean locked, 
			String background, ArrayList<Map> maps, int scaleX, int scaleY, Player player) throws LevelException{
		this(number, name, type, locked);
		if(maps.size() != 2)
			throw new LevelException("Le niveau doit être composé de deux cartes.");
		if(player == null)
			throw new LevelException("Ce niveau ne comporte pas de joueur");
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.maps = maps;
		this.background = background;
		
		this.mergedMap = new MergedMap(this.maps.get(0).getTiles(), this.maps.get(0).getEntities(), player);
		this.state = LevelState.LOADED;
	}
	
	public MergedMap getMergedMap(){
		return this.mergedMap;
	}
	
	public void setLocked(boolean lock){
		if(this.locked && !lock){
			unlock();
		}
		else if (!this.locked && lock){
			lock();
		}
	}
	
	public int getScaleX(){
		return this.scaleX;
	}
	
	public int getScaleY(){
		return this.scaleY;
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
