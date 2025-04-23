package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Position;
import pt.ipbeja.estig.po2.snowman.gui.app.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.gui.app.model.SnowmanGame;

public class SnowmanCell extends StackPane {

    private static final Image no_snow = new Image("");
    private static final Image snow = new Image("");
    private static final Image block = new Image("");
    private static final Image snowman = new Image("");

    private final ImageView imageView = new ImageView(no_snow);
    private final Position position;

    public SnowmanCell(Position position) {
        this.position = position;
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
