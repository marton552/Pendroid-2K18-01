package com.faszallitok.nfl2.MyBaseClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyScreen;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.Menu.MenuScreen;


public class LoadingScreen extends MyScreen {


    public LoadingScreen(MyGdxGame game) {
        super(game);
    }
    BitmapFont bitmapFont = new BitmapFont();
    TextureAtlas atlas = new TextureAtlas("atlasok/loading.atlas");
    OneSpriteAnimatedActor loading = new OneSpriteAnimatedActor(atlas);
    {
        loading.setFps(60);
        loading.setLooping(true);
        loading.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        Assets.manager.finishLoading();
        Assets.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        loading.draw(spriteBatch, 1f);
        loading.act(delta);
        bitmapFont.draw(spriteBatch,"Betöltés: " + Assets.manager.getLoadedAssets() + "/" + (Assets.manager.getQueuedAssets()+ Assets.manager.getLoadedAssets()) + " (" + ((int)(Assets.manager.getProgress()*100f)) + "%)", 0, 0);
        spriteBatch.end();
        if (Assets.manager.update()) {
            Assets.afterLoaded();
            game.setScreen(new MenuScreen(game));
            //MusicPlayer.playMenuMusic();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void init() {
        setBackGroundColor(0f, 0f, 0f);
    }
}