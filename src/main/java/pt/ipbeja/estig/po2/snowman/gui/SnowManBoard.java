package pt.ipbeja.estig.po2.snowman.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.snowman.model.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Optional;

public class SnowManBoard extends VBox implements View {

    private final BoardModel model;
    private SnowmanCell[][] cells;
    private static final double CELL_SIZE = 114;
    private final Pane boardPane = new Pane();
    private final Pane letterAndNumberOverlay = new Pane();
    private final TextArea moveHistoryArea = new TextArea();

    public SnowManBoard(BoardModel model) {
        this.model = model;

        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: transparent;");

        // Configura o painel do tabuleiro
        boardPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Cria o tabuleiro
        createBoard();

        // Configura a área de histórico de movimentos
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setPrefRowCount(5);
        moveHistoryArea.setFocusTraversable(false);
        moveHistoryArea.setStyle("-fx-font-family: monospace;");

        // Cria um StackPane para o tabuleiro e rótulos
        StackPane boardWithLabels = new StackPane();
        boardWithLabels.getChildren().addAll(boardPane, letterAndNumberOverlay);

        // Adiciona os elementos ao layout principal
        this.getChildren().addAll(boardWithLabels, moveHistoryArea);

        // Configuração do evento de teclado
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

    private void addMoveToHistory(Position from, Position to) {
        String move = String.format("(%d,%c) → (%d,%c)",
                from.getRow() + 1,
                (char)('A' + from.getCol()),
                to.getRow() + 1,
                (char)('A' + to.getCol()));
        moveHistoryArea.appendText(move + "\n");
    }

    private void createBoard() {
        Position monsterPosition = model.getMonster().getPosition();
        List<List<PositionContent>> board = model.getBoard();
        cells = new SnowmanCell[board.size()][board.get(0).size()];

        // Limpa os painéis
        boardPane.getChildren().clear();
        letterAndNumberOverlay.getChildren().clear();

        // Cria as células do tabuleiro com margem para os rótulos
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

                // Posiciona as células com margem para os rótulos
                cell.relocate(col * CELL_SIZE + 40, row * CELL_SIZE + 40);
                boardPane.getChildren().add(cell);
                cells[row][col] = cell;
            }
        }

        // Adiciona rótulos das linhas (números) - lado esquerdo
        for (int r = 0; r < board.size(); r++) {
            Label numberLabel = new Label(String.valueOf(r + 1));
            numberLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 16;");
            numberLabel.setLayoutX(10); // Posiciona à esquerda do tabuleiro
            numberLabel.setLayoutY(r * CELL_SIZE + 40 + CELL_SIZE / 2 - 10); // Centraliza verticalmente
            letterAndNumberOverlay.getChildren().add(numberLabel);
        }

        // Adiciona rótulos das colunas (letras) - topo
        for (int c = 0; c < board.get(0).size(); c++) {
            Label letterLabel = new Label(String.valueOf((char) ('A' + c)));
            letterLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 16;");
            letterLabel.setLayoutX(c * CELL_SIZE + 40 + CELL_SIZE / 2 - 10); // Centraliza horizontalmente
            letterLabel.setLayoutY(10); // Posiciona acima do tabuleiro
            letterAndNumberOverlay.getChildren().add(letterLabel);
        }

        // Define o tamanho do painel do tabuleiro com espaço para os rótulos
        boardPane.setPrefSize(
                board.get(0).size() * CELL_SIZE + 80,
                board.size() * CELL_SIZE + 80
        );

        // Define o tamanho do overlay para garantir que os rótulos sejam visíveis
        letterAndNumberOverlay.setPrefSize(
                board.get(0).size() * CELL_SIZE + 80,
                board.size() * CELL_SIZE + 80
        );
    }

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parabéns!");
        alert.setHeaderText("Snowman criado com sucesso!");
        alert.setContentText("Vamos avançar para o nível 2?");

        ButtonType yesButton = new ButtonType("Sim");
        ButtonType noButton = new ButtonType("Não");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            model.level2(); // Carrega o nível 2
            refreshBoard();  // Atualiza o tabuleiro
        }
    }

    private void refreshBoard() {
        createBoard();
        moveHistoryArea.clear();
        this.requestFocus();
    }
}