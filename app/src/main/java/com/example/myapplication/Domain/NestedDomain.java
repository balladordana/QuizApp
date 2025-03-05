package com.example.myapplication.Domain;

public class NestedDomain {
    String theme;
    int score;

    public NestedDomain(String theme, int score) {
        this.theme = theme;
        this.score = score;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
