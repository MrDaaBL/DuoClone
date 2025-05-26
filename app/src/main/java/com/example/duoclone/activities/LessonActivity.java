package com.example.duoclone.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.adapters.QuizAdapter;
import com.example.duoclone.models.Lesson;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.utils.LessonLoader;
import java.util.List;

public class LessonActivity extends AppCompatActivity implements QuizAdapter.OnAnswerListener {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerView); // Изменено с quizRecyclerView

        // Загрузка вопросов
        String language = getIntent().getStringExtra("language");
        LessonLoader lessonLoader = new LessonLoader();
        List<Lesson> lessons = lessonLoader.loadLessons(language);

        if (!lessons.isEmpty() && lessons.get(0).getContent() instanceof QuizQuestion) {
            QuizQuestion question = (QuizQuestion) lessons.get(0).getContent();
            List<QuizQuestion> questions = List.of(question);

            QuizAdapter adapter = new QuizAdapter(questions, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onAnswer(boolean isCorrect, int xpEarned) {
        // Обработка ответа пользователя
    }
}