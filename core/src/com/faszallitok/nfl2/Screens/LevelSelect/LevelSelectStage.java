package com.faszallitok.nfl2.Screens.LevelSelect;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyButton;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyLabel;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.Game.GameScreen;
import com.faszallitok.nfl2.Screens.Menu.MenuScreen;

public class LevelSelectStage extends MyStage {
    public LevelSelectStage(Batch batch, final MyGdxGame game) {
        super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

        OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.BG));
        bg.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        addActor(bg);

        OneSpriteStaticActor bg_man = new OneSpriteStaticActor(Assets.manager.get(Assets.DARK));
        bg_man.setSize(500, getViewport().getWorldHeight() - 20);
        bg_man.setPosition(10, getViewport().getWorldHeight() - bg_man.getHeight() - 10);
        addActor(bg_man);

        OneSpriteStaticActor man = new OneSpriteStaticActor(Assets.manager.get(Assets.MAN));
        man.setPosition(bg_man.getX(), bg_man.getY());
        addActor(man);

        OneSpriteStaticActor r_cont = new OneSpriteStaticActor(Assets.manager.get(Assets.DARK));
        r_cont.setSize(getViewport().getWorldWidth() - bg_man.getWidth() - 20 -10,
                getViewport().getWorldHeight() - 20);
        r_cont.setPosition(getViewport().getWorldWidth() - r_cont.getWidth() - 10, getViewport().getWorldHeight() - r_cont.getHeight() - 10);
        addActor(r_cont);

        MyLabel name = new MyLabel("Ő itt Sanyi", game.getLabelStyle());
        name.setPosition(bg_man.getX() + bg_man.getWidth() / 2 - name.getWidth() / 2, bg_man.getY() + bg_man.getHeight() - name.getHeight());
        addActor(name);

        MyLabel title = new MyLabel("Válassz egy testrészt:", game.getLabelStyle());
        title.setPosition(r_cont.getX() + r_cont.getWidth() / 2 - title.getWidth() / 2, r_cont.getY() + r_cont.getHeight() - title.getHeight());
        addActor(title);

        String[] test_reszek = { "Kéz", "Láb", "Has" };
        for (int i = 0; i < test_reszek.length; i++) {
            MyButton btn = new MyButton(test_reszek[i], game.getButtonStyle());
            btn.setPosition(r_cont.getX() + r_cont.getWidth() / 2 - btn.getWidth() / 2, r_cont.getY() + r_cont.getHeight() - 120 - (i * (btn.getHeight()+20)));
            final int finalI = i;
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    game.setScreen(new GameScreen(game, finalI));
                }
            });
            addActor(btn);
        }

        MyButton back = new MyButton("Menü", game.getButtonStyle());
        back.setPosition(r_cont.getX() + r_cont.getWidth() / 2 - back.getWidth() / 2, r_cont.getY() + 10);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));
            }
        });
        addActor(back);


    }

    @Override
    public void init() {

    }
}
