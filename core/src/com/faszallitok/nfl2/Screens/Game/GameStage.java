package com.faszallitok.nfl2.Screens.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyGdxGame;

public class GameStage extends MyStage {
	private OneSpriteStaticActor menu;


	public GameStage(Batch batch, MyGdxGame game, final GameScreen screen) {
		super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

		OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.MENU_BG));
		bg.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
		addActor(bg);

		menu = new OneSpriteStaticActor(Assets.manager.get(Assets.MENU_ICON));
		menu.setSize(30, 30);
		menu.setPosition(getViewport().getWorldWidth() - 40, getViewport().getWorldHeight() - 40);
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				screen.isPaused = true;
			}
		});
		addActor(menu);

	}

	@Override public void init() {}
}
