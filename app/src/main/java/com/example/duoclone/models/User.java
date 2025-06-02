package com.example.duoclone.models;

public class User {
    private String uid;
    private String name;
    private String email;
    private int progress;
    private int xp;
    private int completedLessons;
    private String id;
    private String displayName;
    private String photoUrl;

    public User() {}

    public User(String id, String displayName, String email, String photoUrl) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public User(String uid, String displayName, String email, int i, int i1, int i2) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.progress = i;
        this.xp = i1;
    }


    // Геттеры и сеттеры...
    public int getCompletedLessons() {
        return completedLessons;
    }
    public String getUid() { return uid; }
    public String getDisplayName() { return displayName; }
    public String getPhotoUrl() { return photoUrl; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getProgress() { return progress; }
    public int getXp() { return xp; }

}