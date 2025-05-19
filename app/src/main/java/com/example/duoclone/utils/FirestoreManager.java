package com.example.duoclone.utils;

import com.example.duoclone.models.Achievement;
import com.example.duoclone.models.UserProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FirestoreManager {
    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_ACHIEVEMENTS = "achievements";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Загрузка прогресса
    public void loadProgress(String userId, FirestoreCallback callback) {
        db.collection(COLLECTION_USERS)
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserProgress progress = documentSnapshot.toObject(UserProgress.class);
                        callback.onSuccess(progress);
                    }
                });
    }

    // Получение достижений
    public void getUserAchievements(String userId, FirestoreAchievementsCallback callback) {
        db.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_ACHIEVEMENTS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Achievement> achievements = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Achievement achievement = doc.toObject(Achievement.class);
                        achievements.add(achievement); // Исправлено: добавление объекта, а не класса
                    }
                    callback.onSuccess(achievements);
                });
    }

    // Интерфейсы обратных вызовов
    public interface FirestoreCallback {
        void onSuccess(UserProgress progress);
    }

    public interface FirestoreAchievementsCallback {
        void onSuccess(List<Achievement> achievements);
    }
}