package com.rectoverso.utils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArrowIconButton implements ApplicationListener{
	
	public static final int TILE_HEIGHT = 32;
	public static final int TILE_WIDTH = 64;
	
	private SpriteBatch batch;
    private Pixmap pixmap;
    private Texture texture;
    private Sprite sprite;
    
    private boolean side = false;
    private int posX = 0;
    private int posY = 0;
    private boolean visible = true;
    
    
    
    public boolean isColliding(int x, int y){
    	if(!visible)return false;
    	
    	//le repère y est completement inversé et le centre n'est pas le même selon la taille
    	int xi = (int) sprite.getX() + TILE_WIDTH/2;
    	int yi = (int) (Gdx.graphics.getHeight() - sprite.getY()) - TILE_HEIGHT ;
    	
    	double d = Math.sqrt( Math.pow(x-xi, 2) + Math.pow(y-yi, 2) );
    	
    	texture.dispose();
    	if (d < TILE_HEIGHT){
    		texture = new Texture(Gdx.files.internal("hud/arrow_icon_select.png"));
    		sprite.setTexture(texture);
    		return true;
    	}
    	else{
    		texture = new Texture(Gdx.files.internal("hud/arrow_icon_idle.png"));
    		sprite.setTexture(texture);
    		return false;
    	}
    	
    }
    
    public void create(boolean side, int x, int y) {        
    	
    	this.posX = x;
    	this.posY = y;
    	this.side = side;
    	this.create();
    	
    }
    
    @Override
	public void create() {
    	
    	batch = new SpriteBatch();
    	
    	texture = new Texture(Gdx.files.internal("hud/arrow_icon_idle.png"));
    	sprite = new Sprite(texture);
    	
    	sprite.setOriginCenter();
    	
    	if (side){
    		sprite.setScale(-1, 1);
    	}
    	else{
    		sprite.setScale(1, 1);
    	}
    	
    	calculatePosition();
	}

    public void setVisible(boolean visible){
    	this.visible = visible;
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {        
        if(!visible)return;
        
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    private void calculatePosition(){
    	int x = posX - TILE_WIDTH /2;
    	int y = posY - TILE_WIDTH /2;
    	sprite.setPosition(x,y);
    }
    
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
