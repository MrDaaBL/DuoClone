package com.example.duoclone.models;

public class Lesson {
    private final String id;
    private final String title;
    private final String type;
    private final int totalExercises;
    private final int completedExercises;
    private final boolean isCompleted;

    public Lesson(String id, String title, String type, int totalExercises, int completedExercises, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.totalExercises = totalExercises;
        this.completedExercises = completedExercises;
        this.isCompleted = isCompleted;
    }

    // Геттеры
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public int getTotalExercises() { return totalExercises; }
    public int getCompletedExercises() { return completedExercises; }
    public boolean isCompleted() { return isCompleted; }
}