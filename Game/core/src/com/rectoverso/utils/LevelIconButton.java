package com.rectoverso.utils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rectoverso.model.Level;

public class LevelIconButton implements ApplicationListener {
    
	public enum IconState{ LOCKED , IDLE , SELECTED };
	private IconState state = IconState.LOCKED;
	
	public static final int TILE_HEIGHT = 32;
	public static final int TILE_WIDTH = 64;
	public static final int GRID_SIZE = 8;
	
	private SpriteBatch batch;
    private Pixmap pixmap;
    private Texture texture;
    private Sprite sprite;
    
    private BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"),
	         Gdx.files.internal("skin/default.png"), false);
    private String text = "00000";
    
    private int posX=0;
    private int posY=0;
    private int size=1;
    private Level level;
    
    public void setLevel(Level lvl , boolean selected){
    	this.level = lvl;
    	
    	if(level.isLocked()){
    		state = IconState.LOCKED;
    		text = "";
    	}
    	else{
    		switch(level.getType()){
    		case MOVIE:
    			text = "C-";
    			break;
    		case SECRET:
    			text = "S-";
    			break;
    		case NORMAL:
    			text = "";
    			break;
    		}
    		text += level.getNumber();
    		if (selected){
    			state = IconState.SELECTED;
	    	}
	    	else{
	    		state = IconState.IDLE;
	    	}
    	}
    	renderState();
    }
    public Level getLevel(){
    	return this.level;
    }
    
    public boolean isColliding(int x, int y){
    	if (state != IconState.IDLE) return false;
    	
    	//le repère y est completement inversé et le centre n'est pas le même selon la taille
    	int xi = (int) sprite.getX() + TILE_WIDTH/2;
    	int yi = (int) (Gdx.graphics.getHeight() - sprite.getY()) - TILE_HEIGHT + TILE_HEIGHT/2*(this.size-1);
    	
    	double d = Math.sqrt( Math.pow(x-xi, 2) + Math.pow(y-yi, 2) );
    	//Gdx.app.log(RVGame.LOG, this.level.getName()+" : "+d);
    	texture.dispose();
    	if (d < 16*(this.size)){
    		texture = new Texture(Gdx.files.internal("hud/level_icon_select.png"));
    		sprite.setTexture(texture);
    		font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    		return true;
    	}
    	else{
    		texture = new Texture(Gdx.files.internal("hud/level_icon_idle.png"));
    		sprite.setTexture(texture);
    		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    		return false;
    	}
    	
    }
    
    public void create(int colomn, int row, int size) {        
    	
    	posX = colomn;
    	posY = row;
    	this.size = size;
    	this.create();
    	

    }
    
    @Override
	public void create() {
    	batch = new SpriteBatch();
    	
    	pixmap = new Pixmap(64, 64, Pixmap.Format.RGB888);
    	
    	texture = new Texture(Gdx.files.internal("hud/level_icon_idle.png"));
    	sprite = new Sprite(texture);
    	renderState();
    	
    	sprite.setOrigin(sprite.getOriginX(), 48);
    	sprite.setScale(this.size);
    	
    	calculatePosition();
	}

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {        
        
        
        batch.begin();
        sprite.draw(batch);
        //font.draw(batch, text, sprite.getX()+TILE_WIDTH/2, sprite.getY()+40 - (this.size-1)*TILE_HEIGHT/2);
        font.drawMultiLine(batch, text, 
        		sprite.getX()+TILE_WIDTH/2, 
        		sprite.getY()+40- (this.size-1)*TILE_HEIGHT/2,
        		0,HAlignment.CENTER);
        batch.end();
    }

    private void renderState(){
    	texture.dispose();
    	switch(state){
    	case IDLE:
    		texture = new Texture(Gdx.files.internal("hud/level_icon_idle.png"));
    		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    		break;
    	case SELECTED:
    		texture = new Texture(Gdx.files.internal("hud/level_icon_select.png"));
    		font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    		break;
    	case LOCKED:
    		texture = new Texture(Gdx.files.internal("hud/level_icon_locked.png"));
    		break;
    	}
    	sprite.setTexture(texture);
    }
    private void calculatePosition(){
    	int x = Gdx.graphics.getWidth()/2 - texture.getWidth()/2 + posX*TILE_WIDTH/2 - posY*TILE_WIDTH/2 ;
    	int y = Gdx.graphics.getHeight()/2 - texture.getHeight()/2 + GRID_SIZE*TILE_HEIGHT/2 - posX*TILE_HEIGHT/2 - posY*TILE_HEIGHT/2;
    	sprite.setPosition(x,y);
    }
    
    public void setState(IconState state){
    	this.state = state;
    	renderState();
    }
    
    public IconState getState(){
    	return state;
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