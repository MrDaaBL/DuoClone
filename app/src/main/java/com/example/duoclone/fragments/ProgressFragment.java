package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.duoclone.R;
import com.example.duoclone.utils.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProgressFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView xpText;
    private FirestoreManager firestoreManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        xpText = view.findViewById(R.id.xp_text);
        firestoreManager = new FirestoreManager();

        // Загрузка данных прогресса
        loadProgressData();

        return view;
    }

    private void loadProgressData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestoreManager.loadProgress(userId, progress -> {
            if (progress != null) {
                progressBar.setProgress(progress.getCompletedLessons());
                xpText.setText("XP: " + progress.getXp());
            } else {
                xpText.setText("Данные не найдены");
            }
        });
    }
}