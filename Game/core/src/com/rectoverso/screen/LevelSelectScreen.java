package com.rectoverso.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.SoundManager.RVSound;
import com.rectoverso.model.Level;
import com.rectoverso.model.World;
import com.rectoverso.utils.ArrowIconButton;
import com.rectoverso.utils.ButtonListener;
import com.rectoverso.utils.ButtonListener.DefaultInputListener;
import com.rectoverso.utils.LevelIconButton;
import com.rectoverso.utils.LevelIconButton.IconState;

public class LevelSelectScreen extends AbstractScreen {

	private Label lab_level = new Label("CHOOSE YOUR FIGHTAAAAAAAAAAAA !", this.getSkin());
	private Label lab_world = new Label("Monde 0 - Crepuscule", this.getSkin());
	
	private TextButton playButton;
	
	private ArrayList<LevelIconButton> levelIcons = new ArrayList<LevelIconButton>();
	private ArrowIconButton rightArrow = new ArrowIconButton();
	private ArrowIconButton leftArrow = new ArrowIconButton();
	private boolean flagWorldChanged = false;
	
	public LevelSelectScreen(RVGame game) {
		super(game);
	}
	
	@Override
	public void show()
	{
		super.show();
		
		// register the back button
        TextButton backButton = new TextButton("Retour", getSkin());
        backButton.addListener( new ButtonListener.ChangeScreenListener(game,game.getMenuScreen()) );
        
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
                game.getLevelSelectManager().playLevel();
                
                
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
		
		
		rightArrow.create(false,Gdx.graphics.getWidth() - 64,Gdx.graphics.getHeight()/2);
		leftArrow.create(true,64,Gdx.graphics.getHeight()/2);
		loadworld();
		
		
	}
	public void loadworld(){
		
		World world = game.getLevelSelectManager().getWorldSelected();
		setLabelWorld(world.getName());
		
		if (world.getNumber() == 0){
			leftArrow.setVisible(false);
		}
		else{
			leftArrow.setVisible(true);
		}
		
		if (world.getNumber() == 3 || game.getLevelSelectManager().getWorld(world.getNumber()+1).isLocked()){
			rightArrow.setVisible(false);
		}
		else{
			rightArrow.setVisible(true);
		}
		
		//il faut se d�barasser proprement de toutes les icones pour eviter la perte de m�moire
		for(LevelIconButton icon : levelIcons){
			icon.dispose();
		}
		levelIcons.clear();
		int i = 0;
		for(Level lvl : world.getLevels()){
			LevelIconButton levelIcon = new LevelIconButton();
			switch(lvl.getType()){
			case NORMAL:
				levelIcon.create(i, 0, 2);
				i+=2;
				break;
			default :
				levelIcon.create(i, 0, 1);
				i++;
				break;
				
			
			}
			boolean selected = (lvl == game.getLevelSelectManager().getLevelSelected());
			levelIcon.setLevel(lvl,selected);
			levelIcons.add(levelIcon);
		}
		
		
	}
	
	public void render(float delta){
		
		super.render(delta);
		
		this.renderIcons();
		this.renderLabels();
		
		
	}
	private void renderIcons(){
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		//icones de fleche
		rightArrow.render();
		leftArrow.render();
		if(rightArrow.isColliding(mouseX,mouseY)){
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) ){
				if (!flagWorldChanged){
					flagWorldChanged = true;
					game.getLevelSelectManager().setSelectedWorld(game.getLevelSelectManager().getWorldSelected().getNumber()+1);
					this.loadworld();
				}
			}
			else {
				flagWorldChanged = false;
			}
		}
		else if (leftArrow.isColliding(mouseX,mouseY)){
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) ){
				if (!flagWorldChanged){
					flagWorldChanged = true;
					game.getLevelSelectManager().setSelectedWorld(game.getLevelSelectManager().getWorldSelected().getNumber()-1);
					this.loadworld();
				}
			}
			else {
				flagWorldChanged = false;
			}
		}
		
		
		//icones de niveaux
		for(LevelIconButton icon : levelIcons){
			icon.render();
			if (icon.isColliding(Gdx.input.getX(), Gdx.input.getY())){
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
					//on s�lectionne un niveau
					Gdx.app.log(RVGame.LOG, "select level "+icon.getLevel().getName());
					game.getLevelSelectManager().setLevelSelected(icon.getLevel());
					icon.setState(IconState.SELECTED);
					
					//il faut d�selectionner toute autre icone
					for(LevelIconButton i : levelIcons){
						if (i != icon){
							if (i.getState() == IconState.SELECTED){
								i.setState(IconState.IDLE);
							}
						}
					}
				}
			}
		}
	}
	private void renderLabels(){
		Level level = game.getLevelSelectManager().getLevelSelected();
		World world = game.getLevelSelectManager().getWorldSelected();
		
		if (level != null && world != null){
			String text = "";
			switch(level.getType()){
			case NORMAL :
				text = "chapitre";
				break;
			case SECRET :
				text = "secret";
				break;
			case MOVIE :
				text = "cinématique";
				break;
			}
			setLabelLevel(text+" "+level.getNumber()+" - "+level.getName());
			this.playButton.setVisible(true);
		}
		else{
			setLabelLevel("CHOOSE YOUR FIGHTAAAAAAAAAAAA !");
			this.playButton.setVisible(false);
		}
	}
	
 	public void setLabelLevel(String texte){
		lab_level.setText(" - "+texte+" - ");
	}
 	public void setLabelWorld(String texte){
		lab_world.setText(texte);
	}
}
