package pt.ipbeja.estig.po2.snowman.model;

import java.io.IOException;
import java.util.*;

public class BoardModel {
    private final List<List<PositionContent>> board;
    private Monster monster;
    private final Map<Position, SnowballType> snowballs;
    private final Map<Position, PositionContent> originalCellContent;
    private View view;
    private final List<String> moveHistory = new ArrayList<>();

    // Construtor
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
        this.snowballs = new HashMap<>();
        this.originalCellContent = new HashMap<>();
        level1();
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
    }

    // Inicialização do nível 1
    private void level1() {
        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);

        Position smallBallPos = new Position(2, 5);
        board.get(smallBallPos.getRow()).set(smallBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(smallBallPos, SnowballType.SMALL);

        Position averageBallPos = new Position(4, 4);
        board.get(averageBallPos.getRow()).set(averageBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(averageBallPos, SnowballType.AVERAGE);

        Position bigBallPos = new Position(1, 3);
        board.get(bigBallPos.getRow()).set(bigBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(bigBallPos, SnowballType.BIG);
    }

    // Inicialização do nível 2
    public void level2() {
        for (List<PositionContent> row : board) {
            Collections.fill(row, PositionContent.NO_SNOW);
        }
        snowballs.clear();
        originalCellContent.clear();

        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(1).set(3, PositionContent.BLOCK);
        board.get(3).set(1, PositionContent.BLOCK);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);

        Position smallBallPos = new Position(3, 4);
        board.get(smallBallPos.getRow()).set(smallBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(smallBallPos, SnowballType.SMALL);

        Position averageBallPos = new Position(4, 2);
        board.get(averageBallPos.getRow()).set(averageBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(averageBallPos, SnowballType.AVERAGE);

        Position bigBallPos = new Position(1, 3);
        board.get(bigBallPos.getRow()).set(bigBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(bigBallPos, SnowballType.BIG);

        monster.setPosition(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);

        if (view != null) {
            view.updateAllBoard();
        }
    }

    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position nextPos = calculateNewPosition(currentPos, direction);

        if (!isValidMove(nextPos)) return;

        PositionContent nextContent = board.get(nextPos.getRow()).get(nextPos.getCol());

        if (nextContent == PositionContent.SNOWBALL) {
            if (handleSnowballPush(currentPos, nextPos, direction)) return;
        } else if (nextContent != PositionContent.NO_SNOW && nextContent != PositionContent.SNOW) {
            return;
        }

        updatePreviousCell(currentPos);
        if (nextContent == PositionContent.SNOW) {
            handleSnowCreationIfNeeded(nextPos);
        }

        monster.setPosition(nextPos);
        board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
        view.update(nextPos, PositionContent.MONSTER);

        if (!nextPos.equals(currentPos)) {
            recordMove(currentPos, nextPos);
            checkSnowmanCompletion(nextPos);
        }
    }

    private boolean handleSnowballPush(Position currentPos, Position nextPos, Direction direction) {
        Position beyondPos = calculateNewPosition(nextPos, direction);
        if (!isValidMove(beyondPos)) return true;

        PositionContent beyondContent = board.get(beyondPos.getRow()).get(beyondPos.getCol());
        SnowballType currentType = snowballs.get(nextPos);

        if (tryCombineToAverageSmall(nextPos, beyondPos, currentType, beyondContent)) return true;
        if (tryCombineToAverageBig(nextPos, beyondPos, currentType, beyondContent)) return true;
        if (tryMakeSnowman(currentPos, nextPos, beyondPos, currentType, beyondContent)) return true;

        pushSnowball(nextPos, beyondPos, currentType, beyondContent);
        return false;
    }

    private boolean tryCombineToAverageSmall(Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        if (beyondContent == PositionContent.SNOWBALL) {
            SnowballType beyondType = snowballs.get(beyondPos);
            if (currentType == SnowballType.SMALL && beyondType == SnowballType.AVERAGE) {
                snowballs.put(beyondPos, SnowballType.AVERAGE_SMALL);
                view.update(beyondPos, PositionContent.SNOWBALL);
                clearOldSnowball(nextPos);
                return true;
            }
        }
        return false;
    }

    private boolean tryCombineToAverageBig(Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        if (beyondContent == PositionContent.SNOWBALL) {
            SnowballType beyondType = snowballs.get(beyondPos);
            if (currentType == SnowballType.BIG && beyondType == SnowballType.AVERAGE) {
                snowballs.put(beyondPos, SnowballType.AVERAGE_BIG);
                view.update(beyondPos, PositionContent.SNOWBALL);
                clearOldSnowball(nextPos);
                return true;
            }
        }
        return false;
    }

    private boolean tryMakeSnowman(Position currentPos, Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        SnowballType beyondType = snowballs.get(beyondPos);
        if ((currentType == SnowballType.AVERAGE_SMALL && beyondType == SnowballType.BIG) ||
                (currentType == SnowballType.BIG && beyondType == SnowballType.AVERAGE_SMALL)) {

            board.get(beyondPos.getRow()).set(beyondPos.getCol(), PositionContent.SNOWMAN);
            snowballs.remove(beyondPos);
            originalCellContent.remove(beyondPos);
            view.update(beyondPos, PositionContent.SNOWMAN);

            clearOldSnowball(nextPos);
            recordMove(currentPos, nextPos);
            checkSnowmanCompletion(beyondPos);
            return true;
        }
        return false;
    }

    private void clearOldSnowball(Position pos) {
        board.get(pos.getRow()).set(pos.getCol(), originalCellContent.getOrDefault(pos, PositionContent.NO_SNOW));
        originalCellContent.remove(pos);
        snowballs.remove(pos);
        view.update(pos, board.get(pos.getRow()).get(pos.getCol()));
    }

    private void pushSnowball(Position from, Position to, SnowballType currentType, PositionContent beyondContent) {
        if (beyondContent == PositionContent.SNOW && currentType != SnowballType.BIG) {
            currentType = grow(currentType);
        }

        PositionContent oldCellContent = originalCellContent.getOrDefault(from, PositionContent.NO_SNOW);
        board.get(from.getRow()).set(from.getCol(), oldCellContent);
        originalCellContent.remove(from);
        snowballs.remove(from);
        view.update(from, oldCellContent);

        originalCellContent.put(to, board.get(to.getRow()).get(to.getCol()));
        board.get(to.getRow()).set(to.getCol(), PositionContent.SNOWBALL);
        snowballs.put(to, currentType);
        view.update(to, PositionContent.SNOWBALL);
    }

    private void updatePreviousCell(Position currentPos) {
        if (snowballs.containsKey(currentPos)) {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.SNOWBALL);
            view.update(currentPos, PositionContent.SNOWBALL);
        } else {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.NO_SNOW);
            view.update(currentPos, PositionContent.NO_SNOW);
        }
    }

    private void handleSnowCreationIfNeeded(Position nextPos) {
        if (board.get(nextPos.getRow()).get(nextPos.getCol()) == PositionContent.SNOW) {
            originalCellContent.put(nextPos, board.get(nextPos.getRow()).get(nextPos.getCol()));
            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.SNOWBALL);
            snowballs.put(nextPos, SnowballType.SMALL);
            view.update(nextPos, PositionContent.SNOWBALL);
        }
    }



    // --- Métodos auxiliares novos ---
    private void recordMove(Position from, Position to) {
        moveHistory.add(String.format("(%d,%c) → (%d,%c)",
                from.getRow() + 1,
                (char)('A' + from.getCol()),
                to.getRow() + 1,
                (char)('A' + to.getCol())));
    }

    private void checkSnowmanCompletion(Position position) {
        if (board.get(position.getRow()).get(position.getCol()) == PositionContent.SNOWMAN) {
            try {
                GameRecorder.saveGame(moveHistory, position);
                view.onGameWon(PositionContent.SNOWMAN);
            } catch (IOException e) {
                System.err.println("Erro ao gravar histórico: " + e.getMessage());
            }
        }
    }

    // --- Métodos originais mantidos ---
    public void setView(View view) {
        this.view = view;
    }

    public SnowballType getSnowballTypeAt(Position position) {
        return snowballs.getOrDefault(position, SnowballType.SMALL);
    }

    private SnowballType grow(SnowballType current) {
        return switch (current) {
            case SMALL -> SnowballType.AVERAGE;
            case AVERAGE -> SnowballType.BIG;
            case AVERAGE_SMALL -> SnowballType.BIG;
            case AVERAGE_BIG -> SnowballType.AVERAGE_BIG;
            case BIG -> SnowballType.BIG;
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

    public List<List<PositionContent>> getBoard() {
        return board;
    }

    public Monster getMonster() {
        return monster;
    }

    public void updateCell(Position newPos, PositionContent positionContent) {
        // Implementação original mantida
    }
}