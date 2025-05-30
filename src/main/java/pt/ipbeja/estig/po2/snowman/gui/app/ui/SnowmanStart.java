package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.snowman.gui.app.model.BoardModel;



public class SnowmanStart extends Application {
    @Override
    public void start(Stage stage) {
        //Música background menu
        SoundController.playMusic("/MenuBackgroundMusic.wav");

        // Imagem de inico de jogo
        ImageView home = new ImageView(new Image("/snowmanHomeScreen.png"));
        home.setPreserveRatio(false);
        home.setFitWidth(800);
        home.setFitHeight(600);

        // Gif do monstro no ecrã principal
        ImageView monsterGif = new ImageView(new Image("/AGoodSnowman_Animated.gif"));
        monsterGif.setFitWidth(160);
        monsterGif.setFitHeight(160);
        StackPane.setAlignment(monsterGif, Pos.CENTER_RIGHT);
        StackPane.setMargin(monsterGif, new Insets(-120, 180, 0, 0));

        //Settings Icon
        ImageView settings = new ImageView(new Image("/SettingsIcon.png"));
        settings.setFitWidth(50);
        settings.setFitHeight(50);
        StackPane.setAlignment(settings, Pos.CENTER_LEFT);
        StackPane.setMargin(settings, new Insets(505, 0, 0, 20));

        // Botões
        Button playButton = new Button("Play!");
        Button resumeButton = new Button("Resume Game!");
        Button quitButton = new Button("Quit Game!");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(playButton,resumeButton,quitButton);
        vbox.getStyleClass().add("vbox");

        StackPane root = new StackPane(home,monsterGif,vbox, settings);
        StackPane.setMargin(vbox, new Insets(220,0,0,0));

        ImageView levelsMap = new ImageView(new Image("/levelsmap.png"));
        levelsMap.setFitWidth(800);
        levelsMap.setFitHeight(600);

        playButton.setOnAction(event -> {
            // Botão para entrar no level 1
            Button level1 = new Button("Level 1");
            level1.getStyleClass().add("button");

            level1.setOnAction(e -> {
                BoardModel boardModel = new BoardModel(5, 7);
                SnowManBoard snowManBoard = new SnowManBoard(boardModel);
                Scene gameScene = new Scene(snowManBoard, 800, 600);
                gameScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

                stage.setScene(gameScene);
                snowManBoard.requestFocus();

                // Música background do 1º nível
                SoundController.playMusic("/Level1Music.wav");
            });

            VBox levelButtons = new VBox(10, level1);
            levelButtons.setAlignment(Pos.CENTER);
            levelButtons.getStyleClass().add("vbox");

            StackPane levelRoot = new StackPane(levelsMap, levelButtons, settings);

            Scene levelScene = new Scene(levelRoot, 800, 600);
            levelScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(levelScene);
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