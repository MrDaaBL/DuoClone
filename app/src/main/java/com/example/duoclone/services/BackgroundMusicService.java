package com.example.duoclone.services;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.duoclone.R;


public class BackgroundMusicService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean isRunning = false;
    private float volume = 0.5f;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music); // Файл в res/raw/
        mediaPlayer.setLooping(true); // Зацикливание музыки
        Log.d("MusicService", "Service created");
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("action")) {
            String action = intent.getStringExtra("action");
            if ("set_volume".equals(action)) {
                volume = intent.getFloatExtra("volume", 0.5f);
                mediaPlayer.setVolume(volume, volume);
            }
        }
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            isRunning = false;
            Log.d("MusicService", "Music stopped");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}