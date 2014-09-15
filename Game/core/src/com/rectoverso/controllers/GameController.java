package com.rectoverso.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.rectoverso.RVGame;
import com.rectoverso.model.Level;

public class GameController {

	enum Keys {
		RIGHTCLICK,
		LEFTCLICK,
		SPACE;
	}
	static Map<Keys, Boolean> keys = new HashMap<GameController.Keys, Boolean>();
	static {
		keys.put(Keys.RIGHTCLICK, false);
		keys.put(Keys.LEFTCLICK, false);
	};
	
	private Rectangle rect = new Rectangle(0, 0, 128, 128);
	private OrthographicCamera camera = new OrthographicCamera();
	
	private Level level;
	private int score;
	private int step;
	
	public GameController(Level level){
		this.level = level;
		this.score = 0;
	    this.step = 0;
	}
	
	public OrthographicCamera getCamera(){
		return this.camera;
	}
	
	public Rectangle getRect() {
        return rect;
    }
	
	public void update(float delta) {
		Gdx.app.log(RVGame.LOG, "Update game model");
		rect.x++;
        if (rect.x > (800-128)) {
            rect.x = 0;
        }
    }
}
