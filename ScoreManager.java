package com.example.demo;

public class ScoreManager {
    private int score;

    private static ScoreManager instance;

    private ScoreManager() {
        // Initialize the score
        score = 0;
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void resetScore() {
        score = 0;
    }
}
