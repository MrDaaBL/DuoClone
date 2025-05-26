package com.example.duoclone.models;

public class ImageQuestion {
    private String id;
    private int imageResId;
    private String option1;
    private String option2;
    private String correctAnswer;
    private int xpReward;

    public ImageQuestion(String id, int imageResId, String option1,
                         String option2, String correctAnswer, int xpReward) {
        this.id = id;
        this.imageResId = imageResId;
        this.option1 = option1;
        this.option2 = option2;
        this.correctAnswer = correctAnswer;
        this.xpReward = xpReward;
    }

    // Геттеры
    public int getImageResId() { return imageResId; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getCorrectAnswer() { return correctAnswer; }
    public int getXpReward() { return xpReward; }
}