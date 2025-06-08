package pt.ipbeja.estig.po2.snowman.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.ipbeja.estig.po2.snowman.model.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Optional;

/**
 * Represents the game board GUI for the Snowman game.
 */
public class SnowManBoard extends VBox implements View {

    private final BoardModel model;
    private SnowmanCell[][] cells;
    private static final double CELL_SIZE = 114;
    private final Pane boardPane = new Pane();
    private final Pane letterAndNumberOverlay = new Pane();
    private final TextArea moveHistoryArea = new TextArea();

    /**
     * Creates a new SnowManBoard with specified model.
     * @param model The game model
     */
    public SnowManBoard(BoardModel model) {
        this.model = model;

        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: transparent;");

        boardPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        createBoard();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setPrefRowCount(5);
        moveHistoryArea.setFocusTraversable(false);
        moveHistoryArea.setStyle("-fx-font-family: monospace;");

        StackPane boardWithLabels = new StackPane();
        boardWithLabels.getChildren().addAll(boardPane, letterAndNumberOverlay);

        this.getChildren().addAll(boardWithLabels, moveHistoryArea);
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
                addMoveToHistory(oldPos, newPos);
            }
        });

        this.setFocusTraversable(true);
    }

    /**
     * Adds a move to the history area.
     * @param from Starting position
     * @param to Destination position
     */
    private void addMoveToHistory(Position from, Position to) {
        String move = String.format("(%d,%c) → (%d,%c)",
                from.getRow() + 1,
                (char)('A' + from.getCol()),
                to.getRow() + 1,
                (char)('A' + to.getCol()));
        moveHistoryArea.appendText(move + "\n");
    }

    /**
     * Creates the game board.
     */
    private void createBoard() {
        Position monsterPosition = model.getMonster().getPosition();
        List<List<PositionContent>> board = model.getBoard();
        cells = new SnowmanCell[board.size()][board.get(0).size()];

        boardPane.getChildren().clear();
        letterAndNumberOverlay.getChildren().clear();

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
                cell.relocate(col * CELL_SIZE + 40, row * CELL_SIZE + 40);
                boardPane.getChildren().add(cell);
                cells[row][col] = cell;
            }
        }

        for (int r = 0; r < board.size(); r++) {
            Label numberLabel = new Label(String.valueOf(r + 1));
            numberLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 16;");
            numberLabel.setLayoutX(10);
            numberLabel.setLayoutY(r * CELL_SIZE + 40 + CELL_SIZE / 2 - 10);
            letterAndNumberOverlay.getChildren().add(numberLabel);
        }

        for (int c = 0; c < board.get(0).size(); c++) {
            Label letterLabel = new Label(String.valueOf((char) ('A' + c)));
            letterLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 16;");
            letterLabel.setLayoutX(c * CELL_SIZE + 40 + CELL_SIZE / 2 - 10);
            letterLabel.setLayoutY(10);
            letterAndNumberOverlay.getChildren().add(letterLabel);
        }
        boardPane.setPrefSize(
                board.get(0).size() * CELL_SIZE + 80,
                board.size() * CELL_SIZE + 80
        );
        letterAndNumberOverlay.setPrefSize(
                board.get(0).size() * CELL_SIZE + 80,
                board.size() * CELL_SIZE + 80
        );
    }

    /**
     * Updates the entire board.
     */
    @Override
    public void updateAllBoard() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Position pos = new Position(row, col);
                PositionContent content = model.getBoard().get(row).get(col);
                cells[row][col].setPositionContent(content);
            }
        }
    }

    /**
     * Updates the monster's position on the board.
     * @param oldPos Old position
     * @param newPos New position
     */
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

    /**
     * Updates a specific position on the board.
     * @param position Position to update
     * @param content New content
     */
    @Override
    public void update(Position position, PositionContent content) {
        if (content == PositionContent.SNOWBALL) {
            SnowballType type = model.getSnowballTypeAt(position);
            cells[position.getRow()][position.getCol()].setAsSnowball(type);
        } else {
            cells[position.getRow()][position.getCol()].setPositionContent(content);
        }
    }

    /**
     * Called when the player wins the game.
     * Displays a confirmation dialog asking whether to advance to level 2.
     * If confirmed, the model progresses to level 2 and the board is refreshed.
     *
     * @param positionContent The content at the winning position (not used in this method)
     */
    @Override
    public void onGameWon(PositionContent positionContent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parabéns!");
        alert.setHeaderText("Snowman criado com sucesso!");
        alert.setContentText("Vamos avançar para o nível 2?");

        ButtonType yesButton = new ButtonType("Sim");
        ButtonType noButton = new ButtonType("Não");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            model.level2();
            refreshBoard();
        }
    }

    /**
     * Refreshes the board UI by recreating the board and clearing the move history.
     * Also requests focus to enable key event handling.
     */
    private void refreshBoard() {
        createBoard();
        moveHistoryArea.clear();
        this.requestFocus();
    }
}