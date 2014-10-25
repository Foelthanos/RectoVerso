package com.rectoverso.model;

import java.util.ArrayList;

import com.rectoverso.model.Tile.TileCollision;
import com.rectoverso.model.Tile.TileContent;

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
		this.side = side;
	}
	
	public static ArrayList<Tile> createEmptyMap(int sizeRow , int sizeCol){
		ArrayList<Tile> map = new ArrayList<Tile>();
		for (int row = 0 ; row < sizeRow ; row ++){
			for (int col = 0 ; col < sizeCol ; col ++){
				map.add(new Tile(col, row, TileContent.NOTHING, TileCollision.COLLISION));
			}
		}
		return map;
	}
	
	public ArrayList<Tile> getTiles(){
		return this.tiles;
	}
	public Tile getTile(int row, int col , Level lvl){
		return this.tiles.get(row * lvl.getSizeCol() + col );
	}
	public void setTileContent(int row, int col , Level lvl , TileContent type){
		Tile tile = this.tiles.get(row * lvl.getSizeCol() + col );
		this.setTileContent(tile, type);
	}
	public void setTileContent(Tile tile, TileContent type) {
		tile.setTileContent(type);
	}
	
	public ArrayList<Entity> getEntities(){
		return this.entities;
	}
	
	public Side getSide(){
		return this.side;
	}

	
}
