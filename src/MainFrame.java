import Objects.Egg;
import Objects.Wolf;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainFrame extends Application {

    ImageView imageOfWolfLeft;
    ImageView imageOfWolfRight;
    ImageView imageOfHandsUpRight;
    ImageView imageOfHandsDownRight;
    ImageView imageOfHandsUpLeft;
    ImageView imageOfHandsDownLeft;
    ImageView leftWood;
    ImageView leftDownWood;
    ImageView rightWood;
    ImageView rightDownWood;
    ImageView rightUpChicken;
    ImageView rightDownChicken;
    ImageView leftUpChicken;
    ImageView leftDownChicken;
    static ImageView crackedEggLeft;
    static ImageView crackedEggRight;

    Thread eggsFalling;
    Egg currentEgg;
    static final Random random = new Random();
    Label pointsCounter = new Label(" POINTS: 0");
    Label failsLeft = new Label("FAILS LEFT: " + 4);
    Button exit = new Button("EXIT");

    Group group = new Group();

    ArrayList<ImageView> listOfImagesOfHands = new ArrayList<>();

    int currentStateHorizontal = Wolf.INSTANCE.RIGHT;
    int currentStateVertical = Wolf.INSTANCE.UP;
    boolean gameRunning;

    @Override
    public void start(Stage stage) throws Exception {
        GlobalStates.INSTANCE.pointsCounter = 0;
        GlobalStates.INSTANCE.counterOfCrackedEggs = 0;
        settingImages();
        gameRunning = true;
        changingPosition(Wolf.UP,Wolf.RIGHT);
        exit.setLayoutX(1100);
        exit.setLayoutY(50);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                settingHighScore();
            }
        });
        group.getChildren().add(imageOfWolfRight);
        group.getChildren().add(imageOfWolfLeft);
        group.getChildren().add(imageOfHandsUpRight);
        group.getChildren().add(imageOfHandsDownRight);
        group.getChildren().add(imageOfHandsDownLeft);
        group.getChildren().add(imageOfHandsUpLeft);
        group.getChildren().add(leftWood);
        group.getChildren().add(leftDownWood);
        group.getChildren().add(rightWood);
        group.getChildren().add(rightDownWood);
        group.getChildren().add(crackedEggLeft);
        group.getChildren().add(crackedEggRight);
        group.getChildren().add(exit);
        Scene scene = new Scene(group,1200,700);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){
                    case W:
                        Wolf.INSTANCE.setState(Wolf.INSTANCE.UP, currentStateHorizontal);
                        changingPosition(Wolf.UP, currentStateHorizontal);
                        break;
                    case S:
                        Wolf.INSTANCE.setState(Wolf.INSTANCE.DOWN, currentStateHorizontal);
                        changingPosition(Wolf.DOWN, currentStateHorizontal);
                        currentStateVertical = Wolf.DOWN;
                        break;
                    case A:
                        Wolf.INSTANCE.setState(currentStateVertical, Wolf.INSTANCE.LEFT);
                        changingPosition(currentStateVertical, Wolf.LEFT);
                        currentStateHorizontal = Wolf.LEFT;
                        break;
                    case D:
                        Wolf.INSTANCE.setState(currentStateVertical, Wolf.INSTANCE.RIGHT);
                        changingPosition(currentStateVertical, Wolf.RIGHT);
                        currentStateHorizontal = Wolf.RIGHT;
                        break;
                }
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MenuFrame menuFrame = new MenuFrame();
                            menuFrame.start(new Stage());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                gameRunning = false;
            }
        });
        pointsCounter.setLayoutX(400);
        pointsCounter.setLayoutY(50);
        failsLeft.setLayoutX(600);
        failsLeft.setLayoutY(50);
        pointsCounter.setFont(new Font("Roboto",30));
        failsLeft.setFont(new Font("Roboto", 30));
        pointsCounter.setText(Integer.toString(GlobalStates.INSTANCE.pointsCounter));
        pointsCounter.setText("POINTS: 0");
        group.getChildren().add(pointsCounter);
        group.getChildren().add(failsLeft);
        scene.setFill(GlobalStates.backgroundColor);
        Task<Void> fallingEggs = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (gameRunning) {
                    if (isCancelled()) {
                        break;
                    }
                    int max = 100;
                    int randomVerticalState = random.nextInt(max);
                    int randomHorizontalState = random.nextInt(max);
                    if (randomHorizontalState % 2 == 0) {
                        randomHorizontalState = 2;
                    } else
                        randomHorizontalState = 1;
                    if (randomVerticalState % 2 == 0)
                        randomVerticalState = 2;
                    else
                        randomVerticalState = 1;
                    EggFalling eggFalling = new EggFalling(randomVerticalState, randomHorizontalState);
                    currentEgg = eggFalling.egg;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            group.getChildren().add(currentEgg);
                        }
                    });
                    try {
                        Thread.sleep(GlobalStates.timeOfEggRolling + 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pointsCounter.setText("POINTS: " + GlobalStates.INSTANCE.pointsCounter);
                            failsLeft.setText("FAILS LEFT: " + Math.abs(GlobalStates.INSTANCE.counterOfCrackedEggs - 4));
                            group.getChildren().remove(currentEgg);
                        }
                    });
                    if(GlobalStates.INSTANCE.pointsCounter > 10){
                        GlobalStates.setTimeOfEggRolling(2000);
                    }
                    else if(GlobalStates.INSTANCE.pointsCounter > 30){
                        GlobalStates.setTimeOfEggRolling(1000);
                    }
                    else if(GlobalStates.INSTANCE.pointsCounter > 60){
                        GlobalStates.setTimeOfEggRolling(600);
                    }
                    if (GlobalStates.INSTANCE.counterOfCrackedEggs >= 4) {
                        System.out.println("END");
                        gameRunning = false;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                stage.close();
                                settingHighScore();
                            }
                        });
                    }
                }
                return null;
            }

        };
        stage.setScene(scene);
        stage.show();
        eggsFalling = new Thread(fallingEggs);
        eggsFalling.start();
    }

    private void settingHighScore(){
        HighScoreFrame highScoreFrame = new HighScoreFrame();
        try {
            highScoreFrame.start(new Stage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void settingImages(){
        imageOfWolfRight = new ImageView(Wolf.INSTANCE.rightWolfImage);
        imageOfWolfLeft = new ImageView(Wolf.INSTANCE.leftWolfImage);
        imageOfHandsUpLeft = new ImageView(Wolf.INSTANCE.leftUpHandImage);
        listOfImagesOfHands.add(imageOfHandsUpLeft);
        imageOfHandsDownLeft = new ImageView(Wolf.INSTANCE.leftDownHandImage);
        listOfImagesOfHands.add(imageOfHandsDownLeft);
        imageOfHandsDownRight = new ImageView(Wolf.INSTANCE.rightDownHandImage);
        listOfImagesOfHands.add(imageOfHandsDownRight);
        imageOfHandsUpRight = new ImageView(Wolf.INSTANCE.rightUpHandImage);
        listOfImagesOfHands.add(imageOfHandsUpRight);
        rightWood = new ImageView(GlobalStates.rightWood);
        leftWood = new ImageView(GlobalStates.leftWood);
        rightDownWood = new ImageView(GlobalStates.rightWood);
        leftDownWood = new ImageView(GlobalStates.leftWood);
        rightUpChicken = new ImageView(GlobalStates.rightChicken);
        rightDownChicken = new ImageView(GlobalStates.rightChicken);
        leftUpChicken = new ImageView(GlobalStates.leftChicken);
        leftDownChicken = new ImageView(GlobalStates.leftChicken);
        crackedEggLeft = new ImageView(GlobalStates.groundCrackedEgg);
        crackedEggRight = new ImageView(GlobalStates.groundCrackedEgg);

        imageOfWolfRight.setFitWidth(Wolf.WIDTH_OF_WOLF);
        imageOfWolfRight.setFitHeight(Wolf.HEIGHT_OF_WOLF);
        imageOfWolfLeft.setFitWidth(Wolf.WIDTH_OF_WOLF);
        imageOfWolfLeft.setFitHeight(Wolf.HEIGHT_OF_WOLF);
        imageOfWolfRight.setX(Wolf.rightWolfX);
        imageOfWolfRight.setY(Wolf.wolfY);
        imageOfWolfLeft.setX(Wolf.leftWolfX);
        imageOfWolfLeft.setY(Wolf.wolfY);

        imageOfHandsUpRight.setX(Wolf.rightHandsX);
        imageOfHandsUpRight.setY(Wolf.upHandsY);
        imageOfHandsDownRight.setX(Wolf.rightHandsX);
        imageOfHandsDownRight.setY(Wolf.downHandsY);
        imageOfHandsDownLeft.setX(Wolf.leftHandsX);
        imageOfHandsDownLeft.setY(Wolf.downHandsY);
        imageOfHandsUpLeft.setX(Wolf.leftHandsX);
        imageOfHandsUpLeft.setY(Wolf.upHandsY);
        for(ImageView imageView : listOfImagesOfHands){
            imageView.setFitHeight(Wolf.HEIGHT_OF_HANDS);
            imageView.setFitWidth(Wolf.WIDTH_OF_HANDS);
        }

        leftWood.setX(0);
        leftWood.setY(100);
        leftWood.setFitWidth(300);
        leftDownWood.setX(0);
        leftDownWood.setY(300);
        leftDownWood.setFitWidth(300);

        rightWood.setX(900);
        rightWood.setY(100);
        rightWood.setFitWidth(350);
        rightDownWood.setX(900);
        rightDownWood.setY(300);
        rightDownWood.setFitWidth(300);

        crackedEggLeft.setX(300);
        crackedEggLeft.setY(580);
        crackedEggLeft.setFitWidth(30);
        crackedEggLeft.setFitHeight(30);
        crackedEggRight.setX(900);
        crackedEggRight.setY(580);
        crackedEggRight.setFitWidth(30);
        crackedEggRight.setFitHeight(30);
        crackedEggRight.setVisible(false);
        crackedEggLeft.setVisible(false);
    }

    private void changingPosition(int verticalState, int horizontalState){
        if(verticalState == Wolf.UP && horizontalState == Wolf.RIGHT){
            imageOfHandsDownLeft.setVisible(false);
            imageOfHandsUpLeft.setVisible(false);
            imageOfWolfLeft.setVisible(false);
            imageOfHandsDownRight.setVisible(false);
            imageOfWolfRight.setVisible(true);
            imageOfHandsUpRight.setVisible(true);
        }
        else if(verticalState == Wolf.DOWN && horizontalState == Wolf.RIGHT){
            imageOfHandsDownLeft.setVisible(false);
            imageOfHandsUpLeft.setVisible(false);
            imageOfWolfLeft.setVisible(false);
            imageOfHandsDownRight.setVisible(true);
            imageOfWolfRight.setVisible(true);
            imageOfHandsUpRight.setVisible(false);
        }
        else if(verticalState == Wolf.UP && horizontalState == Wolf.LEFT){
            imageOfHandsDownLeft.setVisible(false);
            imageOfHandsUpLeft.setVisible(true);
            imageOfWolfLeft.setVisible(true);
            imageOfHandsDownRight.setVisible(false);
            imageOfWolfRight.setVisible(false);
            imageOfHandsUpRight.setVisible(false);
        }
        else if(verticalState == Wolf.DOWN && horizontalState == Wolf.LEFT){
            imageOfHandsDownLeft.setVisible(true);
            imageOfHandsUpLeft.setVisible(false);
            imageOfWolfLeft.setVisible(true);
            imageOfHandsDownRight.setVisible(false);
            imageOfWolfRight.setVisible(false);
            imageOfHandsUpRight.setVisible(false);
        }
    }

    public static void crackingEggs(int verticalState){
        if(verticalState == Wolf.RIGHT){
            crackedEggRight.setVisible(true);
        }
        else if(verticalState == Wolf.LEFT){
            crackedEggLeft.setVisible(true);
        }
    }

    public static void disappearEgg(){
        crackedEggLeft.setVisible(false);
        crackedEggRight.setVisible(false);
    }

}
