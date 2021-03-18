
    import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

    public class MenuFrame extends Application {

        final Button newGame = new Button("NEW GAME");
        final Button highScores = new Button("HIGH SCORES");
        final Button exit = new Button("EXIT");
        final Button informations = new Button("INFO");


        @Override
        public void start(Stage stage) throws Exception {
            Group group = new Group();
            settingButtons();
            stage.setTitle("Nu pogodi!");
            group.getChildren().add(newGame);
            group.getChildren().add(highScores);
            group.getChildren().add(exit);
            group.getChildren().add(informations);
            Scene scene = new Scene(group,400,400);
            stage.setScene(scene);
            stage.show();

        }


        private void settingButtons(){
            newGame.setLayoutX(160);
            newGame.setLayoutY(120);
            highScores.setLayoutX(155);
            highScores.setLayoutY(150);
            informations.setLayoutX(175);
            informations.setLayoutY(180);
            exit.setLayoutX(180);
            exit.setLayoutY(210);

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    MainFrame mainFrame = new MainFrame();
                    Stage stage =(Stage)(newGame.getScene().getWindow());
                    stage.close();
                    try {
                        mainFrame.start(new Stage());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                  //  GlobalStates.INSTANCE.savingHighScores();
                    System.exit(0);
                }
            });

            highScores.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        HighScoresRankingFrame highScoresRankingFrame = new HighScoresRankingFrame();
                        highScoresRankingFrame.start(new Stage());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            informations.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try{
                        InfomationsFrame infomationsFrame = new InfomationsFrame();
                        infomationsFrame.start(new Stage());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
