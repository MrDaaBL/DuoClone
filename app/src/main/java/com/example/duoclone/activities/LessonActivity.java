package com.example.duoclone.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.adapters.QuizAdapter;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.utils.SoundManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    private RecyclerView quizRecyclerView;
    private QuizAdapter quizAdapter;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        soundManager = new SoundManager(this);

        // Инициализация RecyclerView
        quizRecyclerView = findViewById(R.id.quizRecyclerView);
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Загрузка вопросов
        List<QuizQuestion> questions = loadQuestions();
        quizAdapter = new QuizAdapter(questions, isCorrect -> {
            // Обработка правильного/неправильного ответа
            if (isCorrect) {
                soundManager.playCorrectSound();
            } else {
                soundManager.playWrongSound();
            }
        }, soundManager);

        quizRecyclerView.setAdapter(quizAdapter);
    }

    private List<QuizQuestion> loadQuestions() {
        List<QuizQuestion> questions = new ArrayList<>();

        // Пример вопроса (замените реальными данными)
        questions.add(new QuizQuestion(
                "q1",
                "Как переводится 'Hello'?",
                Arrays.asList("Привет", "Пока"),
                0, // Индекс правильного ответа
                "multiple_choice",
                10
        ));

        return questions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }
}