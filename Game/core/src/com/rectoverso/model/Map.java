package com.rectoverso.model;

import java.util.ArrayList;

public class Map {

	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Map(ArrayList<Tile> tiles, ArrayList<Entity> entities){
		this.tiles = tiles;
		this.entities = entities;
	}
}
