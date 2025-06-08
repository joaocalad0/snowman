package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents a monster that can move, collect snowballs,
 * and create a snowman on the board.
 */
public class Monster extends MobileElement {
    private int snowballCount;

    /**
     * Constructs a Monster at the given position with zero snowballs collected.
     *
     * @param position the initial position of the monster
     */
    public Monster(Position position) {
        super(position);
        this.snowballCount = 0;
    }

    /**
     * Returns the current number of collected snowballs.
     *
     * @return the number of snowballs the monster has collected
     */
    public int getSnowballCount() {
        return snowballCount;
    }

    /**
     * Increments the count of collected snowballs by one,
     * up to a maximum of 3.
     */
    public void incrementSnowballCount() {
        if (snowballCount < 3) {
            snowballCount++;
        }
    }

    /**
     * Resets the count of collected snowballs to zero.
     */
    public void resetSnowballCount() {
        snowballCount = 0;
    }

    /**
     * Attempts to push a snowball on the board at the specified new position.
     * If there is snow at the position, increments the snowball count and
     * clears the snow from the board at that position.
     *
     * @param board the game board model
     * @param newPos the position to push the snowball to
     */
    public void pushSnowball(BoardModel board, Position newPos) {
        PositionContent content = board.getBoard().get(newPos.getRow()).get(newPos.getCol());
        if (content == PositionContent.SNOW) {
            incrementSnowballCount();
            board.updateCell(newPos, PositionContent.NO_SNOW);
        }
    }

    /**
     * Checks whether the monster has collected enough snowballs (3)
     * to form a snowman.
     *
     * @param board the game board model (not used in the current implementation)
     * @return true if the monster has collected 3 snowballs, false otherwise
     */
    public boolean canFormSnowman(BoardModel board) {
        return snowballCount == 3;
    }

    /**
     * Creates a snowman at the specified position on the board if the monster
     * has collected 3 snowballs. Resets the snowball count after creation.
     *
     * @param board the game board model
     * @param newPos the position to place the snowman
     */
    public void createSnowman(BoardModel board, Position newPos) {
        if (canFormSnowman(board)) {
            board.updateCell(newPos, PositionContent.SNOWMAN);
            resetSnowballCount();
        }
    }
}
