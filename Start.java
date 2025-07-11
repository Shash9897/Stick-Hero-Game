package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    private Stage primaryStage;
    private StackPane mainContainer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StickHero");
        mainContainer = new StackPane();
        switchScene("MainScreen.fxml");
        Scene scene = new Scene(mainContainer, 275, 515);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public BaseController switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);

            BaseController controller = loader.getController();
            controller.setMainApplication(this);

            return controller; // Return the loaded controller

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if an exception occurs
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
