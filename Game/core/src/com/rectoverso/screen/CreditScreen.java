package com.rectoverso.screen;

import java.util.Locale;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rectoverso.RVGame;
import com.rectoverso.controllers.MusicManager.RVMusic;
import com.rectoverso.controllers.SoundManager.RVSound;
import com.rectoverso.utils.DefaultInputListener;

/**
 * A simple options screen.
 */
public class CreditScreen extends AbstractScreen {
    private Label volumeValue;

    public CreditScreen(RVGame game)
    {
        super(game);
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.defaults().spaceBottom(30);
        table.columnDefaults(0).padRight(20);
        table.add("Conception graphique :").colspan(3).padRight(0);
        table.row();
        table.add("Alisee \"Plumpox\" Preud'homme").colspan(3).padRight(0);
        table.row();
        table.add("Game Design :").colspan(3).padRight(0);
        table.row();
        table.add("Brice \"Sypher\" Andre").colspan(3).padRight(0);
        table.row();
        table.add("Programmeurs :").colspan(3).padRight(0);
        table.row();
        table.add("Brahim \"Violacrimosum\" Berkati").colspan(3).padRight(0);
        table.row();
        table.add("Fabien \"Nexibaf\" Ziebel").colspan(3).padRight(0);
        table.row();
        
        // register the back button
        TextButton backButton = new TextButton("Retour au menu principal", getSkin());
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
        table.row();
        table.add(backButton).height(60).colspan(3).padRight(0).fillX();
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
        float volume = ( game.getPreferencesManager().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
}