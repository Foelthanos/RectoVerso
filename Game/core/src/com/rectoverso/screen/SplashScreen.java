package com.rectoverso.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.MusicManager.RVMusic;

public class SplashScreen extends AbstractScreen {

	private Image splashImage;
	private Label smiley;
	private Label teamName;
	private Label teamDescription;

	private String[] smileyList = {":)",";)",":(","^^","T_T"};
	private String[] teamDescriptionList = {"Sorry, but your game is in another computer",
			"devTeam.exe has crashed. Please, contact an administrator."
			};
	
	public SplashScreen(RVGame game) {
		// TODO Auto-generated constructor stub
		super(game);
	}

	@Override
	public void show()
	{
		super.show();

		// start playing the menu music
		if(!RVGame.DEV_MODE )
			game.getMusicManager().play(RVMusic.MENU);

		// retrieve the splash image's region from the atlas
		//AtlasRegion splashRegion = getAtlas().findRegion("splash");
		//Drawable splashDrawable = new TextureRegionDrawable( splashRegion );
		
		
		// Texte
		smiley = new Label(smileyList[(int)(Math.random()*smileyList.length)], this.getSkin());
		smiley.setPosition(Gdx.graphics.getWidth()/8, (float) (Gdx.graphics.getHeight()/1.25));
		
		teamName = new Label("404:Team_Not_Found", this.getSkin());
		teamName.setPosition(Gdx.graphics.getWidth()/5, (float) (Gdx.graphics.getHeight()/1.25));
		
		teamDescription = new Label(teamDescriptionList[(int)(Math.random()*teamDescriptionList.length)], this.getSkin());
		teamDescription.setPosition(Gdx.graphics.getWidth()/5, (float) (Gdx.graphics.getHeight()/1.50));
		
		// here we create the splash image actor; its size is set when the
		// resize() method gets called
		//splashImage = new Image( splashDrawable, Scaling.none );
		//splashImage.setFillParent( true );

		// this is needed for the fade-in effect to work correctly; we're just
		// making the image completely transparent
		//splashImage.getColor().a = 0f;

		teamName.addAction(Actions.sequence( Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f )));
		teamDescription.addAction(Actions.sequence( Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f )));
		smiley.addAction(Actions.sequence( Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f ),
				new Action() {
			@Override
			public boolean act(
					float delta )
			{
				// the last action will move to the next screen
				game.setScreen( new MenuScreen( game ) );
				return true;
			}
		} ) );
		// configure the fade-in/out effect on the splash image
		/*splashImage.addAction(Actions.sequence( Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f ),
				new Action() {
			@Override
			public boolean act(
					float delta )
			{
				// the last action will move to the next screen
				game.setScreen( new MenuScreen( game ) );
				return true;
			}
		} ) );*/

		// and finally we add the actor to the stage
		stage.addActor(smiley);
		stage.addActor(teamName);
		stage.addActor(teamDescription);
		//stage.addActor(splashImage);
	}


}
