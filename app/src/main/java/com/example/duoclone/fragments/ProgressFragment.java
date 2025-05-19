package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.duoclone.R;
import com.example.duoclone.models.UserProgress;
import com.example.duoclone.utils.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProgressFragment extends Fragment {
    private FirestoreManager firestoreManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreManager = new FirestoreManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        TextView xpText = view.findViewById(R.id.xp_text);

        // Загрузка данных
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestoreManager.loadProgress(userId, progress -> {
            progressBar.setProgress(progress.getCompletedLessons());
            xpText.setText("XP: " + progress.getTotalXP());
        });

        return view;
    }
}