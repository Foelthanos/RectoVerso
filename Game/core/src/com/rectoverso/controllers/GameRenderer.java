package com.rectoverso.controllers;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rectoverso.RVGame;
import com.rectoverso.model.Tile.TileContent;

/**
 * Class that manage the render of the game screen. 
 * @author Brahim "Violacrimosum" Berkati
 * @version 0.1
 */
public class GameRenderer {
	
	private int ppuX, ppuY;
	
	private GameController gController;

	private TextureAtlas atlas;
	private ShapeRenderer shapeRenderer;
	//private SpriteBatch spriteBatch;
	
	private Hashtable<TileContent, TextureRegion> textureMap = new Hashtable<TileContent, TextureRegion>();
	
	public GameRenderer(GameController gController){
		//this.spriteBatch = new SpriteBatch();

		this.gController = gController;
		 
		this.gController.getCamera().setToOrtho(true, 800, 600);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(this.gController.getCamera().combined);
		
        //this.initTextureMap();
        
		ppuX = 1;
		ppuY = 1;
	}
	
	private void initTextureMap() {
		// TODO Auto-generated method stub
		atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
		this.textureMap.put(TileContent.BASE_GRASSGROUND, new TextureRegion(atlas.findRegion("")));
	}

	public void render() {
		Gdx.app.log(RVGame.LOG, "Render game model");
		 /*
         * 1. We draw a black background. This prevents flickering.
         */

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
         * 2. We draw the Filled rectangle
         */

        // Tells shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeType.Filled);

        // Chooses RGB Color of 87, 109, 120 at full opacity
        shapeRenderer.setColor(87 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.rect(gController.getRect().x, gController.getRect().y,
        		gController.getRect().width, gController.getRect().height);

        // Tells the shapeRenderer to finish rendering
        // We MUST do this every time.
        shapeRenderer.end();

        /*
         * 3. We draw the rectangle's outline
         */

        // Tells shapeRenderer to draw an outline of the following shapes
        shapeRenderer.begin(ShapeType.Line);

        // Chooses RGB Color of 255, 109, 120 at full opacity
        shapeRenderer.setColor(255 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Line)
        shapeRenderer.rect(gController.getRect().x, gController.getRect().y,
        		gController.getRect().width, gController.getRect().height);

        shapeRenderer.end();
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		//if( spriteBatch != null ) spriteBatch.dispose();
		if( atlas != null ) atlas.dispose();

	}
}
