package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReviveController extends BaseController {

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    private int score;


    @FXML
    private void handleYesButton(ActionEvent event) {
        // Deduct 10 cherries from the total count
        CherryManager.getInstance().deductCherries(10);

        // Switch to the game screen
        GameController gameController = (GameController) getMainApplication().switchScene("GameScreen.fxml");
    }


    @FXML
    private void handleNoButton(ActionEvent event) {
        LastScreenController lastScreenController = (LastScreenController) getMainApplication().switchScene("LastScreen.fxml");
        lastScreenController.setScore(ScoreManager.getInstance().getScore(), HighScore.getHighScore());
    }
}
