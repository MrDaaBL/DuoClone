package com.example.duoclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
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
    private RecyclerView lessonRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        // Инициализация RecyclerView
        lessonRecyclerView = view.findViewById(R.id.lessonRecyclerView);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Загрузка уроков
        List<Lesson> lessons = loadLessons();
        LessonAdapter adapter = new LessonAdapter(lessons);

        // Установка обработчика кликов
        adapter.setOnLessonClickListener(lesson -> {
            Intent intent = new Intent(getContext(), LessonActivity.class);
            intent.putExtra("lesson_id", lesson.getId());
            startActivity(intent);
        });

        lessonRecyclerView.setAdapter(adapter);
        return view;
    }

    private List<Lesson> loadLessons() {
        // Здесь должна быть ваша логика загрузки уроков
        // Пример:
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("1", "Приветствия", "vocabulary", 5, 0, null));
        lessons.add(new Lesson("2", "Числа", "vocabulary", 7, 0, null));
        return lessons;
    }
    private void setupLessonClickListener() {
        adapter.setOnLessonClickListener(lesson -> {
            Intent intent = new Intent(getActivity(), LessonActivity.class);

            // Передаем ID урока и его тип
            intent.putExtra("LESSON_ID", lesson.getId());
            intent.putExtra("LESSON_TYPE", lesson.getType());

            startActivity(intent);
        });
    }
}