package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.WorldM;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WorldM.WIDTH;
		config.height = WorldM.HEIGHT;
		config.title = WorldM.TITLE;
		new LwjglApplication(new WorldM(), config);
	}
}
