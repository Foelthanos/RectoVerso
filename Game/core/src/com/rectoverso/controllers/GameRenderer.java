package com.rectoverso.controllers;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rectoverso.model.Map.Side;
import com.rectoverso.model.Player;
import com.rectoverso.model.Player.State;
import com.rectoverso.model.Player.WalkDirection;
import com.rectoverso.model.Tile;
import com.rectoverso.model.Tile.TileContent;

//import com.sun.prism.GraphicsPipeline.ShaderType;
import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetResult;

/**
 * Class that manage the render of the game screen. 
 * @author Brahim "Violacrimosum" Berkati
 * @version 0.1
 */
public class GameRenderer {

	private int ppuX, ppuY;

	private GameController gController;

	private static final float RUNNING_FRAME_DURATION = 0.15f;

	private TextureAtlas atlas;
	private SpriteBatch spriteBatch;

	private Hashtable<TileContent, TextureRegion> textureMap = new Hashtable<TileContent, TextureRegion>();
	private Hashtable<WalkDirection, Animation> animationMap = new Hashtable<WalkDirection, Animation>();


	public GameRenderer(GameController gController){
		this.spriteBatch = new SpriteBatch();

		this.gController = gController;

		this.gController.getCamera().setToOrtho(false, 800, 600);
		this.spriteBatch.setProjectionMatrix(this.gController.getCamera().combined);

		this.initTextureMap();
		this.initAnimationMap();

		ppuX = 1;
		ppuY = 1;
	}

	private void initTextureMap() {
		// TODO Auto-generated method stub
		atlas = new TextureAtlas(Gdx.files.internal("sprites/tileset/tileset.atlas"));

		this.textureMap.put(TileContent.BASE_GRASSGROUND, new TextureRegion(atlas.findRegion("testGrassGround")));
		this.textureMap.put(TileContent.BASE_TECHNOGROUND, new TextureRegion(atlas.findRegion("testTechGround")));
		this.textureMap.put(TileContent.NOTHING, new TextureRegion());
	}

	private void initAnimationMap(){
		TextureRegion[] walkUpFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkUpFrames[i] = atlas.findRegion("testHeroF1" + i);
		}
		this.animationMap.put(WalkDirection.UP, new Animation(RUNNING_FRAME_DURATION, walkUpFrames));

