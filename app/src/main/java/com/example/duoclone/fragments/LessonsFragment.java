package com.example.duoclone.fragments;

import android.content.Intent;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.lessonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("1", "Тексттік тест", "quiz", 0, 0, null));
        lessons.add(new Lesson("2", "Суреттер тесті", "image_quiz", 0, 0, null));

        LessonAdapter adapter = new LessonAdapter(lessons);
        adapter.setOnLessonClickListener(lesson -> {
            Intent intent = new Intent(getActivity(), LessonActivity.class);
            intent.putExtra("lesson_type", lesson.getType());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}