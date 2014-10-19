package com.rectoverso.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	public enum TileContent {
		BASE_GRASSGROUND,
		BASE_TECHNOGROUND, 
		NOTHING;
	};

	public enum TileCollision{
		NO_COLLISION,
		COLLISION;
	};

	public static final float SIZE = 128f;

	private TileContent content;
	private TileCollision collision;
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	
	public Tile(int col, int row, TileContent content, TileCollision collision) 
	{
		float posX = 64 * col - 64 * row;
		float posY = - 32 * col - 32 * row;
		this.position = new Vector2(posX,posY);
		this.collision = collision;
		this.content = content;
		this.bounds.height = Tile.SIZE;
		this.bounds.width = Tile.SIZE;
	}
	
	public TileContent getTileContent(){
		return this.content;
	}
	
	public void setTileContent(TileContent type){
		this.content = type;
	}
	
	public TileCollision getTileCollision(){
		return this.collision;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public String toString(){
		return "Tile at ("+this.position.x+","+this.position.y+")";
	}
}
