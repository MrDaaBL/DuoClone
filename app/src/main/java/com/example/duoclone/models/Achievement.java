package com.example.duoclone.models;

public class Achievement {
    private String id;
    private String title;
    private String description;
    private boolean unlocked;
    private String imageUrl;

    public Achievement() {} // Пустой конструктор для Firestore

    public Achievement(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.unlocked = false;
    }


    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }
    public String getImageUrl() { return imageUrl; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}