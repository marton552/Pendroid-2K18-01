package com.faszallitok.nfl2.Screens.Game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyLabel;
import com.faszallitok.nfl2.MyGdxGame;

public class GameHUD extends MyStage{

    private OneSpriteStaticActor menu;
    public OneSpriteStaticActor joy;
    public float centerX;
    public float centerY;

    private GameScreen screen;

    Pixmap hungerPixmap;
    OneSpriteStaticActor hungerActor;

    Pixmap suckPixmap;
    OneSpriteStaticActor suckActor;
    MyLabel suckLabel;

    OneSpriteStaticActor dash;
    OneSpriteStaticActor eat;


    public GameHUD(Batch batch, MyGdxGame game, final GameScreen screen) {
        super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);
        this.screen = screen;

        menu = new OneSpriteStaticActor(Assets.manager.get(Assets.MENU_ICON));
        menu.setSize(30, 30);
        menu.setPosition(getViewport().getWorldWidth() - 40, getViewport().getWorldHeight() - 40);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.isPaused = true;
                screen.gameStage.bzz.pause();
            }
        });


        OneSpriteStaticActor menu_bg = new OneSpriteStaticActor(Assets.manager.get(Assets.DARK));
        menu_bg.setSize(menu.getWidth() + 2, menu.getHeight() + 2);
        menu_bg.setPosition(menu.getX() - 1, menu.getY() - 1);
        addActor(menu_bg);
        addActor(menu);

        OneSpriteStaticActor joy_back = new OneSpriteStaticActor(Assets.manager.get(Assets.JOY_BACK));
        joy_back.setSize(joy_back.getWidth() / 3, joy_back.getHeight() / 3);
        joy_back.setX(getViewport().getWorldWidth() - joy_back.getWidth() - 40);
        joy_back.setY(40);
        addActor(joy_back);

        joy = new OneSpriteStaticActor(Assets.manager.get(Assets.JOY_FRONT));
        joy.setSize(joy.getWidth() / 4, joy.getHeight() / 4);
        centerX = joy_back.getX() + joy_back.getWidth() / 2 - joy.getWidth() / 2;
        centerY = joy_back.getY() + joy_back.getHeight() / 2 - joy.getHeight() / 2;
        joy.setPosition(centerX ,centerY);

        joy.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);

                float tx = joy.getX() + x - joy.getWidth() / 2;
                float ty = joy.getY() + y - joy.getHeight() / 2;

                if(tx < centerX - 90 || tx > centerX + 90){
                    joy.setX(joy.getX());
                }else{
                    joy.setX(tx);
                }

                if(ty < centerY - 100 || ty > centerY + 100){
                    joy.setY(joy.getY());
                }else{
                    joy.setY(ty);
                }


                OneSpriteStaticActor szunyog = screen.gameStage.szunyog;

                //System.out.println("fok: "+getAngle(centerX, centerY, tx, ty));
                szunyog.setRotation(screen.gameStage.getAngle(centerX, centerY, tx, ty) - 90);

                screen.gameStage.szunyogDirX = (float) Math.cos(Math.toRadians(szunyog.getRotation() - 90));
                screen.gameStage.szunyogDirY = (float) Math.sin(Math.toRadians(szunyog.getRotation() - 90));

            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                joy.setPosition(centerX, centerY);
                screen.gameStage.szunyogDirX = 0;
                screen.gameStage.szunyogDirY = 0;
            }
        });
        addActor(joy);


        dash = new OneSpriteStaticActor(Assets.manager.get(Assets.DASH));
        dash.setSize(dash.getWidth() / 5, dash.getHeight() / 5);
        dash.setPosition(20, 20);
        dash.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.gameStage.dash();
            }
        });
        addActor(dash);

        eat = new OneSpriteStaticActor(Assets.manager.get(Assets.EAT));
        eat.setSize(eat.getWidth() / 5, eat.getHeight() / 5);
        eat.setPosition(dash.getX() + dash.getWidth() + 10, dash.getY() + dash.getHeight());
        eat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.gameStage.suckBlood();
            }
        });
        addActor(eat);



        hungerPixmap = new Pixmap(200, 20, Pixmap.Format.RGBA8888);
        hungerActor = new OneSpriteStaticActor(new Texture(hungerPixmap));
        updateHungerBar(screen.gameStage.HUNGER);

        hungerActor.setPosition(10,getViewport().getWorldHeight() - hungerActor.getHeight() - 10);
        addActor(hungerActor);

        MyLabel hungerLabel = new MyLabel("Éhség", game.getLabelStyle());
        hungerLabel.setFontScale(0.5f);
        hungerLabel.setPosition(hungerActor.getX() + hungerActor.getWidth() / 2 - hungerLabel.getWidth() / 4,
                hungerActor.getY() - hungerActor.getHeight() / 2 - hungerLabel.getHeight() / 10 + 2);
        addActor(hungerLabel);

        suckPixmap = new Pixmap(400, 10, Pixmap.Format.RGBA8888);
        suckActor = new OneSpriteStaticActor(new Texture(suckPixmap));
        updateSuckBar(screen.gameStage.suckTime - screen.gameStage.currSuckTime);

        suckActor.setPosition(getViewport().getWorldWidth() / 2 - suckActor.getWidth() / 2,getViewport().getWorldHeight() - 100);
        suckActor.setVisible(false);
        addActor(suckActor);

        suckLabel = new MyLabel("Evés...", game.getLabelStyle());
        suckLabel.setFontScale(0.7f);
        suckLabel.setPosition(getViewport().getWorldWidth() / 2 - (suckLabel.getWidth() * 0.5f) / 2,
                              suckActor.getY() + 10);
        suckLabel.setVisible(false);
        addActor(suckLabel);

    }

    void updateSuckBar(float currSuck) {
        suckPixmap.setColor(0, 0, 0, 0);
        suckPixmap.fill();
        suckPixmap.setColor(1, 1, 1, 0.7f);
        suckPixmap.fillRectangle(0, 0, (int)(currSuck * 4), 10);
        suckPixmap.setColor(1, 1, 1, 1);
        suckPixmap.drawRectangle(0, 0, 400, 10);

        suckActor.setTexture(new Texture(suckPixmap));
    }

    void updateHungerBar(float hunger) {
        hungerPixmap.setColor(0, 0, 0, 0);
        hungerPixmap.fill();
        hungerPixmap.setColor(1, 0.76f, 0, 0.7f);
        hungerPixmap.fillRectangle(0, 0, (int)(hunger * 2), 20);
        hungerPixmap.setColor(1, 1, 1, 1);
        hungerPixmap.drawRectangle(0, 0, 200, 20);

        hungerActor.setTexture(new Texture(hungerPixmap));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        updateHungerBar(screen.gameStage.HUNGER);

        if(screen.gameStage.isSucking) {
            suckLabel.setVisible(true);
            suckActor.setVisible(true);
            updateSuckBar(screen.gameStage.suckTime - screen.gameStage.currSuckTime);
            eat.setTexture(Assets.manager.get(Assets.EAT_CD));
        }
        else {
            suckLabel.setVisible(false);
            suckActor.setVisible(false);
            eat.setTexture(Assets.manager.get(Assets.EAT));
        }

        if(screen.gameStage.dashCurrCD < screen.gameStage.dashCD) {
            dash.setTexture(Assets.manager.get(Assets.DASH_CD));
        }else{
            dash.setTexture(Assets.manager.get(Assets.DASH));
        }

        /*screen.gameStage.szunyogDirX = screen.gameStage.mapW - screen.gameStage.szunyog.getX();
        screen.gameStage.szunyogDirY = screen.gameStage.mapH - screen.gameStage.szunyog.getY();

        screen.gameStage.szunyogDirX *= Math.cos(Math.toRadians(screen.gameStage.szunyog.getRotation() + 90));
        screen.gameStage.szunyogDirY *= Math.sin(Math.toRadians(screen.gameStage.szunyog.getRotation() + 90));
        */

    }

    @Override
    public void init() {

    }
}
