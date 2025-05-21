package com.example.duoclone.models;

public class User {
    private String uid;
    private String name;
    private String email;
    private int progress;
    private int xp;
    private int completedLessons;

    public User() {}

    public User(String uid, String name, String email, int progress, int xp, int completedLessons) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.progress = progress;
        this.xp = xp;
        this.completedLessons = completedLessons;
    }

    // Геттеры и сеттеры...
    public int getCompletedLessons() {
        return completedLessons;
    }
    public String getUid() { return uid; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getProgress() { return progress; }
    public int getXp() { return xp; }

}