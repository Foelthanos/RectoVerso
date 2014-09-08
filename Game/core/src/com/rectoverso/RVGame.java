package com.rectoverso;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.rectoverso.controllers.LevelManager;
import com.rectoverso.controllers.MusicManager;
import com.rectoverso.controllers.PreferencesManager;
import com.rectoverso.controllers.SoundManager;
import com.rectoverso.screen.CreditScreen;
import com.rectoverso.screen.GameScreen;
import com.rectoverso.screen.LevelEditorScreen;
import com.rectoverso.screen.MenuScreen;
import com.rectoverso.screen.OptionsScreen;
import com.rectoverso.screen.SplashScreen;

/**
 * Main class of the game. Give methods that connect the screens with
 * all the managers of the game. Give standard gamestate methods.
 * @author Brahim "Violacrimosum" Berkati
 * @version 0.3
 */
public class RVGame extends Game {
	private FPSLogger fps;

	public static final String LOG = RVGame.class.getSimpleName();
	public static final boolean DEV_MODE = true;
	public static final String VER = "0.0.3a";

	private PreferencesManager preferencesManager;
	private LevelManager levelManager;
	private MusicManager musicManager;
	private SoundManager soundManager;

	public PreferencesManager getPreferencesManager()
	{
		return preferencesManager;
	}

	public LevelManager getLevelManager()
	{
		return levelManager;
	}

	public MusicManager getMusicManager()
	{
		return musicManager;
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}

	// Screen methods

	public GameScreen getRulesScreen()
	{
		return new GameScreen(this);
	}

	public SplashScreen getSplashScreen()
	{
		return new SplashScreen(this);
	}

	public OptionsScreen getOptionsScreen()
	{
		return new OptionsScreen(this);
	}
	
	public LevelEditorScreen getLevelEditorScreen()
	{
		return new LevelEditorScreen(this);
	}

	public CreditScreen getCreditScreen()
	{
		return new CreditScreen(this);
	}
	
	public MenuScreen getMenuScreen()
	{
		return new MenuScreen(this);
	}

	@Override
	public void create () {
		Gdx.app.log(RVGame.LOG, "Creating game on " + Gdx.app.getType());

		// create the preferences manager
		preferencesManager = new PreferencesManager();

		// create the music manager
		musicManager = new MusicManager();
		musicManager.setVolume(preferencesManager.getVolume());
		musicManager.setEnabled(preferencesManager.isMusicEnabled());

		// create the sound manager
		soundManager = new SoundManager();
		soundManager.setVolume(preferencesManager.getVolume());
		soundManager.setEnabled(preferencesManager.isSoundEnabled());

		// create the level manager
		levelManager = new LevelManager();

		fps = new FPSLogger();	
	}

	@Override
	public void dispose() {
		super.dispose();
		// You can use this function to print stuff to the console. 
		// It's very useful to use to track what's happening in your game.
		Gdx.app.log(RVGame.LOG, "Disposed");
	}

	@Override
	public void resize(
			int width,
			int height )
	{
		super.resize(width, height);
		Gdx.app.log(RVGame.LOG, "Resizing game to: " + width + " x " + height);

		// show the splash screen when the game is resized for the first time;
		// this approach avoids calling the screen's resize method repeatedly
		if(getScreen() == null ) {
			if( DEV_MODE ) {
				setScreen(new MenuScreen(this));
			} else {
				setScreen(new SplashScreen(this));
			}
		}
	}

	@Override
	public void resume(){
		super.resume();
		Gdx.app.log(RVGame.LOG, "Resuming game");
	}

	@Override
	public void setScreen(Screen screen ){
		super.setScreen(screen);
		Gdx.app.log(RVGame.LOG, "Setting screen: " + screen.getClass().getSimpleName());
	}

	@Override
	public void render () {
		super.render();
		if(DEV_MODE) fps.log();
	}

	public void pause(){
		super.pause();
		Gdx.app.log(RVGame.LOG, "Pausing game");

	}
}
