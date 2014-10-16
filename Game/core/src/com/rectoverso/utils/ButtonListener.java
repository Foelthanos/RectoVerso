package com.rectoverso.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.SoundManager.RVSound;
import com.rectoverso.model.Tile.TileContent;
import com.rectoverso.screen.AbstractScreen;


public class ButtonListener {

	
	
	public static class DefaultInputListener extends InputListener {
		
		 @Override
		    public boolean touchDown(
		        InputEvent event,
		        float x,
		        float y,
		        int pointer,
		        int button )
		    {
		        return true;
		    }

	}
	
	public static class ChangeScreenListener extends InputListener {

		public ChangeScreenListener(RVGame game, AbstractScreen screen ){
			super();
			this.screen = screen;
			this.game = game;
		}
		
		public AbstractScreen screen;
		public RVGame game;
		
		 @Override
		    public boolean touchDown(
		        InputEvent event,
		        float x,
		        float y,
		        int pointer,
		        int button )
		    {
		        return true;
		    }
		 
		 @Override
		    public void touchUp(
		        InputEvent event,
		        float x,
		        float y,
		        int pointer,
		        int button )
		    {
			 	super.touchUp( event, x, y, pointer, button );
			 	//System.out.println("Je suis passé ici !!!");
			 	game.getSoundManager().play(RVSound.CLICK );
			 	game.setScreen(screen);
		    }

	}
	
	public static class TileButtonListener extends InputListener {
		public TileButtonListener(RVGame game, TileContent tileType ){
			super();
			this.tileType = tileType;
			this.game = game;
		}
		
		public TileContent tileType ;
		public RVGame game;
		
		 @Override
		    public boolean touchDown(
		        InputEvent event,
		        float x,
		        float y,
		        int pointer,
		        int button )
		    {
		        return true;
		    }
		 
		 @Override
		    public void touchUp(
		        InputEvent event,
		        float x,
		        float y,
		        int pointer,
		        int button )
		    {
			 	super.touchUp( event, x, y, pointer, button );
			 	//System.out.println("Je suis passé ici !!!");
			 	game.getSoundManager().play(RVSound.CLICK );
			 	game.getLevelEditorManager().setTiledCached(tileType);
		    }
	}
	

}


