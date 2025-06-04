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

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music); // Файл в res/raw/
        mediaPlayer.setLooping(true); // Зацикливание музыки
        Log.d("MusicService", "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            mediaPlayer.start();
            isRunning = true;
            Log.d("MusicService", "Music started");
        }
        return START_STICKY; // Сервис перезапустится, если будет убит системой
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

    // Методы для управления музыкой из других классов
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isRunning = false;
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isRunning = true;
        }
    }
}