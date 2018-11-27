package com.faszallitok.nfl2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.faszallitok.nfl2.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "Public");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 576;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
