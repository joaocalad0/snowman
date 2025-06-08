package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents a position on a 2D grid defined by row and column indices.
 */
public class Position {
    private final int row;
    private final int col;

    /**
     * Constructs a Position with specified row and column.
     *
     * @param row the row index
     * @param col the column index
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row index of this position.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of this position.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Compares this Position to another object for equality.
     * <p>
     * The method returns true if and only if the other object:
     * <ul>
     *   <li>is not null</li>
     *   <li>is of the exact same class (Position)</li>
     *   <li>has identical row and column values</li>
     * </ul>
     * This ensures that two Positions with the same coordinates are
     * considered equal, which is important for collections that rely on
     * equals() such as Sets or keys in Maps.
     *
     * @param o the object to compare against
     * @return true if this and o represent the same position; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // quick check for reference equality
        if (o == null || getClass() != o.getClass()) return false; // class and null check
        Position position = (Position) o;
        return row == position.row && col == position.col; // field-by-field comparison
    }

    /**
     * Returns a hash code consistent with equals().
     * <p>
     * The hash code is computed as 31 * row + col, which is a common
     * way to combine multiple integer fields into a single hash code.
     * <p>
     * This method is critical for the correct behavior of hash-based
     * collections such as HashMap and HashSet.
     *
     * @return an integer hash code for this Position
     */
    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    /**
     * Returns a string representation of this Position.
     * <p>
     * The format is: Position{row=X, col=Y}
     * which is useful for debugging and logging purposes.
     *
     * @return a string describing this Position
     */
    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
