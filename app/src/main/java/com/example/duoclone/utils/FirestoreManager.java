package com.example.duoclone.utils;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.example.duoclone.models.Achievement;
import com.example.duoclone.models.User;
import com.example.duoclone.models.VocabularyCard;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public void saveLessonResult(String userId, String lessonId, int score) {
        DocumentReference docRef = db.collection("users")
                .document(userId)
                .collection("lessons")
                .document(lessonId);

        Map<String, Object> data = new HashMap<>();
        data.put("score", score);
        data.put("timestamp", FieldValue.serverTimestamp());

        docRef.set(data)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Урок сохранен"))
                .addOnFailureListener(e -> Log.w(TAG, "Ошибка", e));
    }


    public void saveVocabularyProgress(String userId, VocabularyCard card) {
        db.collection("users")
                .document(userId)
                .collection("vocabulary")
                .document(card.getWord())
                .set(card);
    }

    public interface FirestoreCallback {
        void onSuccess(User progress);
        void onFailure(Exception e);
        void onVocabularyLoaded(List<VocabularyCard> cards); // Добавлен новый метод
    }

    public static void saveUserData(GoogleSignInAccount account) {
        User user = new User(
                account.getId(),
                account.getDisplayName(),
                account.getEmail(),
                account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : ""
        );

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(account.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User data saved"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error saving user", e));
    }
    public void loadVocabularyProgress(String userId, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .collection("vocabulary")
                .get()
                .addOnSuccessListener(query -> {
                    List<VocabularyCard> cards = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : query) {
                        cards.add(doc.toObject(VocabularyCard.class));
                    }
                    callback.onVocabularyLoaded(cards);
                });
    }
}