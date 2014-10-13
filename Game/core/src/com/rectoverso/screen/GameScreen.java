package com.rectoverso.screen;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.GameController;
import com.rectoverso.controllers.GameRenderer;
import com.rectoverso.model.Level;

public class GameScreen extends AbstractScreen implements InputProcessor, ControllerListener{



	//private GameController gController;
	private GameRenderer gRenderer;
	private GameController gController;

	public GameScreen(RVGame game, Level level) {
		super(game);
		// TODO Auto-generated constructor stub
		this.gController = new GameController(level);
		this.gRenderer = new GameRenderer(this.gController);
	}

	public GameScreen(RVGame game, Level level, boolean b) {
		// TODO Auto-generated constructor stub
		super(game);
		this.gController = new GameController(level);
		this.gRenderer = new GameRenderer(this.gController);
		this.gController.isEditor = true;
	}

	@Override
	public void render(float delta) {
		if(gController.update(delta)==1)
			game.setScreen(game.getLevelEditorScreen());
		gRenderer.render();
	}

	public void show(){
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);

		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Keys.Z:
			gController.moveUp();
			break;
		case Keys.S:
			gController.moveDown();
			break;
		case Keys.Q:
			gController.moveLeft();
			break;
		case Keys.D:
			gController.moveRight();
			break;
		case Keys.SPACE:
			gController.zoomOut();
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Keys.Z:
			gController.releaseUp();
			break;
		case Keys.S:
			gController.releaseDown();
			break;
		case Keys.Q:
			gController.releaseLeft();
			break;
		case Keys.D:
			gController.releaseRight();
			break;
		case Keys.SPACE:
			//gController.zoomOut();
			break;
		case Keys.ESCAPE:
			gController.pushBackToEditor();
			Gdx.app.log(RVGame.LOG, "Ca tape !!");
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		switch(character){
		case Keys.Z:
			System.out.println("Oh oh oh !!!");
			//gController.releaseUp();
			break;
		case Keys.S:
			gController.releaseDown();
			break;
		case Keys.Q:
			gController.releaseLeft();
			break;
		case Keys.D:
			gController.releaseRight();
			break;
		case Keys.SPACE:
			//gController.zoomOut();
			break;
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	/*private GameController gController;

	private Label headMessage;
	private TextButton quit;

	public GameState saveAndLoad;
	private GameStateButtonGroup gameStateButtons;

	private HistoryWidget history;

	private Board board;
	private GameStatusWidget status;*/

	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		System.out.println("Xbox Connected");
		
	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		System.out.println("Xbox Down");
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		System.out.println("Xbox Up");
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
		//if(controller.getName().equals(anObject))
		System.out.println("Xbox axis");
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode,
			PovDirection value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode,
			boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode,
			boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller,
			int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

}
