package com.rectoverso.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Class that manage the render of the game screen. 
 * @author Brahim "Violacrimosum" Berkati
 * @version 0.1
 */
public class GameRenderer {
	
	private int ppuX, ppuY;
	
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	private SpriteBatch spriteBatch;
	
	public GameRenderer(){
		this.spriteBatch = new SpriteBatch();

		camera = new OrthographicCamera(400,400);
		
		
		ppuX = 1;
		ppuY = 1;
	}
	
	public void render() {
		spriteBatch.begin();
		spriteBatch.end();
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		if( spriteBatch != null ) spriteBatch.dispose();
		if( atlas != null ) atlas.dispose();

	}
}
