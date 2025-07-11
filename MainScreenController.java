package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainScreenController extends BaseController {

    @FXML
    private Button continueButton;

    public void handleNewGameButtonClick() {
        // Reset the score before switching to the game screen
        ScoreManager.getInstance().resetScore();

        getMainApplication().switchScene("GameScreen.fxml");
    }

    public void handleContinueButtonClick() {
        getMainApplication().switchScene("GameScreen.fxml");
    }

    public void handleExitButtonClick() {
        Platform.exit();
    }

    @FXML
    private void initialize() {
        // Disable the continue button if the score is 0
        if (ScoreManager.getInstance().getScore() == 0) {
            continueButton.setDisable(true);
        }
    }
}
