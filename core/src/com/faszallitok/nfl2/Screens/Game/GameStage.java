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
import java.util.ArrayList;
import java.util.Random;

public class GameStage extends MyStage {
	public OneSpriteStaticActor szunyog;

	public float szunyogDirX = 0;
	public float szunyogDirY = 0;

	public int mapW = 5000;
	public int mapH = 5000;

	public float szunyog_speed = 6;

	public float HUNGER = 100; //Éhség
	public float hungerLoss = 0.08f; //Milyen éhes legyen időközönként
	public boolean isSucking = false; //Jelenleg vért szív-e
	public float suckTime = 100; //Mennyi idő egy szívás
	public float currSuckTime = 0; //Mennyi ideje szívott
	public float suckSpeed = 1; //Szívás gyorsaság
	public float suckHunger = 10; //Mennyi éhséget vonjon szívás után
	public int currSuckingArea = 0; //Jelenleg melyik területet szívja

	public int dashCD = 120; //Dash töltési idő
	public int dashCurrCD = 120; //Hol tart a visszatöltés

	public int lastStrike = 0; //Hány actnyira volt az utolsó csapás
	public int strikeRarity = 100; //Milyen gyakran legyen csapás(maximum)

	private ArrayList<OneSpriteStaticActor> suckAreas = new ArrayList<OneSpriteStaticActor>();
	private ArrayList<OneSpriteStaticActor> suckedAreas = new ArrayList<OneSpriteStaticActor>();

	private Random rand = new Random();
	private OneSpriteStaticActor map;

	public GameStage(Batch batch, MyGdxGame game, final GameScreen screen) {
		super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

		map = new OneSpriteStaticActor(Assets.manager.get(Assets.ARM_MAP));
		map.setSize(mapW, mapH);

		for (int i = 0; i < 60; i++) {

			OneSpriteStaticActor suck_actor = new OneSpriteStaticActor(Assets.manager.get(Assets.SUCK_MARK));
			suck_actor.setSize(suck_actor.getWidth() / 6, suck_actor.getHeight() / 6);
			suck_actor.setDebug(true);
			suck_actor.addBaseCollisionRectangleShape();

			float dx = rand.nextInt(mapW - (int)suck_actor.getWidth()*2) + (int)suck_actor.getWidth();
			float dy =  rand.nextInt(mapH - (int)suck_actor.getHeight()*2) + (int)suck_actor.getHeight();

			suck_actor.setPosition(dx, dy);
			suckAreas.add(suck_actor);
			addActor(suck_actor);
		}


		szunyog = new OneSpriteStaticActor(Assets.manager.get(Assets.SZUNYOG));
		szunyog.setSize(80, 80);
		szunyog.setDebug(true);
		szunyog.addBaseCollisionRectangleShape();
		szunyog.setPosition(getCamera().viewportWidth / 2 - szunyog.getWidth() / 2, getCamera().viewportHeight / 2 - szunyog.getHeight() / 2 );
		addActor(szunyog);

		//setCameraMoveToXY(100, 100);
		//setCameraMoveToZoom(5.0f);

		((OrthographicCamera)getViewport().getCamera()).zoom = 0.5f;
		//((OrthographicCamera)getViewport().getCamera()).zoom = 8f;


	}

	public float getAngle(float cx, float cy, float tx, float ty) {
		float angle = (float) Math.toDegrees(Math.atan2(ty - cy, tx - cx));

		if(angle < 0){
			angle += 360;
		}

		return angle;
	}

	public void suckBlood() {
		if(isSucking) return;
		System.out.println("size: "+suckAreas.size());
		for (int i = 0; i < suckAreas.size(); i++) {
			if(szunyog.overlaps(suckAreas.get(i))) {
				currSuckingArea = i;
				isSucking = true;
				System.out.println("talált: "+i);
				break;
			}
		}

		System.out.println("Egyik se lol.");
	}

	void finishedSucking() {
		OneSpriteStaticActor area = suckAreas.get(currSuckingArea);
		System.out.println("Végzett");

		OneSpriteStaticActor mark = new OneSpriteStaticActor(Assets.manager.get(Assets.SUCKED_AREA));
		mark.setSize(area.getWidth(), area.getHeight());
		mark.setPosition(area.getX(), area.getY());
		suckedAreas.add(mark);

		float new_dx = rand.nextInt(mapW - (int)area.getWidth()*2) + (int)area.getWidth();
		float new_dy =  rand.nextInt(mapH - (int)area.getHeight()*2) + (int)area.getHeight();
		area.setPosition(new_dx, new_dy);
	}

	@Override
	public void act(float delta) {

		getBatch().setProjectionMatrix(getCamera().combined);
		getBatch().begin();
		map.draw(getBatch(), 1);
		for(int i = 0; i < suckedAreas.size(); i++) {
			suckedAreas.get(i).draw(getBatch(), 1);
		}
		getBatch().end();

		super.act(delta);
		//getViewport().getCamera().position.x = szunyog.getX();
		//getViewport().getCamera().position.y = szunyog.getY();

		//setCameraMoveToXY(szunyog.getX() + szunyog.getWidth() / 2, szunyog.getY() + szunyog.getHeight() / 2);
		//setCameraMoveSpeed(1000f);

		//isSucking = true;

		if(isSucking) {
			if(currSuckTime >= suckTime){
				isSucking = false;
				HUNGER += suckHunger;
				finishedSucking();
				currSuckTime = 0;
			}else
			currSuckTime += suckSpeed;
		}else{
			HUNGER -= hungerLoss;

			szunyog.setX(szunyog.getX() - szunyogDirX * szunyog_speed);
			szunyog.setY(szunyog.getY() - szunyogDirY * szunyog_speed);

			getCamera().position.x = szunyog.getX() + szunyog.getWidth() / 2;
			getCamera().position.y = szunyog.getY() + szunyog.getHeight() / 2;
		}

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

	@Override
	public void draw() {
		super.draw();
	}

	@Override public void init() {}
}
