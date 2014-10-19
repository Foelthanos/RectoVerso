package com.rectoverso.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.rectoverso.RVGame;
import com.rectoverso.model.Level;
import com.rectoverso.model.Player;
import com.rectoverso.model.Tile;
import com.rectoverso.model.Player.State;
import com.rectoverso.model.Player.WalkDirection;
import com.rectoverso.model.Tile.TileCollision;
import com.rectoverso.model.Tile.TileContent;

public class GameController {

	public static final int MOVESPEED = 2;

	enum Keys {
		ESCAPE,
		RIGHTCLICK,
		LEFTCLICK,
		SPACE,
		Z,
		S,
		Q,
		D;
	}
	static Map<Keys, Boolean> keys = new HashMap<GameController.Keys, Boolean>();
	static {
		keys.put(Keys.ESCAPE, false);
		keys.put(Keys.RIGHTCLICK, false);
		keys.put(Keys.LEFTCLICK, false);
		keys.put(Keys.Z, false);
		keys.put(Keys.Q, false);
		keys.put(Keys.S, false);
		keys.put(Keys.D, false);
		keys.put(Keys.SPACE, false);
	};

	private OrthographicCamera camera = new OrthographicCamera();

	public boolean isEditor = false;
	private Level level;
	private int score;
	private int step;
	private Vector2 translation;
	private Vector2 cursorPosition;

	public GameController(Level level){
		this.level = level;
		this.score = 0;
		this.step = 0;
		this.translation = new Vector2(0, 0);
	}

	public OrthographicCamera getCamera(){
		return this.camera;
	}

	public void setCamera(OrthographicCamera camera){
		this.camera = camera;
	}

	public Level getLevel(){
		return this.level;
	}

	public int update(float delta) {
		//Gdx.app.log(RVGame.LOG, "Update game model");
		int returnCode = this.processInput();
		Vector2 tmp = isoPosToOrthoIndex(this.getLevel().getMergedMap().getPlayer().getFootPosition(),0,0);
		/*Gdx.app.log(RVGame.LOG, "-------------------- Position ---------------------");
		Gdx.app.log(RVGame.LOG, "Orthogonal Pos : ["+this.getLevel().getMergedMap().getPlayer().getFootPositionX()+""
				+","+this.getLevel().getMergedMap().getPlayer().getFootPositionY()+"]");
		Gdx.app.log(RVGame.LOG, "Tile Index : ["+tmp.x+","+tmp.y+"]");*/
		if(this.processCollision())
			this.getLevel().getMergedMap().getPlayer().update(delta);
		this.camera.update();
		
		return returnCode;
	}

	private Vector2 isoPosToOrthoIndex(Vector2 isoPos, float offsetX, float offsetY){
		

		return new Vector2(0,0);
	}

	public Tile getTile(float posX, float posY){
		
		Tile firstCase = this.getLevel().getMergedMap().getTiles().get(0);
		float oX = firstCase.getPosition().x + firstCase.SIZE/2;
		float oY = firstCase.getPosition().y + firstCase.SIZE*3/4;
		
		//les coordonn�es de la tile vis�e
		int row,col;
		double a1 = -0.5 ;
		double b1 = oY - (a1 * oX);
		double a2 = 0.5;
		double b2 = oY - (a2 * oX);
		
		int mR = (int) (posY - (a1 * posX));
		int mC = (int) (posY - (a2 * posX));
		
		row = (int)( Math.floor((mR-b1)/64)*-1) -1 ;
		col = (int)( Math.floor((mC-b2)/64)*-1) -1 ;
		
		int size = this.getLevel().getScaleX();
		
		//supposon que le point sorte des limites, on choisira la case la plus proche
		if (row < 0 ) row = 0;
		else if (row >= size) row = size-1;
		if (col < 0 ) col = 0;
		else if (col >= size) col = size-1;
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(this.getCamera().combined);
		shapeRenderer.begin(ShapeType.Line);
		
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.line( 0,  mR, posX, posY);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.line( 0,  mC, posX, posY);
		shapeRenderer.end();
		
		
		return this.getLevel().getMergedMap().getTile(row, col, this.getLevel());
	}
	
