package com.faszallitok.nfl2.Screens.Game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.End.EndScreen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GameStage extends MyStage {
	private Random rand = new Random();

	public OneSpriteStaticActor szunyog;

	public float szunyogDirX = 0;
	public float szunyogDirY = 0;

	public int mapW = 5000;
	public int mapH = 5000;

	public float szunyog_speed = 6; // def: 6

	public float HUNGER = 100; //Éhség
	public float hungerLoss = 0.04f; //Milyen éhes legyen időközönként
	public boolean isSucking = false; //Jelenleg vért szív-e
	public float suckTime = 100; //Mennyi idő egy szívás
	public float currSuckTime = 0; //Mennyi ideje szívott
	public float suckSpeed = 1; //Szívás gyorsaság
	public float suckHunger = 10; //Mennyi éhséget vonjon szívás után
	public int currSuckingArea = 0; //Jelenleg melyik területet szívja

	public int dashCD = 300; //Dash töltési idő
	public int dashCurrCD = dashCD; //Hol tart a visszatöltés
	public float dashSpeed = 2; //Mennyivel szorozza a gyorsaságot, dashkor
	public int dashDuration = 20; //Mennyi ideig legyen dash
	public int dashCurrDur = 0; //Mennyi ideje megy a dash.
	public boolean isDashing = false;

	public int maxSuckedAreas = 30;

	public int lastStrike = 0; //Hány actnyira volt az utolsó csapás
	public int strikeRarityMin = 800; //Milyen gyakran legyen csapás(maximum)
	public int strikeRarityMax = 2000; //Milyen gyakran legyen csapás(maximum)
	public int nextStrike = 0;
	public int nextStrikeCounter = 0;
	public boolean isStriking = false; //Lecsapás van-e
	public int nextStrikeDownRarityMin = 300;
	public int nextStrikeDownRarityMax = 500;
	public int nextStrikeDown = 0;
	public int nextStrikeDownCounter = 0;
	public float strikingAtX = 0;
	public float strikingAtY = 0;
	public boolean strikeInbound = false; //Lecsapás célzás
	public float strikeSpeed = 13;
	public float strikeMinW;
	public float strikeMinH;
	public float strikeMaxW;
	public float strikeMaxH;

	public float strikeDestX = 0;
	public float strikeDestY = 0;
	public float strikeCurrX = 0;
	public float strikeCurrY = 0;
	public float strikeDestSpeed = 30;


	//Stats
	private int dealtDamage = 0;
	private long ellapsedTime = System.currentTimeMillis();
	private int ellapsedSecs = 0;
	private int missedStrikes = 0;


	private ArrayList<OneSpriteStaticActor> suckAreas = new ArrayList<OneSpriteStaticActor>();
	private ArrayList<OneSpriteStaticActor> suckedAreas = new ArrayList<OneSpriteStaticActor>();

	private OneSpriteStaticActor map;

	private OneSpriteStaticActor arm;
	private OneSpriteStaticActor arm_shadow;


	public GameStage(Batch batch, MyGdxGame game, final GameScreen screen, int level) {
		super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

		if(level == 0)
			map = new OneSpriteStaticActor(Assets.manager.get(Assets.ARM_MAP));
		else if(level == 1)
			map = new OneSpriteStaticActor(Assets.manager.get(Assets.LEG_MAP));
		else if(level == 2)
			map = new OneSpriteStaticActor(Assets.manager.get(Assets.STOMACH_MAP));
		else
			map = new OneSpriteStaticActor(Assets.manager.get(Assets.ARM_MAP));
		map.setSize(mapW, mapH);

		for (int i = 0; i < 60; i++) {

			OneSpriteStaticActor suck_actor = new OneSpriteStaticActor(Assets.manager.get(Assets.SUCK_MARK));
			suck_actor.setSize(suck_actor.getWidth() / 6, suck_actor.getHeight() / 6);
			//suck_actor.setDebug(true);
			suck_actor.addBaseCollisionRectangleShape();

			float dx = rand.nextInt(mapW - (int)suck_actor.getWidth()*2) + (int)suck_actor.getWidth();
			float dy =  rand.nextInt(mapH - (int)suck_actor.getHeight()*2) + (int)suck_actor.getHeight();

			suck_actor.setPosition(dx, dy);
			suckAreas.add(suck_actor);
			addActor(suck_actor);
		}


		szunyog = new OneSpriteStaticActor(Assets.manager.get(Assets.SZUNYOG));
		szunyog.setSize(80, 80);
		szunyog.addBaseCollisionRectangleShape();
		szunyog.setPosition(getCamera().viewportWidth / 2 - szunyog.getWidth() / 2, getCamera().viewportHeight / 2 - szunyog.getHeight() / 2 );

		arm = new OneSpriteStaticActor(Assets.manager.get(Assets.ARM));
		//Min-size
		strikeMaxW = arm.getWidth() / 2;
		strikeMaxH = arm.getHeight() / 2;
		arm.setSize(arm.getWidth() / 3, arm.getHeight() / 3);
		strikeMinW = arm.getWidth();
		strikeMinH = arm.getHeight();
		arm.setPosition(szunyog.getX() + 50, szunyog.getY() + 50);
		arm.setVisible(false);

		arm_shadow = new OneSpriteStaticActor(Assets.manager.get(Assets.ARM_SHADOW));
		arm_shadow.setSize(strikeMaxW, strikeMaxH);
		arm_shadow.setVisible(false);
		addActor(szunyog);
		addActor(arm_shadow);
		addActor(arm);


		//setCameraMoveToXY(100, 100);
		//setCameraMoveToZoom(5.0f);

		((OrthographicCamera)getViewport().getCamera()).zoom = 0.5f;
		//((OrthographicCamera)getViewport().getCamera()).zoom = 8f;

		nextStrike = rand.nextInt(strikeRarityMax - strikeRarityMin) + strikeRarityMin;
		nextStrikeCounter = 0;
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
		for (int i = 0; i < suckAreas.size(); i++) {
			if(szunyog.overlaps(suckAreas.get(i))) {
				currSuckingArea = i;
				isSucking = true;
				szunyog.setTexture(Assets.manager.get(Assets.SZUNYOG_SUCK));
				break;
			}
		}
	}

	void finishedSucking() {
		OneSpriteStaticActor area = suckAreas.get(currSuckingArea);
		dealtDamage++;
		szunyog.setTexture(Assets.manager.get(Assets.SZUNYOG));

		if(suckedAreas.size() >= maxSuckedAreas) {
			suckedAreas.get(rand.nextInt(suckAreas.size())).setPosition(area.getX(), area.getY());
		}else{
			OneSpriteStaticActor mark = new OneSpriteStaticActor(Assets.manager.get(Assets.SUCKED_AREA));
			mark.setSize(area.getWidth(), area.getHeight());
			mark.setPosition(area.getX(), area.getY());
			suckedAreas.add(mark);
		}

		float new_dx = rand.nextInt(mapW - (int)area.getWidth()*2) + (int)area.getWidth();
		float new_dy =  rand.nextInt(mapH - (int)area.getHeight()*2) + (int)area.getHeight();
		area.setPosition(new_dx, new_dy);
	}

	public void dash() {
			System.out.println(dashCurrCD + " = " + dashCD);
		if(dashCurrCD < dashCD) {
			return;
		}
		isDashing = true;
		System.out.println("Dash");
		dashCurrCD = 0;
	}

	void startStrike() { //Célzás
		arm.setSize(strikeMaxW, strikeMinH);
		arm_shadow.setVisible(true);
		nextStrikeDown = rand.nextInt(nextStrikeDownRarityMax - nextStrikeDownRarityMin) + nextStrikeDownRarityMin;
		nextStrikeCounter = 0;
		strikeInbound = true;
	}

	void strikeDown() { //Lecsapás
		strikeInbound = false;
		isStriking = true;
		strikingAtX = arm.getX();
		strikingAtY = arm.getY();

	}

	void finishedStriking() {
		isStriking = false;
		arm.setVisible(false);
		arm_shadow.setVisible(false);

		if(szunyog.getX() + szunyog.getWidth() >= arm.getX() && szunyog.getX() <= arm.getX() + arm.getWidth() - 150){
			System.out.println("szunyog.x = "+szunyog.getX() + " arm.x = " + arm.getX());
			if(szunyog.getY() + szunyog.getHeight() >= arm.getY() && szunyog.getY() <= arm.getY() + arm.getHeight() - 120) {
				game.setScreen(new EndScreen(game, 1, dealtDamage, ellapsedSecs, missedStrikes));
				return;
			}
		}

		missedStrikes++;

		nextStrike = rand.nextInt(strikeRarityMax - strikeRarityMin) + strikeRarityMin;
		nextStrikeCounter = 0;
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		if((ellapsedTime + 1000) <= System.currentTimeMillis()) {
			ellapsedTime = System.currentTimeMillis();
			ellapsedSecs++;
		}

		//Min pos
		arm_shadow.setSize(arm.getWidth(), arm.getHeight());

		if(strikeInbound) {
			strikeCurrX += (strikeDestX - strikeCurrX) / strikeDestSpeed;
			strikeCurrY += (strikeDestY - strikeCurrY) / strikeDestSpeed;

			arm.setPosition(strikeCurrX - szunyog.getWidth() - arm_shadow.getWidth() / 16, strikeCurrY - szunyog.getHeight() - arm_shadow.getWidth() / 6);
			arm_shadow.setPosition(arm.getX() - 5, arm.getY() - 5);

			nextStrikeCounter++;
			if(nextStrikeCounter >= nextStrikeDown)
			strikeDown();
		}else{
			if(isStriking == false) {
				nextStrikeCounter++;

				if(nextStrikeCounter >= nextStrike) {
					startStrike();
				}
			}
		}


		if(isStriking){
			if(arm.getWidth() > strikeMinW) {
				arm.setSize(arm.getWidth() - strikeSpeed, arm.getHeight() - strikeSpeed / 2);
				strikingAtX += strikeSpeed / 2;
				strikingAtY += strikeSpeed / 2;
				arm.setPosition(strikingAtX - arm.getWidth() * 0.05f, strikingAtY - arm.getWidth() * 0.05f);
				arm_shadow.setPosition(arm.getX() - 5, arm.getY() - 5);
				if(arm.getWidth() < strikeMinW + 200) arm.setVisible(true);
			}else{
				finishedStriking();
			}
		}
		//getViewport().getCamera().position.x = szunyog.getX();
		//getViewport().getCamera().position.y = szunyog.getY();

		//setCameraMoveToXY(szunyog.getX() + szunyog.getWidth() / 2, szunyog.getY() + szunyog.getHeight() / 2);
		//setCameraMoveSpeed(1000f);

		//isSucking = true;

		if(dashCurrCD < dashCD) dashCurrCD++;

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
			if(HUNGER <= 0) game.setScreen(new EndScreen(game, 0, dealtDamage, ellapsedSecs, missedStrikes)); // End

			float dash_speed = 1;

			if(isDashing) {
				dashCurrDur++;

				if(dashCurrDur <= dashDuration) {
					dash_speed = dashSpeed;
				}

				if(dashCurrDur >= dashDuration) {
					isDashing = false;
					dashCurrDur = 0;
				}
			}

			float newSzX = szunyog.getX() - szunyogDirX * szunyog_speed * dash_speed;
			float newSzY = szunyog.getY() - szunyogDirY * szunyog_speed * dash_speed;
			if(newSzX >= 0 && newSzX + szunyog.getWidth() <= mapW)
				szunyog.setX(newSzX);

			if(newSzY >= 0 && newSzY + szunyog.getHeight() <= mapH)
				szunyog.setY(newSzY);

			strikeDestX = szunyog.getX();
			strikeDestY = szunyog.getY();


			getCamera().position.x = szunyog.getX() + szunyog.getWidth() / 2;
			getCamera().position.y = szunyog.getY() + szunyog.getHeight() / 2;
		}

	}

	@Override
	public void draw() {

		getBatch().setProjectionMatrix(getCamera().combined);
		getBatch().begin();
		map.draw(getBatch(), 1);
		for(int i = 0; i < suckedAreas.size(); i++) {
			suckedAreas.get(i).draw(getBatch(), 1);
		}
		getBatch().end();

		super.draw();
	}

	@Override public void init() {}
}
