package com.faszallitok.nfl2.Screens.LevelSelect;

import com.badlogic.gdx.Gdx;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyScreen;
import com.faszallitok.nfl2.MyGdxGame;

public class LevelSelectScreen extends MyScreen {
    LevelSelectStage levelSelectStage;

    public LevelSelectScreen(MyGdxGame game) {
        super(game);
        levelSelectStage = new LevelSelectStage(spriteBatch, game);
        Gdx.input.setInputProcessor(levelSelectStage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        levelSelectStage.act(delta);
        levelSelectStage.draw();
    }


    @Override
    public void init() {

    }
}
