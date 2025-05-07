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

        // Butões
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

        playButton.setId("playButton");

        // Ação para o Butão "Play Game!" viajar para a board de jogo
        playButton.setOnAction(event -> {
            BoardModel boardModel = new BoardModel(5, 7);
            SnowManBoard snowManBoard = new SnowManBoard(boardModel);
            Scene gameScene = new Scene(snowManBoard, 800, 600);
            stage.setScene(gameScene);
        });

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("A Good Snowman Is Hard To Build");
        stage.setScene(scene);
        stage.show();

        //Música background menu
        BackgroundMusic menuMusic = new BackgroundMusic();
        menuMusic.playMusic("/lofi-chill-background-music-330144.wav");

    }

    public static void main(String[] args) {
        launch();
    }
}