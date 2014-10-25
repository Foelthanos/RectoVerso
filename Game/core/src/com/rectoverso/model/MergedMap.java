package com.rectoverso.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class MergedMap extends Map{
	
	private Player player;
	public Side sideFocus;
	
	public MergedMap(ArrayList<Tile> tiles, ArrayList<Entity> entities, Player player) {
		super(tiles, entities, Side.MERGED);
		this.player = player;
	}

	public Player getPlayer(){
		return this.player;
	}

	public void setPlayer(Player player) {
		// TODO Auto-generated method stub
		this.player = player;
	}

	public Tile getTile(Vector2 tmpTilePos, int levelSize) {
		// TODO Auto-generated method stub
		
		return this.tiles.get((int)(tmpTilePos.y)*levelSize + (int)(tmpTilePos.x));
	}
	
	public Tile getTile(int row, int col, Level lvl)
	{
		if (sideFocus == Side.RECTO)
		{
			return this.tiles.get(row * lvl.getSizeCol() + col );
		}
		else
		{
			return this.tiles.get(row * lvl.getSizeRow() + col );
		}
		
		/*public Tile getTile(int row, int col , Level lvl){
			return this.tiles.get(row * lvl.getSizeCol() + col );
		}*/
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}
