package com.example.duoclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.Achievement;
import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {
    private final List<Achievement> achievements;

    public AchievementsAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        holder.title.setText(achievement.getTitle());
        holder.description.setText(achievement.getDescription());
        holder.image.setImageResource(achievement.isUnlocked() ? R.drawable.ic_badge_unlocked : R.drawable.ic_badge_locked);
    }

    @Override
    public int getItemCount() { return achievements.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView title;
        public final TextView description;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.achievement_image);
            title = view.findViewById(R.id.achievement_title);
            description = view.findViewById(R.id.achievement_description);
        }
    }
}