package com.faszallitok.nfl2.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.faszallitok.nfl2.MyBaseClasses.Scene2D.MyScreen;
import com.faszallitok.nfl2.MyGdxGame;
import com.faszallitok.nfl2.Screens.Menu.HUD;

public class GameScreen extends MyScreen {
    public GameStage gameStage;
    public HUD hud;
    GameHUD gameHud;
    public boolean isPaused = false;

    public GameScreen(MyGdxGame game) {
        super(game);
        gameStage = new GameStage(spriteBatch, game, this);
        hud = new HUD(spriteBatch, game, this);
        gameHud = new GameHUD(spriteBatch, game, this);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameStage);
        inputMultiplexer.addProcessor(gameHud);
        inputMultiplexer.addProcessor(hud);

        Gdx.input.setInputProcessor(inputMultiplexer);
        this.setBackGroundColor(1, 1, 1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(!isPaused)
            gameStage.act(delta);
        gameStage.draw();

        gameHud.act(delta);
        gameHud.draw();

        if(isPaused) {
            hud.act();
            hud.draw();
        }

    }

    @Override
    public void init() {

    }
}
