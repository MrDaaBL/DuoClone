package com.example.duoclone.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.activities.LessonActivity;
import com.example.duoclone.adapters.LessonAdapter;
import com.example.duoclone.models.Lesson;
import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {
    private static final String PREFS_NAME = "lesson_progress";
    private static final int ANSWERS_TO_UNLOCK = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.lessonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Lesson> lessons = loadLessonsWithProgress();
        LessonAdapter adapter = new LessonAdapter(lessons);

        adapter.setOnLessonClickListener(lesson -> {
            if (isLessonUnlocked(lesson.getId())) {
                startLesson(lesson);
            } else {
                showLockedLessonDialog(lesson);
            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<Lesson> loadLessonsWithProgress() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        List<Lesson> lessons = new ArrayList<>();

        // Базовые уроки (всегда разблокированы)
        lessons.add(new Lesson("1", "Тексттік тест", "text_quiz", 0, 0, null));
        lessons.add(new Lesson("2", "Суреттер тесті", "image_quiz", 0, 0, null));

        // Дополнительные уровни (зависит от прогресса)
        for (int i = 3; i <= 5; i++) {
            boolean isUnlocked = prefs.getBoolean("lesson_" + i + "_unlocked", false);
            lessons.add(new Lesson(
                    String.valueOf(i),
                    "Көмекші деңгей " + (i-2),
                    "advanced_" + i,
                    isUnlocked ? 0 : 1, // 0 - разблокирован, 1 - заблокирован
                    0,
                    null
            ));
        }
        return lessons;
    }

    private boolean isLessonUnlocked(String lessonId) {
        if (lessonId.equals("1") || lessonId.equals("2")) {
            return true;
        }
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean("lesson_" + lessonId + "_unlocked", false);
    }

    private void startLesson(Lesson lesson) {
        Intent intent = new Intent(getActivity(), LessonActivity.class);
        intent.putExtra("lesson_id", lesson.getId());
        intent.putExtra("lesson_type", lesson.getType());
        startActivity(intent);
    }

    private void showLockedLessonDialog(Lesson lesson) {
        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Сабақ құлыпталған")
                .setMessage("Бұл сабақты ашу үшін алдыңғы деңгейден кемінде " +
                        ANSWERS_TO_UNLOCK + " дұрыс жауап беруіңіз керек!")
                .setPositiveButton("OK", null)
                .show();
    }

    public void updateLessonProgress(String lessonId, int correctAnswers) {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        if (correctAnswers >= ANSWERS_TO_UNLOCK) {
            int nextLesson = Integer.parseInt(lessonId) + 1;
            prefs.edit().putBoolean("lesson_" + nextLesson + "_unlocked", true).apply();
        }
    }
}