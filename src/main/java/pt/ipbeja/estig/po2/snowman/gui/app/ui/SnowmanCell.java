package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Position;
import pt.ipbeja.estig.po2.snowman.gui.app.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.gui.app.model.SnowballType;

public class SnowmanCell extends StackPane {

    private static final Image no_snow = new Image("/no_snow.png");
    private static final Image snow = new Image("/snow.png");
    private static final Image block = new Image("/block.png");
    private static final Image monster = new Image("/monster.png");

    private static final Image smallSnowball = new Image("/smal.png");
    private static final Image averageSnowball = new Image("/average.png");
    private static final Image bigSnowball = new Image("/big.png");

    private final ImageView backgroundView = new ImageView();
    private final ImageView contentView = new ImageView();

    private final Position position;
    private static final double CELL_SIZE = 114;

    public SnowmanCell(Position position) {
        this.position = position;

        for (ImageView view : new ImageView[]{backgroundView, contentView}) {
            view.setFitWidth(CELL_SIZE);
            view.setFitHeight(CELL_SIZE);
            view.setPreserveRatio(false);
            view.setSmooth(false);
        }

        this.setPrefSize(CELL_SIZE, CELL_SIZE);
        this.setMinSize(CELL_SIZE, CELL_SIZE);
        this.setMaxSize(CELL_SIZE, CELL_SIZE);
        this.setPadding(Insets.EMPTY);

        this.getChildren().addAll(backgroundView, contentView);

        backgroundView.setImage(no_snow);
        contentView.setImage(null);
    }

    public Position getPosition() {
        return position;
    }

    public void setAsMonster() {
        backgroundView.setImage(no_snow);
        contentView.setImage(monster);
    }

    public void setPositionContent(PositionContent content) {
        switch (content) {
            case NO_SNOW -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(null);
            }
            case SNOW -> {
                backgroundView.setImage(snow);
                contentView.setImage(null);
            }
            case BLOCK -> {
                backgroundView.setImage(block);
                contentView.setImage(null);
            }
            case MONSTER -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(monster);
            }
            case SNOWBALL -> {
                backgroundView.setImage(snow);
                contentView.setImage(smallSnowball);
            }
            default -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(null);
            }
        }
    }

    public void setAsSnowball(SnowballType type) {
        backgroundView.setImage(snow);
        switch (type) {
            case SMALL -> contentView.setImage(smallSnowball);
            case AVERAGE -> contentView.setImage(averageSnowball);
            case BIG -> contentView.setImage(bigSnowball);
            default -> contentView.setImage(smallSnowball);
        }
    }
}
