package com.example.duoclone.utils;

import android.util.Log;
import com.example.duoclone.models.Achievement;
import com.example.duoclone.models.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirestoreManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Интерфейс для загрузки прогресса
    public interface FirestoreCallback {
        void onSuccess(User progress);
        void onFailure(Exception e);
    }

    // Интерфейс для загрузки достижений
    public interface FirestoreAchievementsCallback {
        void onSuccess(List<Achievement> achievements);
        void onFailure(Exception e);
    }

    // Загрузка прогресса пользователя
    public void loadProgress(String userId, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        User progress = document.toObject(User.class);
                        callback.onSuccess(progress);
                    } else {
                        callback.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Ошибка загрузки прогресса", e);
                    callback.onFailure(e);
                });
    }

    // Загрузка достижений пользователя
    public void getUserAchievements(String userId, FirestoreAchievementsCallback callback) {
        db.collection("users")
                .document(userId)
                .collection("achievements")
                .get()
                .addOnSuccessListener(query -> {
                    List<Achievement> achievements = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : query) {
                        Achievement achievement = doc.toObject(Achievement.class);
                        achievements.add(achievement);
                    }
                    callback.onSuccess(achievements);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Ошибка загрузки достижений", e);
                    callback.onFailure(e);
                });
    }
}