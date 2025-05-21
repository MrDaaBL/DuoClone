package com.example.duoclone.models;

import java.util.List;

public class VocabularyCard {
    private String word;
    private String translation;
    private List<String> options;
    private String correctAnswer;

    // Конструкторы
    public VocabularyCard() {}

    public VocabularyCard(String word, String translation, List<String> options) {
        this.word = word;
        this.translation = translation;
        this.options = options;
        this.correctAnswer = translation;
    }

    // Геттеры
    public String getWord() { return word; }
    public String getTranslation() { return translation; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}