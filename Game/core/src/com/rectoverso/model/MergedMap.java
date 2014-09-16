package com.rectoverso.model;

import java.util.ArrayList;

public class MergedMap extends Map{
	
	private Player player;
	
	public MergedMap(ArrayList<Tile> tiles, ArrayList<Entity> entities) {
		super(tiles, entities);
	}

}
