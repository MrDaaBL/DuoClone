package com.example.duoclone.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.activities.LessonActivity;
import com.example.duoclone.models.Lesson;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private final List<Lesson> lessons;

    public LessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.lessonTitle.setText(lesson.getTitle());
        holder.lessonProgress.setText(
                lesson.getCompletedExercises() + "/" + lesson.getTotalExercises() + " заданий выполнено"
        );

        // Обработка клика на элемент
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LessonActivity.class);
            intent.putExtra("lesson_title", lesson.getTitle()); // Передача данных
            v.getContext().startActivity(intent);
            ((Activity) v.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView lessonIcon;
        public final TextView lessonTitle;
        public final TextView lessonProgress;

        public ViewHolder(View view) {
            super(view);
            lessonIcon = view.findViewById(R.id.lessonIcon);
            lessonTitle = view.findViewById(R.id.lessonTitle);
            lessonProgress = view.findViewById(R.id.lessonProgress);
        }
    }
}