package com.example.demo;

public class HighScore {
    private static int highScore = 0;

    private static HighScore instance;
    private HighScore() {
        // Private constructor to prevent instantiation
    }

    public static HighScore getInstance() {
        if (instance == null) {
            instance = new HighScore();
        }
        return instance;
    }
    public static int getHighScore() {
        return highScore;
    }

    public static void updateHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }
}
