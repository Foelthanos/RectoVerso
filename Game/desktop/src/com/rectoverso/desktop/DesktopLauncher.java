package com.rectoverso.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rectoverso.RVGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.title = "RectoVerso";
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new RVGame(), config);
	}
}
