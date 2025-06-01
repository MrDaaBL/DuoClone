package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duoclone.R;
import com.example.duoclone.models.Achievement;
import com.example.duoclone.adapters.AchievementAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView, lessonTextView, levelTextView;
    private RecyclerView achievementsRecyclerView;
    private AchievementAdapter achievementAdapter;
    private List<Achievement> achievementList;

    private FirebaseUser user;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameTextView = view.findViewById(R.id.text_username);
        lessonTextView = view.findViewById(R.id.text_lessons_completed);
        levelTextView = view.findViewById(R.id.text_level);
        achievementsRecyclerView = view.findViewById(R.id.recycler_achievements);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        achievementList = new ArrayList<>();
        achievementAdapter = new AchievementAdapter(achievementList);
        achievementsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        achievementsRecyclerView.setAdapter(achievementAdapter);

        if (user != null) {
            loadUserProfile();
            loadAchievements();
        }

        return view;
    }

    private void loadUserProfile() {
        db.collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String displayName = documentSnapshot.getString("displayName");
                        Long lessonProgress = documentSnapshot.getLong("lessonProgress");
                        Long level = documentSnapshot.getLong("level");

                        usernameTextView.setText("Аты: " + (displayName != null ? displayName : "Анықталмаған"));
                        lessonTextView.setText("Сабақтар: " + (lessonProgress != null ? lessonProgress : 0));
                        levelTextView.setText("Деңгей: " + (level != null ? level : 1));
                    }
                });
    }

    private void loadAchievements() {
        CollectionReference achievementsRef = db.collection("users")
                .document(user.getUid())
                .collection("achievements");

        achievementsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            achievementList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                Achievement achievement = doc.toObject(Achievement.class);
                achievementList.add(achievement);
            }
            achievementAdapter.notifyDataSetChanged();
        });
    }
}
