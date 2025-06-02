package com.example.duoclone.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.duoclone.R;
import com.example.duoclone.adapters.LessonPagerAdapter;
import com.example.duoclone.adapters.QuizAdapter;
import com.example.duoclone.fragments.LessonsFragment;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.utils.LessonLoader;
import java.util.List;

public class LessonActivity extends AppCompatActivity implements QuizAdapter.OnAnswerListener {
    private ViewPager2 questionsPager;
    private View resultLayout;
    private TextView tvResult;
    private TextView tvGrade;
    private List<QuizQuestion> questions;
    private QuizAdapter adapter;
    private int currentLevel = 1; // Текущий уровень сложности
    private int correctAnswers = 0; // Счетчик правильных ответов
    private ViewPager viewPager;
    private LessonPagerAdapter adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initViews();
        loadQuestions();

        // Инициализация элементов
        Button btnBack = findViewById(R.id.btn_back);
        Button btnNextLevel = findViewById(R.id.btn_next_level);
        viewPager = findViewById(R.id.lesson_pager);

        // Настройка ViewPager
        adapter = new QuizAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Обработчики кнопок
        btnBack.setOnClickListener(v -> finish());
        btnNextLevel.setOnClickListener(v -> loadNextLevel());

        nextLevelButton.setOnClickListener(v -> {
            currentLevel++;
            loadLevelContent();
            nextLevelButton.setVisibility(View.GONE);
        });

        // Загрузка контента для текущего уровня
        loadLevelContent();
    }

    private void loadLevelContent() {
        // Здесь загружаем контент для currentLevel
        adapter.setLevel(currentLevel);
        adapter.notifyDataSetChanged();
    }

    // Вызывается из LessonFragment при правильном ответе
    public void onCorrectAnswer() {
        correctAnswers++;
        checkLevelCompletion();
    }


    private void initViews() {
        questionsPager = findViewById(R.id.questions_pager);
        resultLayout = findViewById(R.id.result_layout);
        tvResult = findViewById(R.id.tv_result);
        tvGrade = findViewById(R.id.tv_grade);

    }

    private void loadQuestions() {
        LessonLoader lessonLoader = new LessonLoader();
        questions = lessonLoader.loadExtendedLessons("kk");

        adapter = new QuizAdapter(this, questions, this);
        questionsPager.setAdapter(adapter);
        questionsPager.setUserInputEnabled(false);
        resultLayout.setVisibility(View.GONE);
    }
    private void loadLessonFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lesson_container, LessonsFragment.newInstance(currentLevel));
        transaction.commit();
    }
    @Override
    public void onAnswerSelected(boolean isCorrect) {
        if (isCorrect) {
            correctAnswers++;
        }
    }

    @Override
    public void moveToNextQuestion() {
        int nextItem = questionsPager.getCurrentItem() + 1;
        if (nextItem < adapter.getItemCount()) {
            questionsPager.setCurrentItem(nextItem, true);
        } else {
            showResults();
        }
    }

    private void showResults() {
        runOnUiThread(() -> {
            tvResult.setText(getResultText());
            tvGrade.setText(getGradeText());
            resultLayout.setVisibility(View.VISIBLE);
        });
    }

    private String getResultText() {
        return String.format(getString(R.string.result_format),
                correctAnswers,
                questions.size());
    }

    private String getGradeText() {
        double percentage = (double) correctAnswers / questions.size();
        if (percentage >= 0.8) return getString(R.string.grade_5);
        if (percentage >= 0.6) return getString(R.string.grade_4);
        return getString(R.string.grade_3);
    }

    public void restartLesson(View view) {
        correctAnswers = 0;
        adapter.resetQuiz();
        questionsPager.setCurrentItem(0, false);
        resultLayout.setVisibility(View.GONE);
    }
    private void checkLevelCompletion() {
        if (correctAnswers >= 5) {
            SharedPreferences prefs = getSharedPreferences("progress", MODE_PRIVATE);
            prefs.edit().putBoolean("level_" + currentLevel, true).apply();
            findViewById(R.id.btn_next_level).setVisibility(View.VISIBLE);
        }
    }

    private void loadNextLevel() {
        currentLevel++;
        loadLessonFragment();
        findViewById(R.id.btn_next_level).setVisibility(View.GONE);
        correctAnswers = 0;
    }
}