package com.example.duoclone.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.duoclone.R;

public class MusicService extends Service {
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.background_music);
        player.setLooping(true);
        player.setVolume(0.3f, 0.3f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!player.isPlaying()) {
            player.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
        super.onDestroy();
    }
}