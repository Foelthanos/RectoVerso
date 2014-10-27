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
	public String fileName;
	
	private String background;
	private boolean locked;
	private ArrayList<Map> maps = new ArrayList<Map>(2);
	private MergedMap mergedMap;
	private int sizeRow,sizeCol;
	//private ArrayList<Rooting> routings = new ArrayList<Rooting>(8);
	
	//instancier un niveau vide
	public static Level createEmptyLevel(int sizeRow , int sizeCol){
		Level emptyLevel = new Level();
		emptyLevel.loadEmptyLevel(sizeRow,sizeCol);
		return emptyLevel;
	}
	public Level(){
		this(0, "Unnamed", LevelType.NORMAL, false);
	}
	public Level(int number, String name, LevelType type, boolean locked){
		this.number = number;
		this.name = name;
		this.type = type;
		this.locked = locked;
		this.state = LevelState.PRINTED;
	}
	
	public void loadLevel(String background, ArrayList<Map> maps, int sizeRow , int sizeCol, Player player) throws LevelException{
		
		if(maps.size() != 2)
			throw new LevelException("Le niveau doit être composé de deux cartes.");
		if(player == null)
			throw new LevelException("Ce niveau ne comporte pas de joueur");
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		
		this.maps = maps;
		this.background = background;
		
		this.mergedMap = new MergedMap(this.maps.get(0).getTiles(), this.maps.get(0).getEntities(), player);
		this.mergedMap.sideFocus = Side.RECTO;
		this.state = LevelState.LOADED;
	}
	public void loadEmptyLevel(int sizeRow , int sizeCol){
		ArrayList<Map> maps = new ArrayList<Map>();
		Map recto = new Map(Map.createEmptyMap(sizeRow , sizeCol), new ArrayList<Entity>(),Side.RECTO);
		Map verso = new Map(Map.createEmptyMap(sizeCol , sizeRow), new ArrayList<Entity>(),Side.VERSO);
		maps.add(recto);
		maps.add(verso);
		Player player = new Player(new Vector2(2, 3), new Vector2(2, 1),Side.RECTO);
		try {
			loadLevel("",maps,sizeRow,sizeCol,player);
		} catch (LevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void unloadLevel(){
		this.sizeRow = 0;
		this.sizeCol = 0;
		
		this.maps = null;
		this.background = null;
		
		this.mergedMap = null;
		this.state = LevelState.PRINTED;
	}
	
	public MergedMap getMergedMap(){
		return this.mergedMap;
	}
	public Map getMap(Map.Side side){
		if (side == Side.MERGED) return getMergedMap();
		
		for (Map m : this.maps){
			if (m == null) System.out.println("Cette carte n'existe pas !");
			if(m.getSide() == side) return m;
		}
		return null;
	}
	
	public void setLocked(boolean lock){
		if(this.locked && !lock){
			unlock();
		}
		else if (!this.locked && lock){
			lock();
		}
	}
	
	public int getSizeRow(){
		return this.sizeRow;
	}
	public int getSizeCol(){
		return this.sizeCol;
	}
	public String getBackground(){
		return background;
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
	public void setName(String name){
		this.name = name;
	}
	
	public int getNumber(){
		return number;
	}
	
	public LevelType getType(){
		return type;
	}

	public void setMergedMap(Side side) {
		if(side == Side.MERGED) return;
		if (side == Side.RECTO){
			this.mergedMap.setTiles(this.maps.get(0).getTiles());
			this.mergedMap.setEntities(this.maps.get(0).getEntities());
			this.mergedMap.sideFocus = Side.RECTO;
		}
		else{
			this.mergedMap.setTiles(this.maps.get(1).getTiles());
			this.mergedMap.setEntities(this.maps.get(1).getEntities());
			this.mergedMap.sideFocus = Side.VERSO;
		}
	}
	public void resizeMap(int row, int col) {
		// TODO Auto-generated method stub
		
		
		this.getMap(Side.RECTO).resize(row, col, this);
		this.getMap(Side.VERSO).resize(col, row, this);
		
		this.sizeRow = row;
		this.sizeCol = col;
		
		this.mergedMap.setTiles(this.getMap(Side.RECTO).getTiles());
	}

}
