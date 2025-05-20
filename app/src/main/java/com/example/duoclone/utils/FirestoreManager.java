package com.example.duoclone.utils;

import android.util.Log;
import com.example.duoclone.models.UserProgress;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Интерфейс для обратного вызова
    public interface FirestoreCallback {
        void onSuccess(UserProgress progress);
    }

    // Сохранение прогресса
    public void saveProgress(String userId, int completedLessons, int totalXP) {
        UserProgress progress = new UserProgress(userId, completedLessons, totalXP);
        db.collection("users")
                .document(userId)
                .set(progress)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Данные сохранены"))
                .addOnFailureListener(e -> Log.e("Firestore", "Ошибка: " + e.getMessage()));
    }

    // Загрузка прогресса
    public void loadProgress(String userId, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserProgress progress = documentSnapshot.toObject(UserProgress.class);
                        callback.onSuccess(progress);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Ошибка загрузки: " + e.getMessage()));
    }
}