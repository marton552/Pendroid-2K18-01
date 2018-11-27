package com.faszallitok.nfl2.MyBaseClasses.UI;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Game.InitableInterface;

import java.awt.Button;

public class MyButton extends TextButton implements InitableInterface {
    public MyButton(String text, TextButtonStyle style) {
        super(text, style);
        setSize(300, 50);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Sound sound = Assets.manager.get(Assets.BTN_SOUND);
                //sound.play();
            }
        });
    }

    @Override
    public void init() {

    }
}
