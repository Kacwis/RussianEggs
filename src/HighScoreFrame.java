import Objects.HighScore;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HighScoreFrame extends Application {

    Label winning = new Label("THE END");
    Label text = new Label("INSERT YOUR NAME");
    TextField name = new TextField();
    Button accept = new Button("ACCEPT");

    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        group.getChildren().add(winning);
        group.getChildren().add(text);
        group.getChildren().add(name);
        group.getChildren().add(accept);
        name.setLayoutX(35);
        name.setLayoutY(65);
        winning.setLayoutX(80);
        winning.setLayoutY(10);
        text.setLayoutX(50);
        text.setLayoutY(40);
        accept.setLayoutX(75);
        accept.setLayoutY(100);
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GlobalStates.INSTANCE.listOfHighScores.add(new HighScore(name.getText(),Integer.toString(GlobalStates.INSTANCE.pointsCounter)));
                GlobalStates.INSTANCE.savingHighScores();
                MenuFrame menuFrame = new MenuFrame();
                try {
                    menuFrame.start(new Stage());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                stage.close();
            }
        });
        group.setVisible(true);
        Scene scene = new Scene(group, 200,200);
        stage.setScene(scene);
        stage.show();
    }
}
