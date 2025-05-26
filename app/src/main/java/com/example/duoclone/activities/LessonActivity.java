package com.example.duoclone.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import com.example.duoclone.R;
import com.example.duoclone.adapters.LessonAdapter;
import com.example.duoclone.models.Lesson;
import com.example.duoclone.models.VocabularyCard;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    private RecyclerView lessonRecyclerView;
    private LessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Кнопка "Назад"
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // Инициализация RecyclerView
        lessonRecyclerView = findViewById(R.id.lessonRecyclerView);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Загрузка данных
        List<Lesson> lessons = loadLessons();
        adapter = new LessonAdapter(lessons);
        lessonRecyclerView.setAdapter(adapter);
    }

    private List<Lesson> loadLessons() {
        List<Lesson> lessons = new ArrayList<>();

        // Пример урока с корректными параметрами
        lessons.add(new Lesson(
                "vocab_1",          // id
                "Сәлем",            // title
                "vocabulary",       // type
                5,                  // totalExercises
                0,                  // completedExercises
                new VocabularyCard("Сәлем", "Hello", List.of("Hi", "Bonjour")) // content
        ));

        return lessons;
    }
}