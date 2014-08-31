package com.rectoverso.screen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.MusicManager.RVMusic;

public class SplashScreen extends AbstractScreen {

	private Image splashImage;

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
		AtlasRegion splashRegion = getAtlas().findRegion("splash");
		Drawable splashDrawable = new TextureRegionDrawable( splashRegion );

		// here we create the splash image actor; its size is set when the
		// resize() method gets called
		splashImage = new Image( splashDrawable, Scaling.none );
		splashImage.setFillParent( true );

		// this is needed for the fade-in effect to work correctly; we're just
		// making the image completely transparent
		splashImage.getColor().a = 0f;

		// configure the fade-in/out effect on the splash image
		splashImage.addAction(Actions.sequence( Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f ),
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

		// and finally we add the actor to the stage
		stage.addActor(splashImage);
	}


}
