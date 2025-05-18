package com.example.duoclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.Lesson;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private final List<Lesson> lessons;

    public LessonsAdapter(List<Lesson> lessons) {
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
        if (lesson.isCompleted()) {
            holder.completionStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView lessonIcon;
        public final TextView lessonTitle;
        public final TextView lessonProgress;
        public final ImageView completionStatus;

        public ViewHolder(View view) {
            super(view);
            lessonIcon = view.findViewById(R.id.lessonIcon);
            lessonTitle = view.findViewById(R.id.lessonTitle);
            lessonProgress = view.findViewById(R.id.lessonProgress);
            completionStatus = view.findViewById(R.id.completionStatus);
        }
    }
}