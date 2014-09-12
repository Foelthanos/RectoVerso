package com.rectoverso.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.SoundManager.RVSound;
import com.rectoverso.model.Level;
import com.rectoverso.model.World;
import com.rectoverso.utils.DefaultInputListener;

public class LevelSelectScreen extends AbstractScreen {

	private Label lab_level = new Label("CHOOSE YOUR FIGHTAAAAAAAAAAAA !", this.getSkin());
	private Label lab_world = new Label("Monde 0 - Crepuscule", this.getSkin());
	
	private TextButton playButton;
	
	public LevelSelectScreen(RVGame game) {
		super(game);
	}
	
	@Override
	public void show()
	{
		super.show();
		
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
        
     // register the play button
        playButton = new TextButton("Lancer", getSkin());
        playButton.addListener( new DefaultInputListener() {
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
        
     // retrieve the default table actor
        Table table = super.getTable();
        table.top().right().padTop(20).padRight(20);
        //table.columnDefaults(0).padRight(0);
        table.row();
        table.add(backButton).width(100).height(50);
        
        
        Table table2 = new Table(getSkin());
		table2.setFillParent(true);
		stage.addActor(table2);
		if( RVGame.DEV_MODE ) {
            table2.debug();
        }
		table2.bottom().padBottom(20).padRight(20);
		table2.row();
        table2.add(lab_level).padBottom(20);
		table2.row();
        table2.add(playButton).width(200).height(50);
        playButton.setVisible(false);
        
        
        Table table3 = new Table(getSkin());
		table3.setFillParent(true);
		stage.addActor(table3);
		if( RVGame.DEV_MODE ) {
            table3.debug();
        }
		table3.top().left().padTop(20).padLeft(20);
		table3.add(lab_world);
        //setLabelLevel("1.2 - Sous les Ombres");
		lab_world.setFontScale(2);
		
	}
	
	public void render(float delta){
		
		super.render(delta);
		
		World world = game.getLevelSelectManager().getWorldSelected();
		if (world != null){
			setLabelWorld(world.getName());
		}
		
		
		Level level = game.getLevelSelectManager().getLevelSelected();
		if (level != null && world != null){
			setLabelLevel(world.getNumber()+"."+level.getNumber()+" - "+level.getName());
		}
		else{
			setLabelLevel("CHOOSE YOUR FIGHTAAAAAAAAAAAA !");
		}
	}
	
 	public void setLabelLevel(String texte){
		lab_level.setText(" - "+texte+" - ");
	}
 	public void setLabelWorld(String texte){
		lab_world.setText(texte);
	}
}
