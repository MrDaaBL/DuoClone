package com.example.duoclone.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duoclone.R;
import com.example.duoclone.adapters.LessonAdapter;
import com.example.duoclone.models.Lesson;
import com.example.duoclone.utils.LessonLoader;
import java.util.List;

public class LessonsFragment extends Fragment {
    private RecyclerView lessonRecyclerView;
    private LessonAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        // Инициализация RecyclerView
        lessonRecyclerView = view.findViewById(R.id.lessonRecyclerView);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Загрузка уроков для казахского языка
        List<Lesson> lessons = LessonLoader.loadLessons(
                requireContext().getAssets(),
                "kk" // Код языка
        );

        // Настройка адаптера
        adapter = new LessonAdapter(lessons);
        lessonRecyclerView.setAdapter(adapter);

        return view;
    }
}