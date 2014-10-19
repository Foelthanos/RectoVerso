package com.rectoverso.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.rectoverso.model.Tile.TileContent;
import com.rectoverso.utils.ButtonListener;
import com.rectoverso.controllers.GameController;
import com.rectoverso.controllers.GameRenderer;
import com.rectoverso.controllers.LevelEditorManager;
import com.rectoverso.controllers.LevelManager;

public class LevelEditorScreen extends AbstractScreen {

	private final int PANELW = 200; 
	private final int BUTTONH = 40;
	
	private LevelEditorManager manager;
	//private Level level;
	
	
	private SpriteBatch batch;
	private TextureRegion menuImage;
	
	private boolean flagLeftClic = false;
	private boolean flagRightClic = false;
	private Vector3 cameraDragPosition;
	private Vector3 mouseDragPosition;
	
	
	private Table tableTiles = new Table(getSkin());
	private Table tableObjects = new Table(getSkin());
	private Table tableDecors = new Table(getSkin());
	private Table tableBackground = new Table(getSkin());
	private ScrollPane scrollPaneItems;

	public LevelEditorScreen(RVGame game) {
		
		super(game);
		//this(game, Level.createEmptyLevel(10) );
		this.manager = this.game.getLevelEditorManager();
		if(manager.getLevelEdited() == null){
			System.out.println("création d'un niveau vide !");
			Level level = Level.createEmptyLevel(10);
			manager.setLevelEdited(level);
		}
	}

	@Override
	public void show()
	{
		super.show();
		// retrieve the default table actor
		batch = this.getBatch();
		
		// register the button "charger"
		TextButton loadButton = new TextButton("Charger", getSkin() );
		loadButton.addListener(new ButtonListener.DefaultInputListener() {
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
		newButton.addListener(new ButtonListener.DefaultInputListener() {
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
		saveButton.addListener(new ButtonListener.DefaultInputListener() {
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
		testButton.addListener(new ButtonListener.DefaultInputListener() {
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
				game.setScreen(game.getGameScreen(manager.getLevelEdited(), true));
			}
		} );

		// register the back button
        TextButton backButton = new TextButton("Retour", getSkin());
        backButton.addListener( new ButtonListener.ChangeScreenListener(game,game.getMenuScreen()) );
           
        
     // register the manager button
        TextButton propertiesButton = new TextButton("Proprietes", getSkin());
        propertiesButton.addListener( new ButtonListener.DefaultInputListener() {
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
        tileButton.addListener( new ButtonListener.DefaultInputListener() {
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
        BGButton.addListener( new ButtonListener.DefaultInputListener() {
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
        decorButton.addListener( new ButtonListener.DefaultInputListener() {
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
        objectButton.addListener( new ButtonListener.DefaultInputListener() {
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
		//le panel est en haut à gauche
		this.tableTiles.left().top();

		//ici on case 4 bouttons par lignes
		int column = 0;
		for (TileContent tileType : TileContent.values()){
			TextButton button = new TextButton(tileType.toString(), getSkin());
			button.setName(tileType.toString());
			button.addListener( new ButtonListener.TileButtonListener(game,tileType) );
			
			//ajoute le bouton au panel
			this.tableTiles.add(button).size(PANELW/4,this.BUTTONH).bottom().left();
			
			// quand le nombre max de bouton est atteint, on passe à la ligne suivante
			column ++;
			if(column == 4)this.tableTiles.row();
			
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
		
		this.manager.getGameRenderer().render();
		
		this.renderMap(delta);
		
		this.stage.draw();
		
	}
	public void renderMap(float delta){
		
		OrthographicCamera camera = this.manager.getGameController().getCamera();
		
		if(Gdx.input.getX() < 225 || Gdx.input.getY() < 40) return;
		
		//obtenir les coordonnées de la souris par rapport à la caméra
		float mouseX = Gdx.input.getX() - Gdx.graphics.getWidth()/2 + camera.position.x;
		float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight()/2 + camera.position.y;
		
		//une tuile est elle visée ?
		Tile tile = this.manager.getGameController().getTile(mouseX, mouseY);
		if (tile != null ){
			this.manager.setTileFocused(tile);
		}
		this.renderTileFocused();
		
		//as t- on cliqué gauche?
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) ){
			if(!flagLeftClic){
				//flagLeftClic = true;
				this.manager.changeTile();
			}
		}
		else{
			flagLeftClic = false;
		}

		//as t- on cliqué droit?
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) ){
			if(!flagRightClic){
				System.out.println("Retaining position");
				flagRightClic = true;
				this.cameraDragPosition = new Vector3(camera.position);
				this.mouseDragPosition = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
			}
			Vector3 mouseOffset = new Vector3(Gdx.input.getX() ,  Gdx.graphics.getHeight() - Gdx.input.getY(), 0).sub(mouseDragPosition)  ;
			System.out.println("Mouse offset : " + cameraDragPosition.toString());
			camera.position.set( new Vector3(cameraDragPosition).sub(mouseOffset));
			
		}
		else{
			flagRightClic = false;
		}
		
		camera.update();
	}
	
	private void renderTileFocused() {
		// TODO Auto-generated method stub
		
		Tile tileFocused = this.manager.getTileFocused();
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(this.manager.getGameController().getCamera().combined);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(0f, 1f, 1f, 1f);
		
		int tx = (int) tileFocused.getPosition().x + 64;
		int ty = (int) tileFocused.getPosition().y + 96;
		
		shapeRenderer.line( tx,  ty, tx + 64, ty - 32);
		shapeRenderer.line( tx,  ty, tx, ty - 64);
		shapeRenderer.line( tx + 64, ty - 32, tx , ty - 64);
		shapeRenderer.line( tx + 64, ty - 32, tx -64, ty - 32);
		shapeRenderer.line( tx , ty - 64, tx - 64 , ty - 32);
		shapeRenderer.line( tx - 64, ty - 32, tx , ty );
		
		shapeRenderer.end();
		
	}

	
}
