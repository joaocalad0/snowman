package pt.ipbeja.estig.po2.snowman.model;

/**
 * Abstract class representing a mobile element in the game.
 */
public abstract class MobileElement {
    private Position position;

    /**
     * Creates a new MobileElement at specified position.
     * @param position Initial position
     */
    public MobileElement(Position position) {
        this.position = position;
    }

    /**
     * Gets the current position.
     * @return Current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position.
     * @param position New position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}

