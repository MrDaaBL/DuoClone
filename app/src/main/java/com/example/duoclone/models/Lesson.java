package com.example.duoclone.models;

public class Lesson {
    private String id;
    private String title;
    private String type;
    private int totalExercises;
    private int completedExercises;
    private Object content;

    public Lesson(
            String id,
            String title,
            String type,
            int totalExercises,
            int completedExercises,
            Object content
    ) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.totalExercises = totalExercises;
        this.completedExercises = completedExercises;
        this.content = content;
    }

    // Геттеры
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public int getTotalExercises() { return totalExercises; }
    public int getCompletedExercises() { return completedExercises; }
    public Object getContent() { return content; }
}