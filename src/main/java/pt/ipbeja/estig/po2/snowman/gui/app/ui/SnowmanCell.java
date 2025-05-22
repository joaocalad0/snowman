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
    //private static final Image snowman = new Image("block");
    private static final Image monster = new Image("/monster.png");

    private final ImageView backgroundView = new ImageView(); //fundo da primeira camada (relva ou neve)
    private final ImageView imageView = new ImageView();      // mantém: conteúdo (bloco, monstro, etc.) em cima da reva como segudo plano


    private final Position position;
    private static final double CELL_SIZE = 114;

    public SnowmanCell(Position position) {
        this.position = position;

        for (ImageView view : new ImageView[]{backgroundView, imageView}) {
            view.setFitWidth(CELL_SIZE);
            view.setFitHeight(CELL_SIZE);
            view.setPreserveRatio(false);
            view.setSmooth(false);
        }

        this.setPrefSize(CELL_SIZE, CELL_SIZE);
        this.setMinSize(CELL_SIZE, CELL_SIZE);
        this.setMaxSize(CELL_SIZE, CELL_SIZE);
        this.setPadding(Insets.EMPTY);
        StackPane.setMargin(imageView, Insets.EMPTY);

        // fundo depois conteúdo
        this.getChildren().addAll(backgroundView, imageView);

        // Por defeito: relva no fundo, vazio por cima
        backgroundView.setImage(no_snow);
        imageView.setImage(null);
    }

    public Position getPosition() {
        return position;
    }

    public void setAsMonster() {
        //print just for debug
        System.out.println("A definir imagem de monstro em " + position);
        this.imageView.setImage(monster);
        this.setStyle(null);
    }


    public void setPositionContent(PositionContent content) {
        switch (content) {
            case NO_SNOW -> imageView.setImage(no_snow);
            case SNOW -> imageView.setImage(snow);
            case BLOCK -> imageView.setImage(block);
            //case SNOWMAN -> imageView.setImage(snowman);
            case MONSTER -> imageView.setImage(monster);
        }
    }
}
