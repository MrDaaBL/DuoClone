package com.example.duoclone.models;

public class UserProgress {
    private int completedLessons;
    private int totalXP;
    private String userId;

    // Обязательный пустой конструктор для Firestore
    public UserProgress() {}

    public UserProgress(String userId, int completedLessons, int totalXP) {
        this.userId = userId;
        this.completedLessons = completedLessons;
        this.totalXP = totalXP;
    }

    // Геттеры
    public int getCompletedLessons() { return completedLessons; }
    public int getTotalXP() { return totalXP; }
    public String getUserId() { return userId; }
}
