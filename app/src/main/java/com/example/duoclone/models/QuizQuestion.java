package com.example.duoclone.models;

import java.util.List;

public class QuizQuestion {
    private String id;
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;
    private int xpReward;

    // Полный конструктор
    public QuizQuestion(String id, String questionText,
                        List<String> options,
                        int correctOptionIndex,
                        int xpReward) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.xpReward = xpReward;
    }

    // Геттеры
    public String getId() { return id; }
    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public String getOption1() { return options.get(0); }
    public String getOption2() { return options.get(1); }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public int getXpReward() { return xpReward; }
}