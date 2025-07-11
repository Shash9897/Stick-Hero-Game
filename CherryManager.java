package com.example.demo;

public class CherryManager {
    private static int cherryScore;

    private static CherryManager instance;

    private CherryManager() {
        // Initialize the cherry count
        cherryScore = 0;
    }

    public static CherryManager getInstance() {
        if (instance == null) {
            instance = new CherryManager();
        }
        return instance;
    }

    public int getCherryScore() {
        return cherryScore;
    }

    public void setCherryScore(int cherryScore) {
        this.cherryScore = cherryScore;
    }
    public void resetCherryScore() {
        cherryScore = 0;
    }
    public void deductCherries(int amount) {
        int currentCherryCount = getCherryScore(); // Get the current cherry count
        int newCherryCount = Math.max(0, currentCherryCount - amount); // Ensure the count doesn't go below zero
        setCherryScore(newCherryCount); // Update the cherry count
    }

}

