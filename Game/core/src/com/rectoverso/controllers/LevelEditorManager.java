package com.rectoverso.controllers;

import com.rectoverso.model.Entity;
import com.rectoverso.model.Level;
import com.rectoverso.model.Map;
import com.rectoverso.model.Map.Side;
import com.rectoverso.model.Tile;
import com.rectoverso.model.Tile.TileContent;
import com.rectoverso.screen.LevelEditorScreen;

public class LevelEditorManager {
	
	public enum FocusType{
		TILE,
		BG,
		DECOR,
		OBJECT
	}

	private Tile tileFocused;
	
	private TileContent tileCached = TileContent.NOTHING;
	private String bGCached = null;
	private Entity decorCached = null;
	private Entity objectCached = null;
	
	private Side currentSide = Side.RECTO;
	
	private Level levelEdited;
	private GameRenderer levelRenderer ;
	private GameController levelController;
	
	public LevelEditorScreen view;
	
	public boolean flagUnsavedChange = false;
	
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
	
	public void setBGCached (String name){
		if (bGCached != null) bGCached = name;
	}
	public String getBGCached (){
		return bGCached;
	}
	
	public void setDecorCached (Entity decor ){
		if (decorCached != null) decorCached = decor;
	}
	public Entity getDecorCached (){
		return decorCached;
	}
	
	public void setObjectCached (Entity object ){
		if (objectCached != null) objectCached = object;
	}
	public Entity getObjectCached (){
		return objectCached;
	}
	
	public void flipSide(){
		if (currentSide == Side.RECTO){
			currentSide = Side.VERSO;
		}
		else{
			currentSide = Side.RECTO;
		}
		this.levelController.getLevel().setMergedMap(currentSide);
	}
	public Side getCurrentSide(){
		return currentSide;
	}
	
	public void setLevelEdited(Level level){
		this.levelEdited = level;
		this.levelController = new GameController(level);
		this.levelController.isEditorView = true;
		this.levelRenderer = new GameRenderer(levelController);
		//levelController.getCamera().viewportHeight = 100;
		//levelController.getCamera().viewportWidth = 100;
		this.levelController.getCamera().position.x = - 50;
		this.levelController.getCamera().position.y = - 100;
		
	}
	public Level getLevelEdited(){
		return levelEdited;
	}
	public void saveLevelEdited()
	{
		LevelManager.saveLevel(this.getLevelEdited(),this.view.propertie_FileName.getText());
		view.showConfirm("Sauvegarde", "Niveau sauvegarde avec succes", "", "");
	}
	
	public void newFile(){
		Level level = Level.createEmptyLevel(5,8);
		level.fileName = "NewLevel";
		this.setLevelEdited(level);
	}
	
	public void updateProperties(){
		this.view.propertie_col.setText( this.levelEdited.getSizeCol()+"");
		this.view.propertie_row.setText( this.levelEdited.getSizeRow()+"");
		this.view.propertie_LevelName.setText( this.levelEdited.getName()+"" );
		this.view.propertie_FileName.setText( this.levelEdited.fileName);
	}
	public void applyProperties(){
		
		this.levelEdited.fileName = this.view.propertie_FileName.getText();
		this.levelEdited.setName(this.view.propertie_LevelName.getText());
		
		//Recupérer le numéro de ligne et colonne
		int row = -1;
		int col = -1;
		try{
			row = Integer.parseInt(this.view.propertie_row.getText());
			col = Integer.parseInt(this.view.propertie_col.getText());
		}
		catch (NumberFormatException e){
			e.toString();
		}
		
		if (row > 0 && col > 0){
			this.levelEdited.resizeMap( row , col);
		}
		updateProperties();
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
