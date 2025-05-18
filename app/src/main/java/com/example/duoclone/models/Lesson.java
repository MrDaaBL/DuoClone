package com.example.duoclone.models;

public class Lesson {
    private final String title;
    private final int totalExercises;
    private final int completedExercises;
    private final boolean isCompleted;

    public Lesson(String title, int totalExercises, int completedExercises, boolean isCompleted) {
        this.title = title;
        this.totalExercises = totalExercises;
        this.completedExercises = completedExercises;
        this.isCompleted = isCompleted;
    }

    // Геттеры
    public String getTitle() { return title; }
    public int getTotalExercises() { return totalExercises; }
    public int getCompletedExercises() { return completedExercises; }
    public boolean isCompleted() { return isCompleted; }
}
