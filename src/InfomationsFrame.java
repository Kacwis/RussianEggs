import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InfomationsFrame extends Application {

    Text informations = new Text();
    @Override
    public void start(Stage stage) throws Exception {
        informations.setText(" W - UP \n S - DOWN \n A - LEFT \n D - RIGHT");
        informations.setLayoutX(30);
        informations.setLayoutY(30);
        Group group = new Group();
        group.getChildren().add(informations);
        Scene scene = new Scene(group,100,100);
        stage.setScene(scene);
        stage.show();
    }
}
