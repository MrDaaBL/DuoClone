package com.example.duoclone.models;

import java.util.List;

public class VocabularyCard {
    private String word;
    private String translation;
    private List<String> options;

    public VocabularyCard(String word, String translation, List<String> options) {
        this.word = word;
        this.translation = translation;
        this.options = options;
    }

    public String getWord() { return word; }
    public String getTranslation() { return translation; }
    public List<String> getOptions() { return options; }
}