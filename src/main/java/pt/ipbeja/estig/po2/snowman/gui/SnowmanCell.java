package pt.ipbeja.estig.po2.snowman.gui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.model.Position;
import pt.ipbeja.estig.po2.snowman.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.model.SnowballType;

/**
 * Represents a single cell in the Snowman game board.
 */
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
    private final ImageView snowballView = new ImageView();

    private final Position position;
    private static final double CELL_SIZE = 116;

    /**
     * Creates a new SnowmanCell at the specified position.
     * @param position The position of this cell
     */
    public SnowmanCell(Position position) {
        this.position = position;

        for (ImageView view : new ImageView[]{backgroundView, contentView, snowballView}) {
            view.setFitWidth(CELL_SIZE);
            view.setFitHeight(CELL_SIZE);
            view.setPreserveRatio(false);
            view.setSmooth(false);
            view.setCache(true);
            view.setPickOnBounds(false);
        }

        this.setPrefSize(CELL_SIZE, CELL_SIZE);
        this.setMinSize(CELL_SIZE, CELL_SIZE);
        this.setMaxSize(CELL_SIZE, CELL_SIZE);
        this.setPadding(Insets.EMPTY);
        this.setSnapToPixel(true);
        this.getChildren().addAll(backgroundView, contentView, snowballView);

        backgroundView.setImage(no_snow);
        contentView.setImage(null);

        this.setSnapToPixel(true);
    }

    /**
     * Sets this cell as containing the monster.
     */
    public void setAsMonster() {
        contentView.setImage(monster);
        snowballView.setImage(null);
    }

    /**
     * Sets the content of this cell.
     * @param content The content to set
     */
    public void setPositionContent(PositionContent content) {
        switch (content) {
            case NO_SNOW -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(null);
                snowballView.setImage(null);
            }
            case SNOW -> {
                backgroundView.setImage(snow);
                contentView.setImage(null);
                snowballView.setImage(null);
            }
            case BLOCK -> {
                backgroundView.setImage(block);
                contentView.setImage(null);
                snowballView.setImage(null);
            }
            case MONSTER -> {
                setAsMonster();
            }
            case SNOWMAN -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(new Image("/snowman.png"));
                snowballView.setImage(null);
            }
            default -> {
                backgroundView.setImage(no_snow);
                contentView.setImage(null);
                snowballView.setImage(null);
            }
        }
    }

    /**
     * Sets this cell as containing a snowball of specified type.
     * @param type The type of snowball
     */
    public void setAsSnowball(SnowballType type) {
        if (type == SnowballType.BIG) {

        } else {
            backgroundView.setImage(no_snow);
        }
        switch (type) {
            case SMALL -> {
                snowballView.setImage(smallSnowball);
                snowballView.setFitWidth(CELL_SIZE * 0.35);
                snowballView.setFitHeight(CELL_SIZE * 0.35);
            }
            case AVERAGE -> {
                snowballView.setImage(averageSnowball);
                snowballView.setFitWidth(CELL_SIZE * 0.5);
                snowballView.setFitHeight(CELL_SIZE * 0.5);
            }
            case BIG -> {
                snowballView.setImage(bigSnowball);
                snowballView.setFitWidth(CELL_SIZE * 0.75);
                snowballView.setFitHeight(CELL_SIZE * 0.75);
            }
            case AVERAGE_SMALL -> {
                snowballView.setImage(new Image("/average_small.png"));
                snowballView.setFitWidth(CELL_SIZE * 1.55);
            }
            case  AVERAGE_BIG -> {
                snowballView.setImage(new Image("/average_big.png"));
                snowballView.setFitWidth(CELL_SIZE * 1.75);
            }
            default -> {
                snowballView.setImage(smallSnowball);
                snowballView.setFitWidth(CELL_SIZE * 0.25);
                snowballView.setFitHeight(CELL_SIZE * 0.25);
            }
        }
        snowballView.setPreserveRatio(true);
    }
}


