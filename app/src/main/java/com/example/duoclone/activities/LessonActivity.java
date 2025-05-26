package com.example.duoclone.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.duoclone.R;
import com.example.duoclone.adapters.QuizAdapter;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.utils.LessonLoader;
import java.util.List;

public class LessonActivity extends AppCompatActivity implements QuizAdapter.OnAnswerListener {
    private ViewPager2 questionsPager;
    private View resultLayout;
    private TextView tvResult;
    private TextView tvGrade;
    private int correctAnswers = 0;
    private List<QuizQuestion> questions;
    private QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_pager);

        initViews();
        loadQuestions();
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
}