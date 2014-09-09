package com.rectoverso.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.rectoverso.utils.DefaultInputListener;

public class LevelEditorScreen extends AbstractScreen {

	private final int BUTTONW = 150; 
	private final int BUTTONH = 25;
	
	private SpriteBatch batch;
	private TextureRegion menuImage;
	
	private List<String> tileList;
	private List<String> bgList;
	private List<String> objectList;
	//private SelectBox<String> tileSel;
	
	private Label lab_tile = new Label("Tuile : ", this.getSkin());
	private Label lab_bg = new Label("Backdround : ", this.getSkin());
	private Label lab_obj = new Label("Objet : ", this.getSkin());

	public LevelEditorScreen(RVGame game) {
		super(game);
	}

	@Override
	public void show()
	{
		super.show();
		// retrieve the default table actor
		/*TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
        menuImage = atlas.findRegion("titleScreenImage");*/
		batch = this.getBatch();

		tileList = new List<String>(getSkin());
		bgList = new List<String>(getSkin());
		objectList = new List<String>(getSkin());
		
		tileList.setItems(new String[] {"Tuile_0", "Tuile_1", "Tuile_2", "Tuile_3", "Tuile_4"});
		bgList.setItems(new String[] {"Background_0","Background_1","Background_2","Background_3"});
		objectList.setItems(new String[] {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10"});
		
		/*tileList.getSelection().setMultiple(false);
		tileList.getSelection().setRequired(false);*/
		
		ScrollPane scrollPane = new ScrollPane(tileList, getSkin());
		ScrollPane scrollPane2 = new ScrollPane(bgList, getSkin());
		ScrollPane scrollPane3 = new ScrollPane(objectList, getSkin());
		//scrollPane.setFlickScroll(false);
		
		/*tileSel = new SelectBox<String>(getSkin(),);
		tileSel.setItems(new String[] {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"});
		*/
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
		
		
		Table table = super.getTable();
		table.left().padRight(0).top().padTop(0);
		/*table.add(loadButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
		table.row();
		table.add(newButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).left();
		table.row();
		table.add(saveButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
		table.row();*/
		table.add(lab_tile).spaceBottom(100);
		table.row();
		table.add(scrollPane).size(this.BUTTONW, this.BUTTONH*14).top().spaceBottom(0).right();
		table.row();
		table.add(lab_bg).spaceBottom(0);
		table.row();
		table.add(scrollPane2).size(this.BUTTONW, this.BUTTONH*4).top().spaceBottom(0).right();
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
		table2.right().padRight(0).top().padTop(0);
		table2.add(newButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).left();
		table2.row();
		table2.add(loadButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
		table2.row();
		table2.add(saveButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
		table2.row();
		table2.add(lab_obj).spaceBottom(100);
		table2.row();
		table2.add(scrollPane3).size(this.BUTTONW, this.BUTTONH*14).top().spaceBottom(0).right();
		table2.row();
		table2.add(testButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
		table2.row();
		table2.add(backButton).size(this.BUTTONW, this.BUTTONH).uniform().spaceBottom(0).right();
	
	}

}
