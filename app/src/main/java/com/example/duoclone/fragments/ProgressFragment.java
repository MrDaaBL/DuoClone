package com.example.duoclone.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.duoclone.R;
import com.example.duoclone.activities.LoginActivity;
import com.example.duoclone.models.User;
import com.example.duoclone.models.VocabularyCard;
import com.example.duoclone.utils.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProgressFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView xpText;
    private FirestoreManager firestoreManager;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (currentUser != null) {
            loadProgressData();
        } else {
            showLoginPrompt();
        }
    }

    private void loadProgressData() {
        String userId = currentUser.getUid();

        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Обработка данных прогресса
                        updateUI(documentSnapshot);
                    } else {
                        showNoDataMessage();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ProgressFragment", "Error loading progress", e);
                    showError();
                });
    }

    private void showLoginPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Авторизация қажет")
                .setMessage("Прогресті көру үшін тіркелгіге кіріңіз")
                .setPositiveButton("Кіру", (dialog, which) -> {
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                })
                .setNegativeButton("Бас тарту", null)
                .show();
    }

    private void updateUI(DocumentSnapshot document) {
        // Обновление UI с полученными данными
    }

    private void showNoDataMessage() {
        Toast.makeText(requireContext(), "Прогресс деректері табылған жоқ", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(requireContext(), "Деректерді жүктеу қатесі", Toast.LENGTH_SHORT).show();
    }
}