		TextureRegion[] walkDownFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkDownFrames[i] = atlas.findRegion("testHeroF2" + i);
		}
		this.animationMap.put(WalkDirection.DOWN, new Animation(RUNNING_FRAME_DURATION, walkDownFrames));

		TextureRegion[] walkRightFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkRightFrames[i] = atlas.findRegion("testHeroF3" + i);
		}
		this.animationMap.put(WalkDirection.RIGHT, new Animation(RUNNING_FRAME_DURATION, walkRightFrames));

		TextureRegion[] walkLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkLeftFrames[i] = atlas.findRegion("testHeroF0" + i);
		}
		this.animationMap.put(WalkDirection.LEFT, new Animation(RUNNING_FRAME_DURATION, walkLeftFrames));

		TextureRegion[] walkUpRightFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkUpRightFrames[i] = atlas.findRegion("testHeroF3" + i);
		}
		this.animationMap.put(WalkDirection.UP_RIGHT, new Animation(RUNNING_FRAME_DURATION, walkUpRightFrames));

		TextureRegion[] walkUpLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkUpLeftFrames[i] = atlas.findRegion("testHeroF1" + i);
		}
		this.animationMap.put(WalkDirection.UP_LEFT, new Animation(RUNNING_FRAME_DURATION, walkUpLeftFrames));

		TextureRegion[] walkDownRightFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkDownRightFrames[i] = atlas.findRegion("testHeroF2" + i);
		}
		this.animationMap.put(WalkDirection.DOWN_RIGHT, new Animation(RUNNING_FRAME_DURATION, walkDownRightFrames));

		TextureRegion[] walkDownLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkDownLeftFrames[i] = atlas.findRegion("testHeroF0" + i);
		}
		this.animationMap.put(WalkDirection.DOWN_LEFT, new Animation(RUNNING_FRAME_DURATION, walkDownLeftFrames));
	}

	private void drawGround(){
		//System.out.println(gController.getLevel().getMergedMap().getTiles());
		for(Tile tile : gController.getLevel().getMergedMap().getTiles()){
			//System.out.println("pos : ("+tile.getPosition().x+","+tile.getPosition().y+")");
			if(!tile.getTileContent().equals(TileContent.NOTHING)){
				spriteBatch.draw(this.textureMap.get(tile.getTileContent()), 
						(tile.getPosition().x)* ppuX, (tile.getPosition().y)* ppuY,
						Tile.SIZE * ppuX, Tile.SIZE * ppuY);
			}
		}
	}

	private void drawPlayer(){
		TextureRegion playerFrame = null;
		if(gController.getLevel().getMergedMap().getPlayer().getState().equals(State.IDLE)){
			
			playerFrame = animationMap.get(gController.getLevel().getMergedMap().getPlayer().getWalkDirection()).getKeyFrame(GameRenderer.RUNNING_FRAME_DURATION+0.01f);
		}
			
		else if(gController.getLevel().getMergedMap().getPlayer().getState().equals(State.WALKING)){
			playerFrame = animationMap.get(gController.getLevel().getMergedMap().getPlayer().getWalkDirection()).
					getKeyFrame(gController.getLevel().getMergedMap().getPlayer().getStateTime(), true) ;
		}
		spriteBatch.draw(playerFrame, 
				gController.getLevel().getMergedMap().getPlayer().getPosition().x * ppuX, 
				gController.getLevel().getMergedMap().getPlayer().getPosition().y * ppuY, 
				Player.SIZE * ppuX, Player.SIZE * ppuY*2);

	}
	
	private void drawGrid(){
		//Dessiner la grille
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(this.gController.getCamera().combined);
		shapeRenderer.begin(ShapeType.Line);
		Tile firstCase = gController.getLevel().getMergedMap().getTiles().get(0);
		float oX = firstCase.getPosition().x + Tile.SIZE/2;
		float oY = firstCase.getPosition().y + Tile.SIZE*3/4;

		int sizeR = 0;
		int sizeC = 0;
		
		if (this.gController.getLevel().getMergedMap().sideFocus == Side.RECTO)
		{
			sizeR = gController.getLevel().getSizeRow();
			sizeC = gController.getLevel().getSizeCol();
		}
		else if (this.gController.getLevel().getMergedMap().sideFocus == Side.VERSO)
		{
			sizeC = gController.getLevel().getSizeRow();
			sizeR = gController.getLevel().getSizeCol();
		}
		

		System.out.println("Position premi�re case : "+ oX + " : " + oY);

		shapeRenderer.setColor(1, 1, 1, 1);
		for(int i = 0 ; i<=Math.max(sizeR, sizeC);i++){
			if(i<=sizeC)
			shapeRenderer.line( oX + 64*i, oY - 32*i, 
					oX - 64*sizeR + 64*i, oY - 32*sizeR- 32*i);
			
			if(i<=sizeR)
			shapeRenderer.line( oX - 64*i, oY - 32*i, 
					oX + 64*sizeC - 64*i, oY - 32*sizeC- 32*i);
		}
		shapeRenderer.end();
		shapeRenderer.dispose();
	}

	public void render() {
		//Gdx.app.log(RVGame.LOG, "Render game model");
		this.spriteBatch.setProjectionMatrix(this.gController.getCamera().combined);
		/*
		 * 1. We draw a black background. This prevents flickering.
		 */

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		/*
		 * 2. We draw the ground
		 */

		spriteBatch.begin();

		this.drawGround();
		this.drawPlayer();

		spriteBatch.end();

		if(gController.isEditorView){
			this.drawGrid();
		}


	}

	public void dispose() {
		// TODO Auto-generated method stub
		if( spriteBatch != null ) spriteBatch.dispose();
		if( atlas != null ) atlas.dispose();

	}


}
