package com.faszallitok.nfl2.Screens.LevelSelect;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyStage;
import com.faszallitok.nfl2.MyGdxGame;

public class LevelSelectStage extends MyStage {
    public LevelSelectStage(Batch batch, MyGdxGame game) {
        super(new ExtendViewport(1024, 576, new OrthographicCamera(1024, 576)), batch, game);

    }

    @Override
    public void init() {

    }
}
