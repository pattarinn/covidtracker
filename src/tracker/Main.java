package tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for ruuning application
 * 
 * @author Pattarin Wongwaipanich
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Assign stage for StageManager
        StageManager stageManager = StageManager.getInstance();
        stageManager.setStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        primaryStage.setTitle("COVID-19 Tracker");
        Scene scene = new Scene(root, 700, 500);
        // add main scene
        StageManager.getInstance().register("main", scene);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
