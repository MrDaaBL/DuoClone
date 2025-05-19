package com.example.duoclone.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duoclone.R;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Получение данных из Intent
        String lessonTitle = getIntent().getStringExtra("lesson_title");
        TextView titleView = findViewById(R.id.lesson_title);
        titleView.setText(lessonTitle != null ? lessonTitle : "Урок");
    }
}