package com.faszallitok.nfl2;

import com.badlogic.gdx.audio.Music;
import com.faszallitok.nfl2.GlobalClasses.Assets;

public class MusicPlayer {

    public static boolean isMenuMusicPlaying = false;
    public static Music menuMusic;

    public static void startMenuMusic() {
        if(isMenuMusicPlaying == false) {
            menuMusic = Assets.manager.get(Assets.MENU_MUSIC);
            menuMusic.play();
            menuMusic.setLooping(true);
            menuMusic.setVolume(0.4f);
            isMenuMusicPlaying = true;
        }
    }

    public static void stopMenuMusic() {
        if(isMenuMusicPlaying) {
            isMenuMusicPlaying = false;
            menuMusic.stop();
        }
    }


}
