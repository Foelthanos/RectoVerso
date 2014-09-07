package com.rectoverso.screen;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.GameRenderer;

public class GameScreen extends AbstractScreen implements InputProcessor{


	
	//private GameController gController;
	private GameRenderer gRenderer;
	
	public GameScreen(RVGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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
	private GameRenderer gRenderer;

	private Label headMessage;
	private TextButton quit;

	public GameState saveAndLoad;
	private GameStateButtonGroup gameStateButtons;

	private HistoryWidget history;

	private Board board;
	private GameStatusWidget status;*/


	/*public GameScreen(ObichouvineGame game, Parameter param, Player p1, Player p2) {
		super(game);
		// TODO Auto-generated constructor stub
		this.board = new Board(9, 9, param);
		this.gRenderer = new GameRenderer(board);
		this.gController = new GameController(board, 
				(param.getfStrike()==FirstStrike.Moscovite)?PawnType.MOSCOVITE:PawnType.SUEDOIS,
						p1, p2);


		Label.LabelStyle titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skin2/titleFont.fnt")), Color.WHITE);

		headMessage = new Label("Tour 1", getSkin());
		headMessage.setStyle(titleStyle);
		quit = new TextButton("Quitter", this.getSkin());

		gameStateButtons = new GameStateButtonGroup(getSkin());

		
		history = new HistoryWidget(this.getSkin());
		history.board = this.board;
		history.gCon = gController;
		status = new GameStatusWidget(this.getSkin(), p1, p2);
		this.status.turn = gController.turn;
		status.gCon = gController;

		//this.saveAndLoad = 
	}


	public void show(){
		super.show();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);

		gameStateButtons.save.addListener(new DefaultInputListener() { // button to exit app  
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
				try {
					GameState.Sauver(board);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}  
		});
		quit.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.getSoundManager().play( ObiSound.CLICK );
				new ObiDialog("Pause", getSkin())
				.button("Abandonner", new DefaultInputListener() { // button to exit app  
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
						game.setScreen(game.getMenuScreen());
						return true;
					}  
				}, true) 
				.rowT()
				.button("Recommencer", new DefaultInputListener() { // button to exit app  
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
						game.setScreen(game.getGameScreen(gController.board.GetParameter(), gController.p1, gController.p2));
						return true;
					}  
				}, true) 
				.button("   Continuer   ") // button that simply closes the dialog  
				.show(stage); // actually show the dialog  ;
			}
		} );

		Table table = super.getTable();
		table.top();

		table.add(headMessage).expandX().colspan(4).spaceTop(20);
		table.row();
		table.add(status).expand().fill().left().width(Block.SIZE*5).height(Block.SIZE*9);
		table.add(history).expand().fill().right().width(Block.SIZE*5).height(Block.SIZE*9);
		table.row();
		table.add(quit).left().expandX().size(150, 40);
		table.add(gameStateButtons).right();
		table.row();
		Gdx.input.setInputProcessor(multiplexer);
		gController.gameStarted = true;
	}


	public void render(float delta) {


		Move c = gController.update(delta);

		if(gController.turn.equals(gController.p1.getTeam()) && gController.p1 instanceof IA){
			gController.p1Computing =true;
		}
		else if(gController.turn.equals(gController.p2.getTeam()) && gController.p2 instanceof IA){
			gController.p2Computing =true;
		}

			this.headMessage.setText("Tour : "+(int)this.gController.turnNum+""+((this.gController.raichi)?" - Raichi !":""));


		if(gController.p1Computing == true)
			status.p1Computing= true;
		else
			status.p1Computing= false;

		if(gController.p2Computing == true)
			status.p2Computing= true;
		else
			status.p2Computing= false;

		if(c != null){
			this.status.switchTurn();
			history.add(c);
		}
		status.updateWidget(gController.mosc, gController.vik);
		super.render(delta);
		gRenderer.render();
		this.stage.draw();
		if(this.gController.gameEnded){
			new ObiDialog("Victoire des "+((gController.turn==PawnType.SUEDOIS)?"Moscovites !":"Vikings !")+((gController.tuichi)?" Victoire par TUICHI !":""), this.getSkin())
			.button("     Quitter      ", new InputListener() { // button to exit app  
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
					game.setScreen(game.getMenuScreen()); 
					return false;  
				}  
			}) 
			.button("    Recommencer    ", new DefaultInputListener() { // button to exit app  
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
					game.setScreen(game.getGameScreen(gController.board.GetParameter(), gController.p1, gController.p2));
					return true;
				}  
			}, true)
			.button(" Nouvelle Partie ", new InputListener() { // button to exit app  
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
					game.setScreen(game.getStartLocalGameScreen()); 
					return false;  
				}  
			}) 
			.show(stage); // actually show the dialog  ;
		}
	}

	public void dispose(){
		super.dispose();
		gRenderer.dispose();

		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		gController.clickPressed(new Vector2(screenX, screenY), button);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		gController.clickReleased(new Vector2(screenX, screenY), button);
		return true;
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
	}*/

}
