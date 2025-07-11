package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PauseController extends BaseController {

    @FXML
    private Button restartButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button playButton;

    @FXML
    private Text scoreText;

    private int score; // Add this line to store the score

    @FXML
    private void handleRestartButtonClick() {

        ScoreManager.getInstance().resetScore();
        getMainApplication().switchScene("GameScreen.fxml");

    }

    @FXML
    private void handleHomeButtonClick() {
        getMainApplication().switchScene("MainScreen.fxml");
    }

    @FXML
    private void handlePlayButtonClick() {
        // Switch back to the game screen
        GameController gameController = (GameController) getMainApplication().switchScene("GameScreen.fxml");
    }

    // You may need a method to update the score text
    public void setScore(int score) {
        // Update UI components with the final score and high score
        this.score = score;
        scoreText.setText("Score: " + score);
    }
}
