package com.example.duoclone.models;

public class Lesson {
    private String title;
    private String word;
    private String translation;

    public Lesson(String title, String word, String translation) {
        this.title = title;
        this.word = word;
        this.translation = translation;
    }

    public String getTitle() { return title; }
    public String getWord() { return word; }
    public String getTranslation() { return translation; }
}