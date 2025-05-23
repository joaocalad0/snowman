package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.*;

import java.util.List;

public class SnowManBoard extends GridPane implements View {

    private final BoardModel model;
    private SnowmanCell[][] cells;

    public SnowManBoard(BoardModel model) {
        this.model = model;
        this.setHgap(0);
        this.setVgap(0);
        this.setPadding(Insets.EMPTY);
        createBoard();

        this.setOnKeyPressed(event -> {
            Position oldPos = model.getMonster().getPosition();
            switch (event.getCode()) {
                case UP -> model.moveMonster(Direction.UP);
                case DOWN -> model.moveMonster(Direction.DOWN);
                case LEFT -> model.moveMonster(Direction.LEFT);
                case RIGHT -> model.moveMonster(Direction.RIGHT);
                default -> {}
            }
            Position newPos = model.getMonster().getPosition();
            if (!newPos.equals(oldPos)) {
                updateMonsterPosition(oldPos, newPos);
            }
        });

        this.setFocusTraversable(true);
    }

    private void createBoard() {

        Position monsterPosition = model.getMonster().getPosition();
        List<List<PositionContent>> board = model.getBoard();
        cells = new SnowmanCell[board.size()][board.get(0).size()];
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                Position position = new Position(row, col);
                SnowmanCell cell = new SnowmanCell(position);

                PositionContent content = board.get(row).get(col);
                if (position.equals(monsterPosition)) {
                    cell.setAsMonster();
                } else if (content == PositionContent.SNOWBALL) {
                    SnowballType type = model.getSnowballTypeAt(position);
                    cell.setAsSnowball(type);
                } else {
                    cell.setPositionContent(content);
                }

                this.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
    }

    public void updateMonsterPosition(Position oldPos, Position newPos) {
        // Atualiza a célula antiga
        cells[oldPos.getRow()][oldPos.getCol()].setPositionContent(model.getBoard().get(oldPos.getRow()).get(oldPos.getCol()));

        // Atualiza a célula nova
        PositionContent newContent = model.getBoard().get(newPos.getRow()).get(newPos.getCol());
        if (newContent == PositionContent.SNOWBALL) {
            SnowballType type = model.getSnowballTypeAt(newPos);
            cells[newPos.getRow()][newPos.getCol()].setAsSnowball(type);
        } else {
            cells[newPos.getRow()][newPos.getCol()].setAsMonster();
        }
    }


    @Override
    public void update(Position position, PositionContent content) {
        model.updateCell(position, content);
    }

    @Override
    public void onGameWon(PositionContent positionContent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Player " + positionContent + " won!");
        alert.showAndWait();
    }
}
