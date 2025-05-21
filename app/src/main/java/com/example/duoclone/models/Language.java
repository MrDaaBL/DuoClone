package com.example.duoclone.models;

public class Language {
    private String name;
    private int flagResourceId;

    public Language(String name, int flagResourceId) {
        this.name = name;
        this.flagResourceId = flagResourceId;
    }

    public String getName() { return name; }
    public int getFlagResourceId() { return flagResourceId; }

    public void setName(String name) { this.name = name; }
    public void setFlagResourceId(int flagResourceId) { this.flagResourceId = flagResourceId; }
}