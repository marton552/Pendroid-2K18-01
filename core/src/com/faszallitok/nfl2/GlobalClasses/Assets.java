package com.faszallitok.nfl2.GlobalClasses;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.Model;


public class Assets {
	// https://github.com/libgdx/libgdx/wiki/Managing-your-assets
	// http://www.jacobplaster.net/using-libgdx-asset-manager
	// https://www.youtube.com/watch?v=JXThbQir2gU
	// https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java

	public static AssetManager manager;



	public static final String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";


	static final FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	static {
		fontParameter.fontFileName = "alegreyaregular.otf";
		fontParameter.fontParameters.size = 30;
		fontParameter.fontParameters.borderColor = Color.WHITE;
		fontParameter.fontParameters.borderWidth = 1;
		fontParameter.fontParameters.characters = CHARS;
		fontParameter.fontParameters.color = Color.WHITE;
	}

	public static final AssetDescriptor<BitmapFont> ALEGREYAREGULAR_FONT
			= new AssetDescriptor<BitmapFont>(fontParameter.fontFileName, BitmapFont.class, fontParameter);


	//Atlasok
	//public static final AssetDescriptor<TextureAtlas> LOADING_ATLAS = new AssetDescriptor<TextureAtlas>("atlasok/title.atlas", TextureAtlas.class);

	//Szunyog
	public static final AssetDescriptor<Texture> SZUNYOG = new AssetDescriptor<Texture>("game/szunyog.png", Texture.class);
	public static final AssetDescriptor<Texture> SZUNYOG_SUCK = new AssetDescriptor<Texture>("game/szunyog-suck.png", Texture.class);

	//Maps
	public static final AssetDescriptor<Texture> MAP = new AssetDescriptor<Texture>("map.png", Texture.class);
	public static final AssetDescriptor<Texture> ARM_MAP = new AssetDescriptor<Texture>("game/kez-map.png", Texture.class);

	//Other
	public static final AssetDescriptor<Texture> SUCKED_AREA = new AssetDescriptor<Texture>("game/sucked-area.png", Texture.class);
	public static final AssetDescriptor<Texture> SUCK_MARK = new AssetDescriptor<Texture>("game/suck-mark.png", Texture.class);

	//Menu
	public static final AssetDescriptor<Texture> MENU_BG = new AssetDescriptor<Texture>("bg.png", Texture.class);
	public static final AssetDescriptor<Texture> MENU_ICON = new AssetDescriptor<Texture>("menu-icon.png", Texture.class);

	//Controller
	public static final AssetDescriptor<Texture> JOY_FRONT = new AssetDescriptor<Texture>("joy-front.png", Texture.class);
	public static final AssetDescriptor<Texture> JOY_BACK = new AssetDescriptor<Texture>("joy-back.png", Texture.class);

	public static final AssetDescriptor<Texture> EAT = new AssetDescriptor<Texture>("game/eat.png", Texture.class);
	public static final AssetDescriptor<Texture> DASH = new AssetDescriptor<Texture>("game/dash.png", Texture.class);

	public static final AssetDescriptor<Texture> EAT_CD = new AssetDescriptor<Texture>("game/eat-cd.png", Texture.class);
	public static final AssetDescriptor<Texture> DASH_CD = new AssetDescriptor<Texture>("game/dash-cd.png", Texture.class);


    //Button
	public static final AssetDescriptor<Texture> BTN_BACK = new AssetDescriptor<Texture>("ui_textures/btn_back.png", Texture.class);
	public static final AssetDescriptor<Texture> BTN_HOVER = new AssetDescriptor<Texture>("ui_textures/btn_hover.png", Texture.class);

	public static final AssetDescriptor<Texture> WHITE_TEXTURE = new AssetDescriptor<Texture>("ui_textures/white.png", Texture.class);
	public static final AssetDescriptor<Texture> FULLWHITE_TEXTURE = new AssetDescriptor<Texture>("ui_textures/fullwhite.png", Texture.class);

	//Slider
	public static final AssetDescriptor<Texture> SLIDER_BG = new AssetDescriptor<Texture>("ui_textures/slider.png", Texture.class);
	public static final AssetDescriptor<Texture> SLIDER_KNOB = new AssetDescriptor<Texture>("ui_textures/sliderknob.png", Texture.class);
	public static final AssetDescriptor<Texture> SLIDER_KNOBH = new AssetDescriptor<Texture>("ui_textures/sliderknob2.png", Texture.class);

	//Music


	public static void prepare() {
		manager = new AssetManager();
		Texture.setAssetManager(manager);
	}

	public static void load() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		manager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

		manager.load(SZUNYOG);
		manager.load(SZUNYOG_SUCK);
		manager.load(MAP);
		manager.load(ARM_MAP);

		manager.load(SUCKED_AREA);
		manager.load(SUCK_MARK);

		manager.load(MENU_BG);
		manager.load(MENU_ICON);

		manager.load(JOY_FRONT);
		manager.load(JOY_BACK);
		manager.load(EAT);
		manager.load(DASH);
		manager.load(EAT_CD);
		manager.load(DASH_CD);


		manager.load(BTN_BACK);
		manager.load(BTN_HOVER);

		manager.load(WHITE_TEXTURE);
		manager.load(FULLWHITE_TEXTURE);

		manager.load(SLIDER_BG);
		manager.load(SLIDER_KNOB);
		manager.load(SLIDER_KNOBH);

		manager.load(ALEGREYAREGULAR_FONT);

	}

    public static void afterLoaded() {
        //manager.get(MUSIC).setLooping(true);
    }

	public static void unload() {
		manager.dispose();
	}

}
