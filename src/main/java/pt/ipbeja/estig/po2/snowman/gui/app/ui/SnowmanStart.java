package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SnowmanStart extends Application {
    @Override
    public void start(Stage stage) {

        ImageView home = new ImageView(new Image("/snowmanHomeScreen.png"));
        home.setPreserveRatio(false);
        home.setFitWidth(800);
        home.setFitHeight(600);

        Button playButton = new Button("Play Game!");
        playButton.setId("playButton");
        StackPane root = new StackPane(home,playButton);

        playButton.setOnAction(event -> {
            SnowManBoard snowManBoard = new SnowManBoard();
            Scene gameScene = new Scene(snowManBoard, 800, 600);
            stage.setScene(gameScene);
        });

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("A Good Snowman Is Hard To Build");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}