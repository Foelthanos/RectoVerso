package com.rectoverso.model;

import java.util.ArrayList;

public class Map {

	protected ArrayList<Tile> tiles = new ArrayList<Tile>();
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	private Side side;
	
	public enum Side{
		RECTO,
		VERSO, 
		MERGED;
	}
	
	public Map(ArrayList<Tile> tiles, ArrayList<Entity> entities, Side side){
		this.tiles = tiles;
		this.entities = entities;
	}
	
	public ArrayList<Tile> getTiles(){
		return this.tiles;
	}
	
	public ArrayList<Entity> getEntities(){
		return this.entities;
	}
	
	public Side getSide(){
		return this.side;
	}
}
