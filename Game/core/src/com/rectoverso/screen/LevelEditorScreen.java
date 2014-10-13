package com.rectoverso.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.MusicManager.RVMusic;
import com.rectoverso.controllers.SoundManager.RVSound;
import com.rectoverso.model.Level;
import com.rectoverso.model.Level.LevelType;
import com.rectoverso.model.Tile;
import com.rectoverso.utils.DefaultInputListener;
import com.rectoverso.controllers.GameController;
import com.rectoverso.controllers.GameRenderer;
import com.rectoverso.controllers.LevelManager;

public class LevelEditorScreen extends AbstractScreen {

	private final int PANELW = 200; 
	private final int BUTTONH = 40;
	
	private GameRenderer levelrenderer ;
	private GameController levelController;
	private Level level;
	
	
	private SpriteBatch batch;
	private TextureRegion menuImage;
	
	
	
	private Table tableTiles = new Table(getSkin());
	private Table tableObjects = new Table(getSkin());
	private Table tableDecors = new Table(getSkin());
	private Table tableBackground = new Table(getSkin());
	private ScrollPane scrollPaneItems;

	public LevelEditorScreen(RVGame game) {
		super(game);
		this.level = Level.createEmptyLevel(10);
		this.levelController = new GameController(this.level);
		this.levelController.isEditor = true;
		this.levelrenderer = new GameRenderer(levelController);
		//levelController.getCamera().viewportHeight = 100;
		//levelController.getCamera().viewportWidth = 100;
		this.levelController.getCamera().position.x = - 50;
		this.levelController.getCamera().position.y = - 100;
		
	}
	
	public LevelEditorScreen(RVGame game, Level level) {
		super(game);
		this.level = level;
		this.levelController = new GameController(this.level);
		this.levelController.isEditor = true;
		this.levelrenderer = new GameRenderer(levelController);
		//levelController.getCamera().viewportHeight = 100;
		//levelController.getCamera().viewportWidth = 100;
		this.levelController.getCamera().position.x = - 50;
		this.levelController.getCamera().position.y = - 100;
		
	}

