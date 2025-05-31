

package pt.ipbeja.estig.po2.snowman.gui.app.model;

import pt.ipbeja.estig.po2.snowman.gui.app.model.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardModel {

    private final List<List<PositionContent>> board;
    private Monster monster;
    private final Map<Position, SnowballType> snowballs;
    private View view;

    public BoardModel(int rows, int cols, View view) {
        this.view = view;
        this.board = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<PositionContent> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(PositionContent.NO_SNOW);
            }
            this.board.add(row);
        }
        this.snowballs = new HashMap<>(); // Inicializar primeiro
        level1();                         // Depois já pode usar snowballs.put(...)
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
    }

    private void level1() {
        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);
    }
    public void setView(View view){
        this.view = view;
    }

    public SnowballType getSnowballTypeAt(Position position) {
        return snowballs.getOrDefault(position, SnowballType.SMALL);
    }

    private SnowballType grow(SnowballType current) {
        return switch (current) {
            case SMALL -> SnowballType.AVERAGE;
            case AVERAGE -> SnowballType.BIG;
            case BIG -> SnowballType.BIG;
            default -> SnowballType.SMALL;
        };
    }

    private Position calculateNewPosition(Position currentPos, Direction direction) {
        return switch (direction) {
            case UP -> new Position(currentPos.getRow() - 1, currentPos.getCol());
            case DOWN -> new Position(currentPos.getRow() + 1, currentPos.getCol());
            case LEFT -> new Position(currentPos.getRow(), currentPos.getCol() - 1);
            case RIGHT -> new Position(currentPos.getRow(), currentPos.getCol() + 1);
        };
    }

    private boolean isValidMove(Position pos) {
        int rows = board.size();
        int cols = board.get(0).size();
        if (pos.getRow() < 0 || pos.getRow() >= rows || pos.getCol() < 0 || pos.getCol() >= cols) {
            return false;
        }
        PositionContent content = board.get(pos.getRow()).get(pos.getCol());
        return content != PositionContent.BLOCK;
    }
    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position nextPos = calculateNewPosition(currentPos, direction);

        if (!isValidMove(nextPos)) return;

        PositionContent nextContent = board.get(nextPos.getRow()).get(nextPos.getCol());

        // Caso 1: empurrar bola de neve
        if (nextContent == PositionContent.SNOWBALL) {
            Position beyond = calculateNewPosition(nextPos, direction);
            if (!isValidMove(beyond)) return;

            PositionContent beyondContent = board.get(beyond.getRow()).get(beyond.getCol());
            if (beyondContent != PositionContent.NO_SNOW && beyondContent != PositionContent.SNOW) return;

            SnowballType currentType = snowballs.get(nextPos);
            if (currentType == null) {
                System.err.println("Erro: bola de neve em " + nextPos + " sem tipo associado.");
                currentType = SnowballType.SMALL;
                snowballs.put(nextPos, currentType);
            }

            // Cresce se for neve e não estiver no tamanho máximo
            if (beyondContent == PositionContent.SNOW && currentType != SnowballType.BIG) {
                currentType = grow(currentType);
            }

            // Atualiza posição anterior
            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.NO_SNOW);
            snowballs.remove(nextPos);
            view.update(nextPos, PositionContent.NO_SNOW);

            // Atualiza nova posição da bola
            board.get(beyond.getRow()).set(beyond.getCol(), PositionContent.SNOWBALL);
            snowballs.put(beyond, currentType);
            view.update(beyond, PositionContent.SNOWBALL);

        } else if (nextContent != PositionContent.NO_SNOW && nextContent != PositionContent.SNOW) {
            return;
        }

        // Atualiza célula atual do monstro
        if (snowballs.containsKey(currentPos)) {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.SNOWBALL);
            view.update(currentPos, PositionContent.SNOWBALL);
        } else {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.NO_SNOW);
            view.update(currentPos, PositionContent.NO_SNOW);
        }

        // Se for neve, cria uma nova bola pequena
        if (nextContent == PositionContent.SNOW) {
            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.SNOWBALL);
            snowballs.put(nextPos, SnowballType.SMALL);
            view.update(nextPos, PositionContent.SNOWBALL);
        }

        // Atualiza posição do monstro
        monster.setPosition(nextPos);
        board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
        view.update(nextPos, PositionContent.MONSTER);
    }



    public List<List<PositionContent>> getBoard() {
        return board;
    }

    public Monster getMonster() {
        return monster;
    }

    public void updateCell(Position newPos, PositionContent positionContent) {
    }
}
