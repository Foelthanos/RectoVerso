package com.rectoverso.controllers;

import com.rectoverso.model.Level;
import com.rectoverso.model.Map;
import com.rectoverso.model.Map.Side;
import com.rectoverso.model.Tile;
import com.rectoverso.model.Tile.TileContent;

public class LevelEditorManager {

	private Tile tileFocused;
	private TileContent tileCached = TileContent.BASE_GRASSGROUND;
	private Side currentSide = Side.RECTO;
	
	private Level levelEdited;
	private GameRenderer levelRenderer ;
	private GameController levelController;
	
	public void setTileFocused (Tile tile){
		if (tile != null) tileFocused = tile;
	}
	public Tile getTileFocused (){
		return tileFocused;
	}
	
	public void setTiledCached(TileContent type){
		tileCached = type;
	}
	public TileContent getTileCached(){
		return tileCached;
	}
	
	public void flipSide(){
		if (currentSide == Side.RECTO){
			currentSide = Side.VERSO;
		}
		else{
			currentSide = Side.RECTO;
		}
	}
	public Side getCurrentSide(){
		return currentSide;
	}
	
	public void setLevelEdited(Level level){
		this.levelEdited = level;
		this.levelController = new GameController(level);
		this.levelController.isEditor = true;
		this.levelRenderer = new GameRenderer(levelController);
		//levelController.getCamera().viewportHeight = 100;
		//levelController.getCamera().viewportWidth = 100;
		this.levelController.getCamera().position.x = - 50;
		this.levelController.getCamera().position.y = - 100;
	}
	public Level getLevelEdited(){
		return levelEdited;
	}
	
	public GameController getGameController(){
		return levelController;
	}
	public GameRenderer getGameRenderer(){
		return levelRenderer;
	}
	public void changeTile(){
		Map map = levelEdited.getMap(currentSide);
		if (!tileFocused.getTileContent().equals(tileCached)){
			map.setTileContent(tileFocused, tileCached);
		}
		else{
			//map.setTileContent(tileFocused, TileContent.NOTHING);
		}
		
	}
	
}
