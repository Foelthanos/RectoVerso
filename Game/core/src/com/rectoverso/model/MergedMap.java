package com.rectoverso.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class MergedMap extends Map{
	
	private Player player;
	
	public MergedMap(ArrayList<Tile> tiles, ArrayList<Entity> entities, Player player) {
		super(tiles, entities, Side.MERGED);
		this.player = player;
	}

	public Player getPlayer(){
		return this.player;
	}

	public void setPlayer(Player player2) {
		// TODO Auto-generated method stub
		this.player = player;
	}

	public Tile getTile(Vector2 tmpTilePos, int levelSize) {
		// TODO Auto-generated method stub
		
		return this.tiles.get((int)(tmpTilePos.y)*levelSize + (int)(tmpTilePos.x));
	}
}
