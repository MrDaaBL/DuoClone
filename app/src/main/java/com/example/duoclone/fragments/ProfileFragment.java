package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.adapters.AchievementsAdapter;
import com.example.duoclone.models.Achievement;
import com.example.duoclone.utils.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public class ProfileFragment extends Fragment {
    private RecyclerView achievementsRecyclerView;
    private TextView userNameText;
    private FirestoreManager firestoreManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        achievementsRecyclerView = view.findViewById(R.id.achievements_recycler_view);
        userNameText = view.findViewById(R.id.user_name);
        firestoreManager = new FirestoreManager();

        // Настройка RecyclerView
        achievementsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Загрузка данных
        loadProfileData();

        return view;
    }

    private void loadProfileData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userNameText.setText(user.getDisplayName());
            firestoreManager.getUserAchievements(user.getUid(), achievements -> {
                AchievementsAdapter adapter = new AchievementsAdapter(achievements);
                achievementsRecyclerView.setAdapter(adapter);
            });
        }
    }
}