package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LastScreenController extends BaseController {
    @FXML
    private Button homeButton;

    @FXML
    private void handleHomeButtonClick() {
        ScoreManager.getInstance().resetScore();
        getMainApplication().switchScene("MainScreen.fxml");
    }


    @FXML
    private Button restartButton;

    @FXML
    private void handleRestartButtonClick() {
        ScoreManager.getInstance().resetScore();
        getMainApplication().switchScene("GameScreen.fxml");
    }
    @FXML
    private Text scoreText; // Add this line
    @FXML
    private Text highScoreText;
    // New method to set the score
    public void setScore(int score, int highScore) {
        // Update UI components with the final score and high score
        scoreText.setText("Score: " + score);
        highScoreText.setText("High Score: " + highScore);
    }
}
