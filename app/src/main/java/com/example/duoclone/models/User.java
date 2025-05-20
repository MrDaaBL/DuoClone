package com.example.duoclone.models;

public class User {
    private String uid;
    private String name;
    private String email;
    private int progress;
    private int xp;

    // Обязательный пустой конструктор
    public User() {}

    public User(String uid, String name, String email, int progress, int xp) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.progress = progress;
        this.xp = xp;
    }

    // Геттеры и сеттеры...
    public String getUid() { return uid; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getProgress() { return progress; }
    public int getXp() { return xp; }

}