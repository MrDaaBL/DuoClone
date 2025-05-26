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

public class NewLessonAdapter extends RecyclerView.Adapter<NewLessonAdapter.ViewHolder> {
    private final List<Lesson> lessons;

    public NewLessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.title.setText(lesson.getTitle());
        holder.icon.setImageResource(
                lesson.getType().equals("quiz") ?
                        R.drawable.ic_quiz :
                        R.drawable.ic_vocabulary
        );
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView icon;

        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.lesson_title);
            icon = view.findViewById(R.id.lesson_icon);
        }
    }
}