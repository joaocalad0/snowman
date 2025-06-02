package pt.ipbeja.estig.po2.snowman.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import pt.ipbeja.estig.po2.snowman.gui.*;
import pt.ipbeja.estig.po2.snowman.model.*;

import java.util.List;

public class SnowManBoard extends Pane implements View {

    private final BoardModel model;
    private SnowmanCell[][] cells;

    private static final double CELL_SIZE = 114;

    public SnowManBoard(BoardModel model) {
        this.model = model;

        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        createBoard();
        cells[0][0].setAsMonster();

        // Configura o evento de tecla para mover o monstro
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

                // Posiciona manualmente para evitar gaps
                cell.relocate(col * CELL_SIZE, row * CELL_SIZE);

                this.getChildren().add(cell);
                cells[row][col] = cell;
            }
        }

        // Define tamanho do painel para conter todas as células
        this.setPrefSize(board.get(0).size() * CELL_SIZE, board.size() * CELL_SIZE);
    }

    // Atualiza visualmente a célula antiga e nova do monstro
    public void updateMonsterPosition(Position oldPos, Position newPos) {
        PositionContent oldContent = model.getBoard().get(oldPos.getRow()).get(oldPos.getCol());
        if (oldContent == PositionContent.SNOWBALL) {
            SnowballType oldType = model.getSnowballTypeAt(oldPos);
            cells[oldPos.getRow()][oldPos.getCol()].setAsSnowball(oldType);
        } else {
            cells[oldPos.getRow()][oldPos.getCol()].setPositionContent(oldContent);
        }

        PositionContent newContent = model.getBoard().get(newPos.getRow()).get(newPos.getCol());
        if (newContent == PositionContent.SNOWBALL) {
            SnowballType newType = model.getSnowballTypeAt(newPos);
            cells[newPos.getRow()][newPos.getCol()].setAsSnowball(newType);
        } else {
            cells[newPos.getRow()][newPos.getCol()].setAsMonster();
        }
    }

    // Implementa método da interface View para atualizar a UI
    @Override
    public void update(Position position, PositionContent content) {
        if (content == PositionContent.SNOWBALL) {
            SnowballType type = model.getSnowballTypeAt(position);
            cells[position.getRow()][position.getCol()].setAsSnowball(type);
        } else {
            cells[position.getRow()][position.getCol()].setPositionContent(content);
        }
    }

    @Override
    public void onGameWon(PositionContent positionContent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Player " + positionContent + " won!");
        alert.showAndWait();
    }
}


