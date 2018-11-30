package com.faszallitok.nfl2.Screens.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyGdxGame;

import java.awt.Point;

public class GameStage extends MyStage {
	private OneSpriteStaticActor menu;
	public OneSpriteStaticActor joy;
	public OneSpriteStaticActor szunyog;

	public float centerX;
	public float centerY;

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


		szunyog = new OneSpriteStaticActor(Assets.manager.get(Assets.SZUNYOG));
		szunyog.setPosition(getViewport().getWorldWidth() / 2 - szunyog.getWidth() / 2, getViewport().getWorldHeight() / 2 - szunyog.getHeight() / 2 );
		addActor(szunyog);

		OneSpriteStaticActor joy_back = new OneSpriteStaticActor(Assets.manager.get(Assets.JOY_BACK));
		joy_back.setX(getViewport().getWorldWidth() - joy_back.getWidth() - 50);
		joy_back.setY(50);
		addActor(joy_back);

		joy = new OneSpriteStaticActor(Assets.manager.get(Assets.JOY_FRONT));
		centerX = joy_back.getX() - joy_back.getWidth() / 2 + joy.getWidth() / 2;
		centerY = joy_back.getY() - joy_back.getHeight() / 2 + joy.getHeight() / 2;
		joy.setPosition(centerX ,centerY);

		joy.addListener(new DragListener() {
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				super.drag(event, x, y, pointer);

				float tx = joy.getX() + x - joy.getWidth() / 2;
				float ty = joy.getY() + y - joy.getHeight() / 2;

				if(tx < centerX - 45 || tx > centerX + 45){
					joy.setX(joy.getX());
				}else{
					joy.setX(tx);
				}

				if(ty < centerY - 50 || ty > centerY + 50){
					joy.setY(joy.getY());
				}else{
					joy.setY(ty);
				}


				System.out.println("fok: "+getAngle(centerX, centerY, tx, ty));
				szunyog.setRotation(getAngle(centerX, centerY, tx, ty) - 90);

				System.out.println("x: "+ x +"");
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				super.dragStop(event, x, y, pointer);
				joy.setPosition(centerX, centerY);
			}
		});
		addActor(joy);


	}

	public float getAngle(float cx, float cy, float tx, float ty) {
		float angle = (float) Math.toDegrees(Math.atan2(ty - cy, tx - cx));

		if(angle < 0){
			angle += 360;
		}

		return angle;
	}

	@Override public void init() {}
}
