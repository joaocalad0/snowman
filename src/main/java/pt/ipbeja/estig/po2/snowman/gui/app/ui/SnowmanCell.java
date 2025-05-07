package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Position;
import pt.ipbeja.estig.po2.snowman.gui.app.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.gui.app.model.SnowmanGame;

public class SnowmanCell extends StackPane {

    private static final Image no_snow = new Image("/no_snow.png");
    private static final Image snow = new Image("/snow.png");
    private static final Image block = new Image("/block.png");
    private static final Image snowman = new Image("/block.png");

    private final ImageView imageView = new ImageView(no_snow);
    private final Position position;
    private static final double CELL_SIZE = 114;


    public SnowmanCell(Position position) {
        this.position = position;
        this.imageView.setSmooth(false);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
        imageView.setPreserveRatio(false);

        this.setPrefSize(CELL_SIZE, CELL_SIZE);
        this.setMinSize(CELL_SIZE, CELL_SIZE);
        this.setMaxSize(CELL_SIZE, CELL_SIZE);

        this.setPadding(Insets.EMPTY);
        StackPane.setMargin(imageView, Insets.EMPTY);

        this.getChildren().add(imageView);
    }

    public Position getPosition() {
        return position;
    }

    public void setPositionContent(PositionContent content) {
        switch (content) {
            case NO_SNOW -> imageView.setImage(no_snow);
            case SNOW -> imageView.setImage(snow);
            case BLOCK -> imageView.setImage(block);
            case SNOWMAN -> imageView.setImage(snowman);
        }
    }
}
