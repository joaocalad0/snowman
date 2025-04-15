package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Model;

import java.util.Optional;

public class SnowmanStart extends Application {
    @Override
    public void start(Stage stage) {

        StackPane pane = new StackPane();

        Scene scene = new Scene(pane, 800, 600);
        stage.setTitle(SnowmanStart.class.getSimpleName());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}