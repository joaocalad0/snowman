package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.scene.layout.GridPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Position;
import pt.ipbeja.estig.po2.snowman.gui.app.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.gui.app.model.View;

public class SnowManBoard extends GridPane implements View {

    private final BoardModel model;


    public SnowManBoard(BoardModel model) {
        this.model = model;
        createBoard();
    }

    private void createBoard() {

    }

    @Override
    public void update(Position position, PositionContent content) {

    }
}
