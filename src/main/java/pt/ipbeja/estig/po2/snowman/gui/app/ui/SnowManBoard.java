package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import pt.ipbeja.estig.po2.snowman.gui.app.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.gui.app.model.Position;
import pt.ipbeja.estig.po2.snowman.gui.app.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.gui.app.model.View;

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
    }

    private void createBoard() {

        Position monsterPosition = model.getMonster().getPosition();
        List<List<PositionContent>> board = model.getBoard();
        cells = new SnowmanCell[board.size()][board.get(0).size()];
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                Position position = new Position(row, col);
                SnowmanCell cell = new SnowmanCell(position);
                if (position.equals(monsterPosition)) {
                    cell.setAsMonster();
                } else {
                    cell.setPositionContent(board.get(row).get(col));
                }

                this.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
    }

    public void updateMonsterPosition(Position oldPos, Position newPos) {
        // Limpar a célula antiga
        cells[oldPos.getRow()][oldPos.getCol()].setPositionContent(model.getBoard().get(oldPos.getRow()).get(oldPos.getCol()));
        // Definir a célula nova como monstro
        cells[newPos.getRow()][newPos.getCol()].setAsMonster();
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
