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
	public OneSpriteStaticActor szunyog;

	public float szunyogDirX = 0;
	public float szunyogDirY = 0;

	public int mapW = 3000;
	public int mapH = 3000;

	public float szunyog_speed = 8;

	public GameStage(Batch batch, MyGdxGame game, final GameScreen screen) {
		super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

		OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.MENU_BG));
		bg.setSize(mapW, mapH);
		addActor(bg);

		szunyog = new OneSpriteStaticActor(Assets.manager.get(Assets.SZUNYOG));
		szunyog.setSize(szunyog.getWidth() / 2, szunyog.getHeight() / 2);
		szunyog.setDebug(true);
		szunyog.setPosition(getCamera().viewportWidth / 2 - szunyog.getWidth() / 2, getCamera().viewportHeight / 2 - szunyog.getHeight() / 2 );
		addActor(szunyog);

		//setCameraMoveToXY(100, 100);
		//setCameraMoveToZoom(5.0f);

		((OrthographicCamera)getViewport().getCamera()).zoom = 0.5f;


	}

	public float getAngle(float cx, float cy, float tx, float ty) {
		float angle = (float) Math.toDegrees(Math.atan2(ty - cy, tx - cx));

		if(angle < 0){
			angle += 360;
		}

		return angle;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		//getViewport().getCamera().position.x = szunyog.getX();
		//getViewport().getCamera().position.y = szunyog.getY();

		//setCameraMoveToXY(szunyog.getX() + szunyog.getWidth() / 2, szunyog.getY() + szunyog.getHeight() / 2);
		//setCameraMoveSpeed(1000f);
		szunyog.setX(szunyog.getX() - szunyogDirX * szunyog_speed);
		szunyog.setY(szunyog.getY() - szunyogDirY * szunyog_speed);

		getCamera().position.x = szunyog.getX() + szunyog.getWidth() / 2;
		getCamera().position.y = szunyog.getY() + szunyog.getHeight() / 2;

		//if(szunyogDirX < 0)



		//System.out.println(szunyog.getX() + "");
		//szunyog.setY(szunyog.getY() + szunyogDirY / 1000);


		//if(szunyogDirX > 0)
		//	szunyog.setX(szunyog.getX() + (szunyog.getX() - szunyogDirX) / szunyog_speed);


		//if(szunyogDirY != 0)

		//Minél távolabb van a szélétől annál gyorsabb. Ezt javtsd.


		//if(szunyogDirX != 0)
		//System.out.println("dirx: "+ (szunyogDirX ) + ", diry: "+szunyogDirY);

	}

	@Override public void init() {}
}
