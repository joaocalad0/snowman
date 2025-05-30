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
        level1();
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
        this.snowballs = new HashMap<>();
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
        Position newPos = calculateNewPosition(currentPos, direction);
        if (!isValidMove(newPos)) {
            return;
        }
        PositionContent newPosContent = board.get(newPos.getRow()).get(newPos.getCol());
        if (board.get(currentPos.getRow()).get(currentPos.getCol()) == PositionContent.MONSTER) {
            if (snowballs.containsKey(currentPos)) {
                board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.SNOWBALL);
                view.update(currentPos, PositionContent.SNOWBALL);
            } else {
                board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.NO_SNOW);
                view.update(currentPos, PositionContent.NO_SNOW);
            }
        }

        if (newPosContent == PositionContent.SNOW) {
            board.get(newPos.getRow()).set(newPos.getCol(), PositionContent.SNOWBALL);
            snowballs.put(newPos, SnowballType.SMALL);
            view.update(newPos, PositionContent.SNOWBALL);
        } else if (newPosContent == PositionContent.SNOWBALL) {
            SnowballType currentBall = snowballs.get(newPos);
            SnowballType newBall = grow(currentBall);
            snowballs.put(newPos, newBall);
            view.update(newPos, PositionContent.SNOWBALL);
        } else {
            view.update(newPos, newPosContent);
        }
        monster.setPosition(newPos);
        board.get(newPos.getRow()).set(newPos.getCol(), PositionContent.MONSTER);
        view.update(newPos, PositionContent.MONSTER);
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
