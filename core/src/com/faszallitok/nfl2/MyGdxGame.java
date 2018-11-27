package com.faszallitok.nfl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.faszallitok.nfl2.GlobalClasses.Assets;
import com.faszallitok.nfl2.MyBaseClasses.Game.MyGame;
import com.faszallitok.nfl2.MyBaseClasses.LoadingScreen;

public class MyGdxGame extends MyGame {
	SpriteBatch batch;

	public Label.LabelStyle getLabelStyle() {
		Label.LabelStyle style;
		style = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
		style.font = Assets.manager.get(Assets.ALEGREYAREGULAR_FONT);
		style.fontColor = Color.WHITE;
		return style;
	}

	public Slider.SliderStyle getSliderStyle(){
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knobDown = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.SLIDER_KNOBH),0,0,20,20));
        sliderStyle.knobOver = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.SLIDER_KNOBH),0,0,20,20));
		sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.SLIDER_KNOB),0,0,20,20));
		sliderStyle.background = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.SLIDER_BG),0,0,400,20));
		return sliderStyle;
	}

	public TextButton.TextButtonStyle getButtonStyle(){
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.font = Assets.manager.get(Assets.ALEGREYAREGULAR_FONT);
		buttonStyle.fontColor = new Color(1, 1, 1, 1);
		buttonStyle.overFontColor = new Color(0, 0, 0, 1);
		buttonStyle.downFontColor = new Color(0, 0, 0, 1);

		buttonStyle.up = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.BTN_BACK)));
		buttonStyle.down = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.BTN_HOVER)));
		buttonStyle.over = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.BTN_HOVER)));

		return buttonStyle;
	}

	@Override
	public void create() {
		super.create();
		Assets.prepare();
		setScreen(new LoadingScreen(this));
	}


}

