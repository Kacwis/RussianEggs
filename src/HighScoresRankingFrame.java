import Objects.HighScore;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class HighScoresRankingFrame extends Application {

    Button exit = new Button("EXIT");

    @Override
    public void start(Stage stage) throws Exception {
        ListView listOfHighScores = new ListView();
        for(HighScore highScore : GlobalStates.INSTANCE.listOfHighScores){
            listOfHighScores.getItems().add(highScore.getName() + "                " + highScore.getScore());
        }
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        exit.setLayoutX(300);
        exit.setLayoutY(350);
        ScrollPane scrollPane = new ScrollPane(listOfHighScores);
        System.out.println(GlobalStates.INSTANCE.listOfHighScores);
        Group group = new Group();
        group.getChildren().add(scrollPane);
        group.getChildren().add(exit);
        Scene scene = new Scene(group,400,400);
        stage.setScene(scene);
        stage.show();

    }
}
