package pt.ipbeja.estig.po2.snowman.gui.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardModel {

    private List<List<PositionContent>> board;
    private Monster monster;
    private Map<Position, SnowballType> snowballs = new HashMap<>();

    public BoardModel(int rows, int cols) {
        board = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++)  {
            List<PositionContent> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(PositionContent.NO_SNOW);
            }
            this.board.add(row);
        }
        level1();
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
    }

    private void level1() {
        board.get(0).set(0, PositionContent.SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);
    }

    public List<List<PositionContent>> getBoard() {
        return board;
    }

    public Monster getMonster() {
        return this.monster;
    }

    public void updateCell(Position position, PositionContent content){
        board.get(position.getRow()).set(position.getCol(), content);
    }

    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position newPos = calculateNewPosition(currentPos, direction);

        if (isValidMove(newPos)) {
            PositionContent destino = board.get(newPos.getRow()).get(newPos.getCol());

            switch (destino) {
                case SNOW -> {
                    // A posição atual do monstro transforma-se numa bola de neve pequena
                    snowballs.put(currentPos, SnowballType.SMALL);
                    updateCell(currentPos, PositionContent.SNOWBALL);
                    snowballs.put(currentPos, SnowballType.SMALL);

                    // O monstro avança para a nova posição
                    monster.setPosition(newPos);
                    updateCell(newPos, PositionContent.MONSTER);
                }
                case SNOWBALL -> {
                    // A bola de neve cresce na nova posição
                    SnowballType atual = snowballs.get(newPos);
                    SnowballType nova = grow(atual);
                    snowballs.put(newPos, nova);

                    // O monstro anda normalmente (posição atual fica a relva)
                    updateCell(currentPos, PositionContent.NO_SNOW);
                    monster.setPosition(newPos);
                    updateCell(newPos, PositionContent.MONSTER);
                }
                case NO_SNOW -> {
                    // Movimento normal em relva
                    updateCell(currentPos, PositionContent.NO_SNOW);
                    monster.setPosition(newPos);
                    updateCell(newPos, PositionContent.MONSTER);
                }
                case SNOWMAN -> {
                    // Pisar um boneco de neve, possível comportamento futuro
                    updateCell(currentPos, PositionContent.NO_SNOW);
                    monster.setPosition(newPos);
                    updateCell(newPos, PositionContent.MONSTER);
                }
                default -> {
                    // BLOCO ou outra coisa — não anda
                }
            }
        }
    }

    private Position calculateNewPosition(Position currentPos, Direction direction) {
        int row = currentPos.getRow();
        int col = currentPos.getCol();

        return switch (direction) {
            case UP -> new Position(row - 1, col);
            case DOWN -> new Position(row + 1, col);
            case LEFT -> new Position(row, col - 1);
            case RIGHT -> new Position(row, col + 1);
        };
    }

    private boolean isValidMove(Position position) {
        if (position.getRow() < 0 || position.getRow() >= board.size() ||
                position.getCol() < 0 || position.getCol() >= board.get(0).size()) {
            return false;
        }
        PositionContent content = board.get(position.getRow()).get(position.getCol());
        return content != PositionContent.BLOCK;
    }

    public SnowballType getSnowballTypeAt(Position position) {
        return snowballs.getOrDefault(position, SnowballType.SMALL);
    }

    private SnowballType grow(SnowballType current) {
        if (current == null)return SnowballType.SMALL;
        return switch (current) {
            case SMALL -> SnowballType.AVERAGE;
            case AVERAGE -> SnowballType.BIG;
            case BIG -> SnowballType.BIG;  // Limite máximo
            default -> SnowballType.SMALL; // fallback
        };
    }
}
