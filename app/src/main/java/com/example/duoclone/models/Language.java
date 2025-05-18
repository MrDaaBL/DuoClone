package com.example.duoclone.models;

public class Language {
    private String name;
    private int flagResourceId;

    public Language(String name, int flagResourceId) {
        this.name = name;
        this.flagResourceId = flagResourceId;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public int getFlagResourceId() {
        return flagResourceId;
    }

    // Сеттеры (если потребуется изменение данных)
    public void setName(String name) {
        this.name = name;
    }

    public void setFlagResourceId(int flagResourceId) {
        this.flagResourceId = flagResourceId;
    }
}