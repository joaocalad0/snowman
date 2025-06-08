package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents a snowball in the game.
 */
public class Snowball extends MobileElement {
    private SnowballType type;

    /**
     * Creates a new Snowball at specified position with specified type.
     * @param position Position of the snowball
     * @param type Type of the snowball
     */
    public Snowball(Position position, SnowballType type) {
        super(position);
        this.type = type;
    }

    /**
     * Gets the type of this snowball.
     * @return The snowball type
     */
    public SnowballType getType() {
        return type;
    }

    /**
     * Sets the type of this snowball.
     * @param type The new type
     */
    public void setType(SnowballType type) {
        this.type = type;
    }
}