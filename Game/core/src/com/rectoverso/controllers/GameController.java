package com.rectoverso.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.rectoverso.RVGame;
import com.rectoverso.model.Level;
import com.rectoverso.model.Player;
import com.rectoverso.model.Player.State;
import com.rectoverso.model.Player.WalkDirection;
import com.rectoverso.model.Tile.TileCollision;
import com.rectoverso.model.Tile.TileContent;

public class GameController {

	public static final int MOVESPEED = 2;

	enum Keys {
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
		keys.put(Keys.RIGHTCLICK, false);
		keys.put(Keys.LEFTCLICK, false);
		keys.put(Keys.Z, false);
		keys.put(Keys.Q, false);
		keys.put(Keys.S, false);
		keys.put(Keys.D, false);
		keys.put(Keys.SPACE, false);
	};

	private OrthographicCamera camera = new OrthographicCamera();

	private Level level;
	private int score;
	private int step;
	private Vector2 translation;

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

	public void update(float delta) {
		//Gdx.app.log(RVGame.LOG, "Update game model");
		this.processInput();
		Vector2 tmp = isoPosToOrthoIndex(this.getLevel().getMergedMap().getPlayer().getFootPosition(),0,0);
		Gdx.app.log(RVGame.LOG, "-------------------- Position ---------------------");
		Gdx.app.log(RVGame.LOG, "Orthogonal Pos : ["+this.getLevel().getMergedMap().getPlayer().getFootPositionX()+""
				+","+this.getLevel().getMergedMap().getPlayer().getFootPositionY()+"]");
		Gdx.app.log(RVGame.LOG, "Tile Index : ["+tmp.x+","+tmp.y+"]");
		if(this.processCollision())
			this.getLevel().getMergedMap().getPlayer().update(delta);
		this.camera.update();
	}

	private Vector2 isoPosToOrthoIndex(Vector2 isoPos, float offsetX, float offsetY){
		

		return new Vector2(0,0);
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

	private void processInput(){

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

		this.camera.position.x = this.getLevel().getMergedMap().getPlayer().getPosition().x;
		this.camera.position.y = this.getLevel().getMergedMap().getPlayer().getPosition().y;
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
