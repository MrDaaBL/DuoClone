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

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private final List<Lesson> lessons;
    private OnLessonClickListener listener;

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

        // Использование строкового ресурса
        String progress = holder.itemView.getContext().getString(
                R.string.lesson_progress,
                lesson.getCompletedExercises(),
                lesson.getTotalExercises()
        );
        holder.lessonProgress.setText(progress);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLessonClick(lesson);
            }
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonIcon = itemView.findViewById(R.id.lessonIcon);
            lessonTitle = itemView.findViewById(R.id.lessonTitle);
            lessonProgress = itemView.findViewById(R.id.lessonProgress);
        }
    }

    public void setOnLessonClickListener(OnLessonClickListener listener) {
        this.listener = listener;
    }

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
    }
}