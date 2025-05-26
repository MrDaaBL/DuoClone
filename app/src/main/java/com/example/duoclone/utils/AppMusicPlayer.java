package com.example.duoclone.utils;

import android.app.Application;
import android.media.MediaPlayer;
import android.content.Context;
import com.example.duoclone.R;

public class AppMusicPlayer extends Application {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;
    private static float volume = 0.5f;

    public static void initialize(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(volume, volume);
        }
    }

    public static void startMusic() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static void setVolume(float newVolume) {
        volume = newVolume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    public static void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }
}