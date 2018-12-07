package com.faszallitok.nfl2.Screens.About;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyButton;
import com.faszallitok.nfl2.MyBaseClasses.UI.MyLabel;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.Menu.MenuScreen;

public class AboutStage extends MyStage {
    private MyButton back;
    private MyLabel title;
    private MyLabel info;
    private MyButton infos;
    private MyButton spells;

    private MyLabel d_spell; //Dash spell
    private MyLabel eat_spell; //Kaja spell
    private OneSpriteStaticActor d_spell_image;
    private OneSpriteStaticActor eat_spell_image;

    private OneSpriteStaticActor eat_spot;

    public AboutStage(Batch batch, MyGdxGame game) {
        super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

        OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.BG));
        bg.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        addActor(bg);

        title = new MyLabel("A Játékról", game.getLabelStyle());
        title.setAlignment(Align.center);
        title.setPosition(getViewport().getWorldWidth() / 2 - title.getWidth() / 2, getViewport().getWorldHeight() - 90);
        title.setFontScale(1.6f);
        addActor(title);

        info = new MyLabel("A célod, hogy a szúnyog segítségével minél többet egyél,\nmert ha nem eszel akkor éhen hal a szúnyog\nVigyázz, mert Sanyi próbál közbe lecsapni téged!\nPróbáld meg kikerülni a többi akadályt is, mert meglepetésben lehet részed.\n\nKészítők: Fa Szállítók csapata", game.getLabelStyle());
        info.setAlignment(Align.center);
        info.setPosition(getViewport().getWorldWidth() / 2 - info.getWidth() / 2, getViewport().getWorldHeight() - 390);
        addActor(info);

        back = new MyButton("Menü", game.getButtonStyle());
        back.setPosition(getViewport().getWorldWidth() / 2 - back.getWidth() / 2, 20 );
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                getGame().setScreen(new MenuScreen(getGame()));
            }
        });
        addActor(back);

        d_spell_image = new OneSpriteStaticActor(Assets.manager.get(Assets.DASH));
        d_spell_image.setVisible(false);
        d_spell_image.setSize(d_spell_image.getWidth() / 5, d_spell_image.getHeight() / 5);
        d_spell_image.setPosition(getViewport().getWorldWidth() / 2 + 150,
                getViewport().getWorldHeight() - d_spell_image.getHeight() - 120);
        addActor(d_spell_image);
        d_spell = new MyLabel("Dash:\nEgy kis időre felgyorsul\na szunyog sebessége.\nViszont várni kell használat után,\nhogy újra használni tudd.", game.getLabelStyle());
        d_spell.setAlignment(Align.center);
        d_spell.setVisible(false);
        d_spell.setFontScale(0.8f);
        d_spell.setPosition(d_spell_image.getX() + d_spell_image.getWidth() / 2 - d_spell.getWidth() / 2,
                d_spell_image.getY() - d_spell.getHeight());
        addActor(d_spell);

        eat_spell_image = new OneSpriteStaticActor(Assets.manager.get(Assets.EAT));
        eat_spell_image.setVisible(false);
        eat_spell_image.setSize(eat_spell_image.getWidth() / 5, eat_spell_image.getHeight() / 5);
        eat_spell_image.setPosition(getViewport().getWorldWidth() / 2 - eat_spell_image.getWidth() - 150,
                                    getViewport().getWorldHeight() - eat_spell_image.getHeight() - 120);
        addActor(eat_spell_image);
        eat_spell = new MyLabel("Evés:\nA kijelölt területen tudsz enni.\nA kijelölt terület így néz ki:", game.getLabelStyle());
        eat_spell.setAlignment(Align.center);
        eat_spell.setVisible(false);
        eat_spell.setFontScale(0.8f);
        eat_spell.setPosition(eat_spell_image.getX() + eat_spell_image.getWidth() / 2 - eat_spell.getWidth() / 2,
                                eat_spell_image.getY() - eat_spell.getHeight());
        addActor(eat_spell);

        eat_spot = new OneSpriteStaticActor(Assets.manager.get(Assets.SUCK_MARK));
        eat_spot.setSize(eat_spot.getWidth() / 6, eat_spot.getHeight() / 6);
        eat_spot.setVisible(false);
        eat_spot.setPosition(eat_spell.getX() + eat_spell.getWidth() / 2 - eat_spot.getWidth() / 2,
                                eat_spell.getY() - eat_spot.getHeight() + 50);
        addActor(eat_spot);


        infos = new MyButton("Információk", game.getButtonStyle());
        infos.setPosition(40, 20);
        infos.setVisible(false);
        infos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(infos.isVisible()) {
                    spells.setVisible(true);
                    infos.setVisible(false);

                    title.setText("A Játékról");
                    d_spell.setVisible(false);
                    d_spell_image.setVisible(false);
                    eat_spell.setVisible(false);
                    eat_spell_image.setVisible(false);
                    eat_spot.setVisible(false);

                    info.setVisible(true);
                }
            }
        });
        addActor(infos);

        spells = new MyButton("Képességek", game.getButtonStyle());
        spells.setPosition(getViewport().getWorldWidth() - spells.getWidth() - 40, 20);
        spells.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(spells.isVisible()) {
                    spells.setVisible(false);
                    infos.setVisible(true);

                    title.setText("Képességek");

                    d_spell.setVisible(true);
                    d_spell_image.setVisible(true);
                    eat_spell.setVisible(true);
                    eat_spell_image.setVisible(true);
                    eat_spot.setVisible(true);

                    info.setVisible(false);
                }
            }
        });
        addActor(spells);


    }

    @Override
    public void init() {

    }
}
