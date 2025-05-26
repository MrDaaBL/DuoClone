package com.example.duoclone.utils;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.duoclone.R;

public class SoundManager {
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private MediaPlayer backgroundMusic;

    public SoundManager(Context context) {
        correctSound = MediaPlayer.create(context, R.raw.correct_sound);
        wrongSound = MediaPlayer.create(context, R.raw.wrong_sound);
        backgroundMusic = MediaPlayer.create(context, R.raw.background_music);
        backgroundMusic.setLooping(true);
    }

    public void playCorrectSound() {
        correctSound.start();
    }

    public void playWrongSound() {
        wrongSound.start();
    }

    public void startBackgroundMusic() {
        backgroundMusic.start();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.pause();
    }
}