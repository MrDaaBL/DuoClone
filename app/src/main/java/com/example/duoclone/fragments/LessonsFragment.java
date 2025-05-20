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
import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LessonAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        // Настройка RecyclerView
        recyclerView = view.findViewById(R.id.lessonsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Загрузка данных
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("Негіздері 1", 10, 5, false));
        lessons.add(new Lesson("Сәлемдесу", 8, 3, true));

        // Инициализация адаптера
        adapter = new LessonAdapter(lessons);
        recyclerView.setAdapter(adapter);

        return view;
    }
}