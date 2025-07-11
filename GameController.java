package com.example.demo;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class GameController extends BaseController {

    @FXML
    private Box stickBox;
    @FXML
    private ImageView heroImageView;
    @FXML
    private ImageView heroImageView1;
    @FXML
    private Box randomContainer;
    @FXML
    private Button pause;
    @FXML
    private Box initialContainer;
    @FXML
    private ImageView cherryImageView;
    @FXML
    private Box Bonus;
    @FXML
    private Text bonusText;
    private boolean stickincrease = false;

    private double stickHeight = 0;

    private Timeline timeline;
    private double hero1StartX;
    private double hero2StartX;
    private Timeline heroMoveTimeline;
    private boolean cherryCounted ;
    private double heroX;

    @FXML
    private Text scoreText;

    private int score = 0;
    @FXML
    private Text cherryText;

    private int cherryScore = 0;

    private Timeline visibilityCheckTimeline;

    @FXML
    private void initialize() {



        heroImageView1.setVisible(false);
        randomizeInitialBlock();
        handleCherryLogic();

        timeline = new Timeline(new KeyFrame(Duration.millis(60), e -> increaseStickHeight()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        visibilityCheckTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> checkHeroVisibility()));
        visibilityCheckTimeline.setCycleCount(Timeline.INDEFINITE);
        visibilityCheckTimeline.play();


        stickBox.setOnMousePressed(this::handleMousePressed);
        stickBox.setOnMouseReleased(this::handleMouseReleased);

        heroImageView.setOnKeyPressed(this::handleKeyPressed);
        heroImageView.setOnKeyReleased(this::handleKeyReleased);


//        here the use of singleton design pattern has been depicted

        heroImageView.requestFocus();
        cherryScore = CherryManager.getInstance().getCherryScore();
        score = ScoreManager.getInstance().getScore();

        cherryText.setText(String.valueOf(cherryScore));
        scoreText.setText(String.valueOf(score));
    }
    @FXML
    private void handlePauseButtonClick(ActionEvent event) {

        PauseController pauseScreenController = (PauseController) getMainApplication().switchScene("PauseScreen.fxml");
        pauseScreenController.setScore(score);
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        if (!stickincrease) {
            stickincrease = true;
            
            timeline.play();
        }
    }


    @FXML
    private void handleMouseReleased(MouseEvent event) {
        if (stickincrease) {
            stickincrease = false;

            timeline.stop();

            Animation fallingAnimation = createFallingAnimation();
            fallingAnimation.play();
        }
    }
    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.D) {
            heroImageView.setVisible(false);
            heroImageView1.setVisible(true);
            heroImageView1.setScaleX(-1.0);
        }
    }



    @FXML
    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.D) {
            heroImageView.setVisible(true);
            heroImageView1.setVisible(false);

        }
    }

    private void checkHeroVisibility() {
        boolean isHero1Visible = heroImageView1.isVisible();


        if (isHero1Visible && isTouchingPillar()) {

            PauseTransition pause1 = new PauseTransition(Duration.millis(120));
            pause1.play();
            pause1.setOnFinished(resetevent -> GameEnd());

        }
    }
    private boolean isTouchingPillar() {
        double heroEndX = heroImageView1.getTranslateX() + heroImageView1.getBoundsInLocal().getWidth();
        double pillarStartX = randomContainer.getTranslateX() - (randomContainer.getWidth() / 2);
        double pillarEndX = randomContainer.getTranslateX() + (randomContainer.getWidth() / 2);

        return heroEndX >= pillarStartX && heroEndX <= pillarEndX;
    }


    private void randomizeInitialBlock() {
        double randomWidth = Math.random() * 60 + 10; // Random width between 60 and 10
        double randomX = Math.random() * (112 + 65) - 65;


        setBlockProperties(randomContainer, randomWidth, randomX);
        randomBonusBlock();
    }
    private void setBlockProperties(Box block, double width, double translateX) {

        block.setWidth(width);
        block.setHeight(195);
        block.setTranslateX(translateX);
        block.setTranslateY(162);
    }
    private void randomBonusBlock(){
        double BonusX= randomContainer.getTranslateX();
        setBonusproperties(Bonus, BonusX );
    }
    private void setBonusproperties( Box Bonus, double translateX){
        Bonus.setTranslateX(translateX);
        Bonus.setTranslateY(67);
    }

    private void handleCherryLogic() {
        double startingX = -105;
        double lastContainerX = randomContainer.getTranslateX() - randomContainer.getWidth() / 2 - 5;
        double randomX = startingX + Math.random() * (lastContainerX - startingX);

        setCherryProperties(cherryImageView, randomX);
    }

    private void setCherryProperties(ImageView imageView, double translateX) {
        imageView.setTranslateX(translateX);
        imageView.setTranslateY(80);
    }

    private Animation createFallingAnimation() {
        double fallHeight = stickHeight / 2.0;
        heroX=heroImageView.getTranslateX();

        PauseTransition pause = new PauseTransition(Duration.millis(500));


        Timeline stickFallTimeline = new Timeline(
                new KeyFrame(Duration.millis(80), e -> {
                    cherryCounted = false;

                    stickBox.setRotate(stickBox.getRotate() + 90);


                    stickBox.setTranslateY(stickBox.getTranslateY() + fallHeight);
                    stickBox.setTranslateX(stickBox.getTranslateX() + fallHeight);

                })
        );

        heroMoveTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {

                    hero1StartX = heroImageView.getTranslateX();

                    hero2StartX = heroImageView1.getTranslateX();
                }),
                new KeyFrame(Duration.millis(8), e -> {

                    double hero1EndX = hero1StartX + 1;

                    double hero2EndX = hero2StartX + 1;

                    heroImageView.setTranslateX(hero1EndX);
                    heroImageView1.setTranslateX(hero2EndX);

                    if (heroImageView1.isVisible() &&
                            (hero2EndX + heroImageView1.getFitWidth() >= (cherryImageView.getX() - cherryImageView.getFitWidth()) &&
                                    hero2EndX - heroImageView1.getFitWidth() <= (cherryImageView.getX()) + cherryImageView.getFitWidth())) {

//                        System.out.println(hero2EndX +heroImageView1.getFitWidth());
//                        System.out.println(cherryImageView.getX() - cherryImageView.getFitWidth());
                        cherryCounted = true;
                        cherryImageView.setVisible(false);
                    }
                    if (heroImageView1.isVisible() && (hero2EndX + heroImageView1.getFitWidth()/2) >= (randomContainer.getTranslateX() - randomContainer.getWidth() / 2)) {
                        pause.play();
                        pause.setOnFinished(resetevent -> GameEnd());
                    }




                    if (hero1EndX >= heroX + stickHeight +15) {
                        stopHeroMoveTimeline();
                        if (cherryCounted) {
                            cherryScore += 1;
                            cherryText.setText(String.valueOf(cherryScore));
                        }


                        double stickEndX = stickBox.getTranslateX() + stickBox.getHeight() / 2;
                        double randomBlockStartX = randomContainer.getTranslateX() + randomContainer.getWidth() / 2;
                        double randomBlockEndX = randomContainer.getTranslateX() - randomContainer.getWidth() / 2;
                        double BonusStartX = Bonus.getTranslateX()+ Bonus.getWidth()/2;
                        double BonusEndX = Bonus.getTranslateX() - Bonus.getWidth()/2;

                        if (stickEndX <= randomBlockStartX && stickEndX >= randomBlockEndX) {
                            if(stickEndX <= BonusStartX && stickEndX >= BonusEndX) {
                                score += 2;
                                showBonusText();

                            }else{

                                score += 1;

                            }
                            scoreText.setText(String.valueOf(score));
                            pause.play();
                            pause.setOnFinished(resetevent -> resetScene());
                        }else{
                            pause.play();
                            pause.setOnFinished(resetevent -> GameEnd());
                        }


                    }

                })
        );



        heroMoveTimeline.setCycleCount(Timeline.INDEFINITE);


        stickFallTimeline.setOnFinished(event -> heroMoveTimeline.play());

        return stickFallTimeline;
    }

    private void stopHeroMoveTimeline() {
        heroMoveTimeline.stop();

    }

    private void resetScene() {

        stickHeight = 0;
        stickBox.setHeight(stickHeight);
        stickBox.setRotate(0);

        TranslateTransition resetHeroTransition = new TranslateTransition(Duration.millis(500), heroImageView);
        resetHeroTransition.setToX(-125);
        resetHeroTransition.setToY(50);


        TranslateTransition resetRandomBlockTransition = new TranslateTransition(Duration.millis(500), randomContainer);
        resetRandomBlockTransition.setToX(-150 + initialContainer.getWidth()/2);
        Bonus.setVisible(false);
        initialContainer.setVisible(false);

        stickBox.setTranslateX(-114);
        stickBox.setTranslateY(65);
        stickBox.setDepth(50);

        ParallelTransition parallelTransition = new ParallelTransition(resetHeroTransition, resetRandomBlockTransition);


        parallelTransition.setOnFinished(event -> {

            randomizeInitialBlock();
            handleCherryLogic();
            hideBonusText();
            initialContainer.setVisible(true);
            Bonus.setVisible(true);
            cherryImageView.setVisible(true);
        });


        parallelTransition.play();
        heroImageView1.setTranslateX(-126);
        heroImageView1.setTranslateY(80);

//        here the use of singleton design pattern has been shown

        ScoreManager.getInstance().setScore(score);
        CherryManager.getInstance().setCherryScore(cherryScore);

    }
    @FXML
    private void GameEnd() {
        HighScore.updateHighScore(score);

        if (cherryScore >= 10) {
            ReviveController reviveController = (ReviveController) getMainApplication().switchScene("ReviveScreen.fxml");

        } else {

            Timeline heroDownTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        heroImageView.setTranslateY(heroImageView.getTranslateY() + 2); // Adjust the downward speed
                    })
            );
            heroDownTimeline.setCycleCount(100);

            heroDownTimeline.setOnFinished(event -> {

                LastScreenController lastScreenController = (LastScreenController) getMainApplication().switchScene("LastScreen.fxml");
                lastScreenController.setScore(score, HighScore.getHighScore());
            });

            heroDownTimeline.play();
        }
    }



    private void increaseStickHeight() {
        double increment = 10.0;
        stickHeight += increment;
        stickBox.setHeight(stickHeight);
        stickBox.setTranslateY(stickBox.getTranslateY() - increment/2);
    }
    private void showBonusText() {
        bonusText.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), bonusText);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void hideBonusText() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), bonusText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> bonusText.setVisible(false));
        fadeOut.play();
    }

}