	private boolean processCollision(){
		//this.getLevel().getMergedMap().getPlayer().getPosition()
		switch(this.getLevel().getMergedMap().getPlayer().getWalkDirection()){
		case DOWN:
			Vector2 tmpTilePos = this.isoPosToOrthoIndex(this.getLevel().getMergedMap().getPlayer().getPosition(), 
					-Player.SPEED,0);
			if(this.getLevel().getMergedMap().getTile(
					tmpTilePos, this.getLevel().getScaleX()).getTileCollision().equals(
							TileCollision.COLLISION)){
				this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);
				return false;
			}
			break;
		case DOWN_LEFT:
			break;
		case DOWN_RIGHT:
			break;
		case UP:
			break;
		case UP_LEFT:
			break;
		case UP_RIGHT:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		default:

		}
		return true;
	}

	private int processInput(){

		this.getLevel().getMergedMap().getPlayer().setVelocityX(0);
		this.getLevel().getMergedMap().getPlayer().setVelocityY(0);
		this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);

		if(this.keys.get(Keys.Z)){
			this.getLevel().getMergedMap().getPlayer().setVelocityY(Player.SPEED);
			this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.UP);
			this.getLevel().getMergedMap().getPlayer().setState(State.WALKING);

			if(this.keys.get(Keys.S)){
				this.getLevel().getMergedMap().getPlayer().setVelocityY(0);
				this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);
			}
			else if(this.keys.get(Keys.Q)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(-Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.UP_LEFT);
			}
			else if(this.keys.get(Keys.D)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.UP_RIGHT);
			}


		}

		else if(this.keys.get(Keys.S)){
			this.getLevel().getMergedMap().getPlayer().setVelocityY(-Player.SPEED);
			this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.DOWN);
			this.getLevel().getMergedMap().getPlayer().setState(State.WALKING);

			if(this.keys.get(Keys.Z)){
				this.getLevel().getMergedMap().getPlayer().setVelocityY(0);
				this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);
			}
			else if(this.keys.get(Keys.Q)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(-Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.DOWN_LEFT);
			}
			else if(this.keys.get(Keys.D)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.DOWN_RIGHT);
			}
		}

		else if(this.keys.get(Keys.Q)){
			this.getLevel().getMergedMap().getPlayer().setVelocityX(-Player.SPEED);
			this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.LEFT);
			this.getLevel().getMergedMap().getPlayer().setState(State.WALKING);

			if(this.keys.get(Keys.D)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(0);
				this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);
			}
			else if(this.keys.get(Keys.Z)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(-Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.UP_LEFT);
			}
			else if(this.keys.get(Keys.S)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.DOWN_LEFT);
			}
		}

		else if(this.keys.get(Keys.D)){
			this.getLevel().getMergedMap().getPlayer().setVelocityX(Player.SPEED);
			this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.RIGHT);
			this.getLevel().getMergedMap().getPlayer().setState(State.WALKING);

			if(this.keys.get(Keys.Q)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(0);
				this.getLevel().getMergedMap().getPlayer().setState(State.IDLE);
			}
			else if(this.keys.get(Keys.Z)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(-Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.UP_RIGHT);
			}
			else if(this.keys.get(Keys.S)){
				this.getLevel().getMergedMap().getPlayer().setVelocityX(Player.SPEED);
				this.getLevel().getMergedMap().getPlayer().setWalkDirection(WalkDirection.DOWN_RIGHT);
			}
		}
		System.out.println(this.isEditor);
		if(keys.get(Keys.ESCAPE)){
			keys.get(keys.put(Keys.ESCAPE, false));
			if(this.isEditor)
				return 1;
			
		}
		this.camera.position.x = this.getLevel().getMergedMap().getPlayer().getPosition().x;
		this.camera.position.y = this.getLevel().getMergedMap().getPlayer().getPosition().y;
		
		return 0;
	}

	public void moveUp(){
		if(!this.keys.get(Keys.Z))
			keys.get(keys.put(Keys.Z, true));

	}

	public void moveDown(){
		if(!this.keys.get(Keys.S))
			keys.get(keys.put(Keys.S, true));
	}

	public void moveLeft(){
		if(!this.keys.get(Keys.Q))
			keys.get(keys.put(Keys.Q, true));
	}

	public void moveRight(){
		if(!this.keys.get(Keys.D))
			keys.get(keys.put(Keys.D, true));
	}
	
	public void pushBackToEditor(){
		if(!this.keys.get(Keys.ESCAPE))
			keys.get(keys.put(Keys.ESCAPE, true));
	}
	
	public void releaseBackToEditor(){
		keys.get(keys.put(Keys.ESCAPE, true));
	}

	public void releaseUp(){
		keys.get(keys.put(Keys.Z, false));
	}

	public void releaseDown(){
		keys.get(keys.put(Keys.S, false));
	}

	public void releaseLeft(){
		keys.get(keys.put(Keys.Q, false));
	}

	public void releaseRight(){
		keys.get(keys.put(Keys.D, false));
	}

	public void zoomOut(){
		camera.zoom -= 0.02;
	}
}
