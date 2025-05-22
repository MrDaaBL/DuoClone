package com.example.duoclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duoclone.R;
import com.example.duoclone.activities.LessonActivity;
import com.example.duoclone.adapters.LessonAdapter;
import com.example.duoclone.models.Lesson;
import com.example.duoclone.utils.LessonLoader;

import java.util.List;

public class LessonsFragment extends Fragment {
    private RecyclerView lessonRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);
        lessonRecyclerView = view.findViewById(R.id.lessonRecyclerView);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Lesson> lessons = loadLessons();
        LessonAdapter adapter = new LessonAdapter(lessons);

        // Установка слушателя кликов
        adapter.setOnLessonClickListener(lesson -> {
            Intent intent = new Intent(getContext(), LessonActivity.class);
            intent.putExtra("lesson_id", lesson.getId());
            startActivity(intent);
        });

        lessonRecyclerView.setAdapter(adapter);
        return view;
    }

    private List<Lesson> loadLessons() {
        // Загрузка уроков из JSON или Firestore
        return LessonLoader.loadLessons(requireContext().getAssets(), "kk");
    }
}