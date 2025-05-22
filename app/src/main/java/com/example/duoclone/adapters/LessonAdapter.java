package com.example.duoclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessonList;
    private OnLessonClickListener listener;

    public LessonAdapter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.titleTextView.setText(lesson.getTitle());

        // Обработка клика
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLessonClick(lesson);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public void setOnLessonClickListener(OnLessonClickListener listener) {
        this.listener = listener;
    }

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.lessonTitleTextView);
        }
    }
}
