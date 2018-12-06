package com.faszallitok.nfl2.Screens.End;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyButton;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyLabel;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.Menu.MenuScreen;

public class EndStage extends MyStage {
    public EndStage(Batch batch, final MyGdxGame game, int death_type, int dealt_damage, int ellapsed_secs, int missed_strikes) {
        super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

        OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.BG));
        bg.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        addActor(bg);

        OneSpriteStaticActor dark = new OneSpriteStaticActor(Assets.manager.get(Assets.DARK));
        dark.setSize(getViewport().getWorldWidth() - 400, getViewport().getWorldHeight());
        dark.setPosition(getViewport().getWorldWidth() / 2 - dark.getWidth() / 2, 0);
        addActor(dark);

        String typeTxt = "Éhen Haltál";
        if(death_type == 1) typeTxt = "Lecsaptak";

        MyLabel title = new MyLabel(typeTxt, game.getLabelStyle());
        title.getStyle().fontColor = Color.YELLOW;
        title.setPosition(getViewport().getWorldWidth() / 2 - title.getWidth() / 2, getViewport().getWorldHeight() - title.getHeight() - 30);
        title.setAlignment(Align.center);
        title.setFontScale(2f);
        addActor(title);

        MyLabel txt = new MyLabel("Okozott sérülések: "+dealt_damage+" db\nTúlélt idő: "+ellapsed_secs+" mp\nKikerült leütések: "+missed_strikes+" db", game.getLabelStyle());
        txt.setFontScale(0.8f);
        txt.setAlignment(Align.center);
        txt.setPosition(getViewport().getWorldWidth() / 2 - txt.getWidth() / 2, getViewport().getWorldHeight() / 2 - txt.getHeight() / 2 - 20);
        txt.setColor(Color.YELLOW);
        addActor(txt);

        MyButton back = new MyButton("Menü", game.getButtonStyle());
        back.setPosition(getViewport().getWorldWidth() / 2 - back.getWidth() / 2, 10);
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
