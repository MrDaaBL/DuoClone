package com.example.duoclone.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duoclone.R;

public class LessonActivity extends AppCompatActivity {
    private MediaPlayer correctSound;
    private MediaPlayer errorSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Инициализация звуков (исправленный синтаксис)
        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        errorSound = MediaPlayer.create(this, R.raw.error_sound);

        // Исправленный ID кнопки (должно быть R.id.btn_answer)
        findViewById(R.id.btn_answer).setOnClickListener(v -> {
            if (isAnswerCorrect()) {
                playSound(correctSound);
            } else {
                playSound(errorSound);
            }
        });
    }

    private boolean isAnswerCorrect() {
        // Логика проверки ответа
        return true;
    }

    private void playSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Освобождение ресурсов
        if (correctSound != null) {
            correctSound.release();
        }
        if (errorSound != null) {
            errorSound.release();
        }
    }
}