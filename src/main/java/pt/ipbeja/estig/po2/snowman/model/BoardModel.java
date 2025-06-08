package pt.ipbeja.estig.po2.snowman.model;

import java.io.IOException;
import java.util.*;

/**
 * Represents the game board model for the Snowman game.
 * Manages the game state including monster, snowballs, and board content.
 */

public class BoardModel {
    private final List<List<PositionContent>> board;
    private Monster monster;
    private List<Snowball> snowballs;
    private final Map<Position, PositionContent> originalCellContent;
    private View view;
    private final List<String> moveHistory = new ArrayList<>();

    /**
     * Constructs a new BoardModel with specified dimensions and view.
     * @param rows Number of rows in the board
     * @param cols Number of columns in the board
     * @param view The view to be updated
     */

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
        this.snowballs = new ArrayList<>();
        this.originalCellContent = new HashMap<>();
        level1();
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
    }

    /**
     * Initializes level 1 with specific board configuration.
     */
    private void level1() {
        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(2, PositionContent.SNOW);
        board.get(1).set(3, PositionContent.SNOW);
        board.get(3).set(1, PositionContent.BLOCK);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);

        Position smallBallPos = new Position(2, 5);
        board.get(smallBallPos.getRow()).set(smallBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(smallBallPos, SnowballType.SMALL));

        Position averageBallPos = new Position(4, 4);
        board.get(averageBallPos.getRow()).set(averageBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(averageBallPos, SnowballType.AVERAGE));

        Position bigBallPos = new Position(2, 3);
        board.get(bigBallPos.getRow()).set(bigBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(bigBallPos, SnowballType.BIG));
    }

    /**
     * Initializes level 2 with specific board configuration.
     */
    public void level2() {
        for (List<PositionContent> row : board) {
            Collections.fill(row, PositionContent.NO_SNOW);
        }
        snowballs.clear();
        originalCellContent.clear();

        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(2, PositionContent.SNOW);
        board.get(1).set(3, PositionContent.SNOW);
        board.get(3).set(1, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);

        Position smallBallPos = new Position(3, 4);
        board.get(smallBallPos.getRow()).set(smallBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(smallBallPos, SnowballType.SMALL));

        Position averageBallPos = new Position(4, 2);
        board.get(averageBallPos.getRow()).set(averageBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(averageBallPos, SnowballType.AVERAGE));

        Position bigBallPos = new Position(1, 3);
        board.get(bigBallPos.getRow()).set(bigBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.add(new Snowball(bigBallPos, SnowballType.BIG));

        monster.setPosition(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);

        if (view != null) {
            view.updateAllBoard();
        }
    }

    /**
     * Moves the monster in the specified direction if possible.
     * @param direction The direction to move the monster
     */
    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position nextPos = calculateNewPosition(currentPos, direction);

        if (!isValidMove(nextPos)) return;

        PositionContent nextContent = board.get(nextPos.getRow()).get(nextPos.getCol());
        originalCellContent.putIfAbsent(nextPos, nextContent);
        if (nextContent == PositionContent.SNOWBALL) {
            if (handleSnowballPush(currentPos, nextPos, direction)) {
                return;
            }
        }
        else if (nextContent == PositionContent.BLOCK ||
                nextContent == PositionContent.SNOWMAN) {
            return;
        }
        updatePreviousCell(currentPos);
        monster.setPosition(nextPos);
        board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
        view.update(nextPos, PositionContent.MONSTER);
        recordMove(currentPos, nextPos);
    }

    /**
     * Handles pushing a snowball by the monster.
     * @param currentPos Current position of the monster
     * @param nextPos Next position (snowball position)
     * @param direction Direction of movement
     * @return true if push was handled, false otherwise
     */
    private boolean handleSnowballPush(Position currentPos, Position nextPos, Direction direction) {
        Position beyondPos = calculateNewPosition(nextPos, direction);
        if (!isValidMove(beyondPos)) return true;

        PositionContent beyondContent = board.get(beyondPos.getRow()).get(beyondPos.getCol());
        Snowball currentSnowball = null;
        for (Snowball s : snowballs) {
            if (s.getPosition().equals(nextPos)) {
                currentSnowball = s;
                break;
            }
        }
        if (currentSnowball == null) return true;

        SnowballType currentType = currentSnowball.getType();

        if (currentType == SnowballType.BIG && beyondContent == PositionContent.SNOWBALL) {
            if (getSnowballTypeAt(beyondPos) == SnowballType.AVERAGE) {
                return true;
            }
        }
        if (currentType == SnowballType.BIG) {
            Snowball beyondSnowball = null;
            for (Snowball s : snowballs) {
                if (s.getPosition().equals(beyondPos)) {
                    beyondSnowball = s;
                    break;
                }
            }
            if (beyondSnowball != null && beyondSnowball.getType() == SnowballType.AVERAGE_SMALL) {
                return true;
            }
        }
        if (tryCombineToAverageSmall(nextPos, beyondPos, currentType, beyondContent)) return true;
        if (tryCombineToAverageBig(nextPos, beyondPos, currentType, beyondContent)) return true;
        if (tryMakeSnowman(currentPos, nextPos, beyondPos, currentType, beyondContent)) return true;
        if (tryMakeSnowmanSmallToAverageBig(currentPos, nextPos, beyondPos, currentType, beyondContent)) return true;

        pushSnowball(nextPos, beyondPos, currentType, beyondContent);
        return false;
    }

    /**
     * Attempts to combine a small snowball with an average one to create average-small.
     * @param nextPos Position of the snowball being pushed
     * @param beyondPos Position beyond the snowball
     * @param currentType Type of current snowball
     * @param beyondContent Content beyond the snowball
     * @return true if combination was successful, false otherwise
     */
    private boolean tryCombineToAverageSmall(Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        if (beyondContent == PositionContent.SNOWBALL) {
            Snowball beyondSnowball = null;
            for (Snowball s : snowballs) {
                if (s.getPosition().equals(beyondPos)) {
                    beyondSnowball = s;
                    break;
                }
            }
            if (beyondSnowball != null &&
                    currentType == SnowballType.SMALL &&
                    beyondSnowball.getType() == SnowballType.AVERAGE) {

                snowballs.remove(beyondSnowball);
                snowballs.add(new Snowball(beyondPos, SnowballType.AVERAGE_SMALL));
                view.update(beyondPos, PositionContent.SNOWBALL);
                clearOldSnowball(nextPos);
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to combine an average snowball with a big one to create average-big.
     * @param nextPos Position of the snowball being pushed
     * @param beyondPos Position beyond the snowball
     * @param currentType Type of current snowball
     * @param beyondContent Content beyond the snowball
     * @return true if combination was successful, false otherwise
     */

    private boolean tryCombineToAverageBig(Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        if (beyondContent == PositionContent.SNOWBALL) {
            Snowball beyondSnowball = null;
            for (Snowball s : snowballs) {
                if (s.getPosition().equals(beyondPos)) {
                    beyondSnowball = s;
                    break;
                }
            }
            if (beyondSnowball != null &&
                    currentType == SnowballType.AVERAGE &&
                    beyondSnowball.getType() == SnowballType.BIG) {

                snowballs.remove(beyondSnowball);
                snowballs.add(new Snowball(beyondPos, SnowballType.AVERAGE_BIG));
                view.update(beyondPos, PositionContent.SNOWBALL);
                clearOldSnowball(nextPos);
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to create a snowman by combining a small snowball with an average-big one.
     * @param currentPos Current position of the monster
     * @param nextPos Next position (snowball position)
     * @param beyondPos Position beyond the snowball
     * @param currentType Type of current snowball
     * @param beyondContent Content beyond the snowball
     * @return true if snowman was created, false otherwise
     */

    private boolean tryMakeSnowmanSmallToAverageBig(Position currentPos, Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        Snowball beyondSnowball = null;
        for (Snowball s : snowballs) {
            if (s.getPosition().equals(beyondPos)) {
                beyondSnowball = s;
                break;
            }
        }
        if (beyondSnowball != null &&
                currentType == SnowballType.SMALL &&
                beyondSnowball.getType() == SnowballType.AVERAGE_BIG) {

            board.get(beyondPos.getRow()).set(beyondPos.getCol(), PositionContent.SNOWMAN);
            snowballs.remove(beyondSnowball);
            originalCellContent.remove(beyondPos);
            view.update(beyondPos, PositionContent.SNOWMAN);

            clearOldSnowball(nextPos);

            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
            view.update(nextPos, PositionContent.MONSTER);

            board.get(currentPos.getRow()).set(currentPos.getCol(), originalCellContent.getOrDefault(currentPos, PositionContent.NO_SNOW));
            originalCellContent.remove(currentPos);
            view.update(currentPos, board.get(currentPos.getRow()).get(currentPos.getCol()));

            recordMove(currentPos, nextPos);
            checkSnowmanCompletion(beyondPos);

            return true;
        }
        return false;
    }

    /**
     * Attempts to create a snowman by combining an average-small snowball with a big one.
     * @param currentPos Current position of the monster
     * @param nextPos Next position (snowball position)
     * @param beyondPos Position beyond the snowball
     * @param currentType Type of current snowball
     * @param beyondContent Content beyond the snowball
     * @return true if snowman was created, false otherwise
     */
    private boolean tryMakeSnowman(Position currentPos, Position nextPos, Position beyondPos, SnowballType currentType, PositionContent beyondContent) {
        Snowball beyondSnowball = null;
        for (Snowball s : snowballs) {
            if (s.getPosition().equals(beyondPos)) {
                beyondSnowball = s;
                break;
            }
        }
        if (beyondSnowball != null &&
                ((currentType == SnowballType.AVERAGE_SMALL && beyondSnowball.getType() == SnowballType.BIG))){

            board.get(beyondPos.getRow()).set(beyondPos.getCol(), PositionContent.SNOWMAN);
            snowballs.remove(beyondSnowball);
            originalCellContent.remove(beyondPos);
            view.update(beyondPos, PositionContent.SNOWMAN);
            clearOldSnowball(nextPos);
            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
            view.update(nextPos, PositionContent.MONSTER);
            board.get(currentPos.getRow()).set(currentPos.getCol(), originalCellContent.getOrDefault(currentPos, PositionContent.NO_SNOW));
            originalCellContent.remove(currentPos);
            view.update(currentPos, board.get(currentPos.getRow()).get(currentPos.getCol()));

            recordMove(currentPos, nextPos);
            checkSnowmanCompletion(beyondPos);

            return true;
        }

        return false;
    }

    /**
     * Clears the old snowball from a position.
     * @param pos Position to clear
     */
    private void clearOldSnowball(Position pos) {
        snowballs.removeIf(s -> s.getPosition().equals(pos));
        originalCellContent.remove(pos);

        PositionContent currentContent = board.get(pos.getRow()).get(pos.getCol());
        if (currentContent == PositionContent.SNOWBALL) {
            board.get(pos.getRow()).set(pos.getCol(), PositionContent.NO_SNOW);
            view.update(pos, PositionContent.NO_SNOW);
        }
    }


    /**
     * Pushes a snowball from one position to another.
     * @param from Starting position of the snowball
     * @param to Destination position
     * @param currentType Current type of the snowball
     * @param beyondContent Content at the destination position
     */
    private void pushSnowball(Position from, Position to, SnowballType currentType, PositionContent beyondContent) {
        SnowballType newType = currentType;
        boolean grew = false;

        if (beyondContent == PositionContent.SNOW && currentType != SnowballType.BIG) {
            newType = grow(currentType);
        }
        PositionContent oldCellContent = originalCellContent.getOrDefault(from, PositionContent.NO_SNOW);
        board.get(from.getRow()).set(from.getCol(), oldCellContent);
        originalCellContent.remove(from);
        snowballs.removeIf(s -> s.getPosition().equals(from));
        view.update(from, oldCellContent);

        board.get(to.getRow()).set(to.getCol(), PositionContent.SNOWBALL);
        snowballs.removeIf(s -> s.getPosition().equals(to));
        snowballs.add(new Snowball(to, newType));
        view.update(to, PositionContent.SNOWBALL);
    }


    /**
     * Updates the previous cell after monster moves away.
     * @param currentPos Position to update
     */
    private void updatePreviousCell(Position currentPos) {
        // Ao sair da posição atual, restaura o conteúdo original
        PositionContent original = originalCellContent.getOrDefault(currentPos, PositionContent.NO_SNOW);
        board.get(currentPos.getRow()).set(currentPos.getCol(), original);
        snowballs.removeIf(s -> s.getPosition().equals(currentPos));
        originalCellContent.remove(currentPos);
        view.update(currentPos, original);
    }


    /**
     * Records a move in the move history.
     * @param from Starting position
     * @param to Destination position
     */
    private void recordMove(Position from, Position to) {
        moveHistory.add(String.format("(%d,%c) → (%d,%c)",
                from.getRow() + 1,
                (char)('A' + from.getCol()),
                to.getRow() + 1,
                (char)('A' + to.getCol())));
    }

    /**
     * Checks if a snowman was completed at a position.
     * @param position Position to check
     */
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

    /**
     * Sets the view for this model.
     * @param view The view to set
     */
    public void setView(View view) {
        this.view = view;
    }


    /**
     * Gets the snowball type at a specific position.
     * @param position Position to check
     * @return SnowballType at the position
     */
    public SnowballType getSnowballTypeAt(Position position) {
        for (Snowball snowball : snowballs) {
            if (snowball.getPosition().equals(position)) {
                return snowball.getType();
            }
        }
        return SnowballType.SMALL;
    }


    /**
     * Grows a snowball to the next size.
     * @param current Current snowball type
     * @return Next snowball type
     */
    private SnowballType grow(SnowballType current) {
        return switch (current) {
            case SMALL -> SnowballType.AVERAGE;
            case AVERAGE -> SnowballType.BIG;
            case AVERAGE_SMALL -> SnowballType.BIG;
            case AVERAGE_BIG -> SnowballType.AVERAGE_BIG;
            case BIG -> SnowballType.BIG;
        };
    }

    /**
     * Calculates new position based on current position and direction.
     * @param currentPos Current position
     * @param direction Direction to move
     * @return New position
     */
    private Position calculateNewPosition(Position currentPos, Direction direction) {
        return switch (direction) {
            case UP -> new Position(currentPos.getRow() - 1, currentPos.getCol());
            case DOWN -> new Position(currentPos.getRow() + 1, currentPos.getCol());
            case LEFT -> new Position(currentPos.getRow(), currentPos.getCol() - 1);
            case RIGHT -> new Position(currentPos.getRow(), currentPos.getCol() + 1);
        };
    }

    /**
     * Checks if a move to a position is valid.
     * @param pos Position to check
     * @return true if move is valid, false otherwise
     */
    private boolean isValidMove(Position pos) {
        int rows = board.size();
        int cols = board.get(0).size();
        if (pos.getRow() < 0 || pos.getRow() >= rows || pos.getCol() < 0 || pos.getCol() >= cols) {
            return false;
        }
        PositionContent content = board.get(pos.getRow()).get(pos.getCol());
        return content != PositionContent.BLOCK;
    }

    /**
     * Gets the game board.
     * @return The game board
     */
    public List<List<PositionContent>> getBoard() {
        return board;
    }

    /**
     * Gets the monster.
     * @return The monster
     */
    public Monster getMonster() {
        return monster;
    }

    /**
     * Updates a cell with new content.
     * @param newPos Position to update
     * @param positionContent New content for the cell
     */
    public void updateCell(Position newPos, PositionContent positionContent) {
        board.get(newPos.getRow()).set(newPos.getCol(), positionContent);
        if (view != null) {
            view.update(newPos, positionContent);
        }
    }

    /**
     * Gets all snowballs on the board.
     * @return List of snowballs
     */
    public List<Snowball> getSnowballs() {
        return this.snowballs;
    }
}