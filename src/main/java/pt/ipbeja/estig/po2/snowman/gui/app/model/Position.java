package pt.ipbeja.estig.po2.snowman.gui.app.model;
import java.util.Objects;

public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

    public Position getNeighbor(Direction direction) {
        return switch (direction) {
            case UP -> new Position(row-1, col);
            case DOWN -> new Position(row+1, col);
            case LEFT -> new Position(row, col-1);
            case RIGHT -> new Position(row, col+1);
        };
    }
}