	@Override
	public void show()
	{
		super.show();
		// retrieve the default table actor
		batch = this.getBatch();
		
		// register the button "charger"
		TextButton loadButton = new TextButton("Charger", getSkin() );
		loadButton.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
			}
		} );
		
		TextButton newButton = new TextButton("Nouveau", getSkin() );
		newButton.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
			}
		} );
		

		// register the button "save"
		TextButton saveButton = new TextButton("Sauvegarder", getSkin() );
		saveButton.addListener(new DefaultInputListener() {
			@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.getLevelManager().saveLevel();
			}
		} );
		
		// register the button "test"
		TextButton testButton = new TextButton("Tester", getSkin() );
		testButton.addListener(new DefaultInputListener() {
		@Override
			public void touchUp(
					InputEvent event,
					float x,
					float y,
					int pointer,
					int button )
			{
				super.touchUp(event, x, y, pointer, button);
				game.getSoundManager().play(RVSound.CLICK );
				game.setScreen(game.getGameScreen(level, true));
			}
		} );

		// register the back button
        TextButton backButton = new TextButton("Retour", getSkin());
        backButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        });
        
     // register the manager button
        TextButton propertiesButton = new TextButton("Proprietes", getSkin());
        propertiesButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
            }
        
        });
		
        TextButton tileButton = new TextButton("Tiles", getSkin());
        tileButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
                scrollPaneItems.setWidget(tableTiles);
            }
        });
		
        TextButton BGButton = new TextButton("BGs", getSkin());
        BGButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
                scrollPaneItems.setWidget(tableBackground);
            }
        });
        
        TextButton decorButton = new TextButton("Dec", getSkin());
        decorButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
                scrollPaneItems.setWidget(tableDecors);
            }
        });
        
        TextButton objectButton = new TextButton("Obj", getSkin());
        objectButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play(RVSound.CLICK );
                scrollPaneItems.setWidget(tableObjects);
            }
        });
        
		Table table = super.getTable();
		table.left().padRight(0).top().padTop(this.BUTTONH);
		table.add(tileButton).size(this.PANELW/4, this.BUTTONH).uniform().spaceBottom(0).left();
		table.add(BGButton).size(this.PANELW/4, this.BUTTONH).uniform().spaceBottom(0).left();
		table.add(decorButton).size(this.PANELW/4, this.BUTTONH).uniform().spaceBottom(0).left();
		table.add(objectButton).size(this.PANELW/4, this.BUTTONH).uniform().spaceBottom(0).left();
		table.row();
		
		
		//table.add(tileList).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom((tileList.getItems().size - 1)*this.BUTTONH).right();
		/*for(int i = 0 ; i < tileList.getItems().size ; i++){
			table.row();
		}*/
		
		Table table2 = new Table(getSkin());
		table2.setFillParent(true);
		stage.addActor(table2);
		if( RVGame.DEV_MODE ) {
            table2.debug();
        }
		table2.right().padRight(0).top();
		table2.add(backButton).size(Gdx.graphics.getWidth()/5, this.BUTTONH).uniform().right();
		table2.add(propertiesButton).size(Gdx.graphics.getWidth()/5, this.BUTTONH).uniform().right();
		table2.add(loadButton).size(Gdx.graphics.getWidth()/5, this.BUTTONH).uniform().right();
		table2.add(saveButton).size(Gdx.graphics.getWidth()/5, this.BUTTONH).uniform().right();
		table2.add(newButton).size(Gdx.graphics.getWidth()/5, this.BUTTONH).uniform().left();
		
		//CHARGER LES OBJETS
		this.loadTiles();
		this.loadBackGrounds();
		this.loadDecors();
		this.loadObjects();
		
		this.scrollPaneItems = new ScrollPane(this.tableBackground, getSkin());
		this.scrollPaneItems.setFlickScroll(true);
		this.scrollPaneItems.setFadeScrollBars(false);
		//ScrollPane scrollPaneTile = new ScrollPane(table2, getSkin());
		
		Table table3 = new Table(getSkin());
		table3.setFillParent(true);
		stage.addActor(table3);
		if( RVGame.DEV_MODE ) {
            table3.debug();
        }
		table3.left().padRight(0).top().padTop(this.BUTTONH*5);
		table3.add(scrollPaneItems).size(this.PANELW+25, Gdx.graphics.getHeight() - this.BUTTONH*6).bottom().spaceBottom(0).left();
		table3.row();
		table3.add(testButton).size(this.PANELW, this.BUTTONH).uniform().left();
	}

	private void loadTiles(){
		if( RVGame.DEV_MODE ) {
			this.tableTiles.debug();
		}
		this.tableTiles.left().top();

		for (int i = 0; i < 15;i++){
			for (int j = 0; j < 4;j++){
				TextButton button = new TextButton(i + ":" + j, getSkin());
				this.tableTiles.add(button).size(PANELW/4,this.BUTTONH).bottom().left();
			}
			this.tableTiles.row();
		}
        
	}
	
	private void loadObjects(){
		if( RVGame.DEV_MODE ) {
			this.tableObjects.debug();
		}
		this.tableObjects.left().top();

		for (int j = 0; j < 4;j++){
			TextButton button = new TextButton("objet qui fait ça " + j, getSkin());
			this.tableObjects.add(button).size(PANELW,this.BUTTONH/2).bottom().left();
			this.tableObjects.row();
		}
		
        
	}
	
	private void loadDecors(){
		if( RVGame.DEV_MODE ) {
			this.tableDecors.debug();
		}
		this.tableDecors.left().top();

		for (int i = 0; i < 15;i++){
			for (int j = 0; j < 3;j++){
				TextButton button = new TextButton(i + ":" + j, getSkin());
				this.tableDecors.add(button).size(PANELW/3,this.BUTTONH*2).bottom().left();
			}
			this.tableDecors.row();
		}
        
	}
	
	private void loadBackGrounds(){
		if( RVGame.DEV_MODE ) {
			this.tableBackground.debug();
		}
		this.tableBackground.left().top();

		for (int j = 0; j < 15;j++){
			TextButton button = new TextButton(j+"", getSkin());
			this.tableBackground.add(button).size(PANELW,this.BUTTONH*2).bottom().left();
			this.tableBackground.row();
		}
		this.tableBackground.row();
        
	}

	@Override
	public void render(float delta) {
		
		//super.render(delta);
		//this.levelController.update(delta);;
		
		//System.out.println("Position souris : "+ mouseX + " : " + mouseY);
		
		
		
		this.levelrenderer.render();
		this.renderVue(delta);
		
		this.stage.draw();
		
	}
	public void renderVue(float delta){
		
		OrthographicCamera camera = this.levelController.getCamera();
		camera.update();
		
		System.out.println("Mouse Input "+Gdx.input.getX() + " ; " + Gdx.input.getY());
		if(Gdx.input.getX() < 225 || Gdx.input.getY() < 40) return;
		
		float mouseX = Gdx.input.getX() - Gdx.graphics.getWidth()/2 + camera.position.x;
		float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight()/2 + camera.position.y;
		
		Tile tile = this.levelrenderer.renderTileFocused(mouseX,mouseY);
		if (tile != null ){
			//System.out.println(tile.getPosition().x + " ; " + tile.getPosition().y);
		}
	}
}
