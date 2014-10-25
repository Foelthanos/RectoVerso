package com.rectoverso.model;

import com.badlogic.gdx.math.Vector2;
import com.rectoverso.model.Map.Side;

public class ReactiveEntity extends Entity {

	public ReactiveEntity(Vector2 spawnCoordinate, Vector2 velocity, Side side) {
		super(spawnCoordinate, velocity, side);
		// TODO Auto-generated constructor stub
	}
	public ReactiveEntity(int spawnRow, int spawnCol, Vector2 velocity, Side side) {
		
		this(new Vector2(spawnRow,spawnCol), velocity, side);
		// TODO Auto-generated constructor stub
	}

}
