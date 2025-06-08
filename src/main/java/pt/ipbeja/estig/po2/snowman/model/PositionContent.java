package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents the possible contents of a position (cell) on the game board.
 *
 * Each constant corresponds to a different type of element that can occupy
 * a cell, affecting game logic and interactions.
 */
public enum PositionContent {
    /**
     * Represents an empty cell with no snow.
     */
    NO_SNOW,

    /**
     * Represents a cell covered with snow.
     */
    SNOW,

    /**
     * Represents an immovable block or obstacle on the board.
     */
    BLOCK,

    /**
     * Represents a snowball present on the board.
     */
    SNOWBALL,

    /**
     * Represents a completed snowman on the board.
     */
    SNOWMAN,

    /**
     * Represents the monster character on the board.
     */
    MONSTER
}
