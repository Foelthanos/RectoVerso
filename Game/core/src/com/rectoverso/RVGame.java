package com.rectoverso;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.rectoverso.controllers.LevelEditorManager;
import com.rectoverso.controllers.LevelManager;
import com.rectoverso.controllers.LevelSelectManager;
import com.rectoverso.controllers.MusicManager;
import com.rectoverso.controllers.PreferencesManager;
import com.rectoverso.controllers.SoundManager;
import com.rectoverso.model.Level;
import com.rectoverso.screen.CreditScreen;
import com.rectoverso.screen.GameScreen;
import com.rectoverso.screen.LevelEditorScreen;
import com.rectoverso.screen.LevelSelectScreen;
import com.rectoverso.screen.MenuScreen;
import com.rectoverso.screen.OptionsScreen;
import com.rectoverso.screen.SplashScreen;

/**
 * Main class of the game. Give methods that connect the screens with
 * all the managers of the game. Give standard gamestate methods.
 * @author Brahim "Violacrimosum" Berkati
 * @version 0.5
 */
public class RVGame extends Game {
	private FPSLogger fps;

	/**
	 * Static constant that give the name of game
	 */
	public static final String LOG = RVGame.class.getSimpleName();
	
	/**
	 * Static boolean that activate the developers mode.
	 */
	public static final boolean DEV_MODE = true;
	
	/**
	 * Static constant that give the actual version of the game
	 */
	public static final String VER = "0.2.3a";

	private PreferencesManager preferencesManager;
	private LevelManager levelManager;
	private LevelSelectManager levelSelectManager;
	private LevelEditorManager levelEditorManager;
	private MusicManager musicManager;
	private SoundManager soundManager;
	
	// -------------- Managers methods ---------------
	
	public PreferencesManager getPreferencesManager()
	{
		return preferencesManager;
	}

	public LevelManager getLevelManager()
	{
		return levelManager;
	}
	public LevelSelectManager getLevelSelectManager()
	{
		return levelSelectManager;
	}

	public LevelEditorManager getLevelEditorManager()
	{
		return levelEditorManager;
	}
	
	public MusicManager getMusicManager()
	{
		return musicManager;
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}

	// --------------- Screen methods -------------------

	public GameScreen getGameScreen(Level level)
	{
		return new GameScreen(this, level);
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
	
	public LevelSelectScreen getLevelSelectScreen()
	{
		return new LevelSelectScreen(this);
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

		// Create the preferences manager
		preferencesManager = new PreferencesManager();

		// Create the music manager
		musicManager = new MusicManager();
		musicManager.setVolume(preferencesManager.getVolume());
		musicManager.setEnabled(preferencesManager.isMusicEnabled());

		// Create the sound manager
		soundManager = new SoundManager();
		soundManager.setVolume(preferencesManager.getVolume());
		soundManager.setEnabled(preferencesManager.isSoundEnabled());

		// Create the level manager
		levelManager = new LevelManager();
		
		// Create the level select manager
		levelSelectManager = new LevelSelectManager();
		levelSelectManager.parseWorlds();
		levelSelectManager.setSelectedWorld(0);
		
		// Create the level editor manager
		levelEditorManager = new LevelEditorManager();

		fps = new FPSLogger();	
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(RVGame.LOG, "Disposed");
	}

	@Override
	public void resize(
			int width,
			int height )
	{
		super.resize(width, height);
		Gdx.app.log(RVGame.LOG, "Resizing game to: " + width + " x " + height);

		/* 
		 * Show the splash screen when the game is resized for the first time.
		 * This approach avoids calling the screen's resize method repeatedly.
		 * if dev_mode is on, the game directly call the menu screen.
		 */
		if(getScreen() == null) {
			if(DEV_MODE) {
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
