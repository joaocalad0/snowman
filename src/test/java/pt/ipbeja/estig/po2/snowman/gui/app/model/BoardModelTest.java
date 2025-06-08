package pt.ipbeja.estig.po2.snowman.gui.app.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.snowman.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardModelTest {

    /**
     * Tests if the monster moves correctly to the left side.
     * The monster starts at position (0,0), moves right and then moves back to the left.
     * Verifies that it ends up again at (0,0).
     */
    @Test
    void testMonsterToTheLeft() {
        // Create a fake view
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        // Create a fake 5x7 board
        BoardModel board = new BoardModel(5, 7, fakeView);

        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);
        board.moveMonster(Direction.RIGHT);
        board.moveMonster(Direction.LEFT);


        assertEquals(PositionContent.MONSTER,board.getBoard().get(0).get(0 ), "The monster should have moved back to the left");
    }

    /**
     * Tests if a SMALL snowball turns into an AVERAGE snowball
     * when pushed over snow.
     * Snowball starts as SMALL at (0,1), pushed to (0,2) which contains snow.
     */
    @Test
    void testCreateAverageSnowball() {
        // Create a fake view
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        // Create a 5x7 board with the fake view
        BoardModel board = new BoardModel(5, 7, fakeView);
        Position smallBallPos = new Position(0, 1);

        // Place SMALL snowball at (0,1) and snow at (0,2)
        board.getBoard().get(0).set(1, PositionContent.SNOWBALL);
        board.getBoard().get(0).set(2, PositionContent.SNOW);
        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);

        board.getSnowballs().add(new Snowball(smallBallPos, SnowballType.SMALL));
        board.moveMonster(Direction.RIGHT);

        assertEquals(SnowballType.AVERAGE,board.getSnowballTypeAt(new Position(0, 2)), "The snowball should have grown to AVERAGE");
    }

    /**
     * Tests if an AVERAGE snowball becomes a BIG snowball
     * when pushed over snow.
     * Snowball starts as AVERAGE at (0,1), pushed to (0,2).
     */
    @Test
    void testCreateBigSnowball() {
        // Create a fake view
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        BoardModel board = new BoardModel(5, 7, fakeView);
        Position averageBallPos = new Position(0, 1);

        // Place AVERAGE snowball at (0,1) and snow at (0,2)
        board.getBoard().get(0).set(1, PositionContent.SNOWBALL);
        board.getBoard().get(0).set(2, PositionContent.SNOW);
        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);

        board.getSnowballs().add(new Snowball(averageBallPos, SnowballType.AVERAGE));
        board.moveMonster(Direction.RIGHT);

        assertEquals(SnowballType.BIG,board.getSnowballTypeAt(new Position(0, 2)), "The snowball should have grown to BIG");
    }

    /**
     * Tests that a BIG snowball does not grow further when pushed over snow.
     * Snowball starts as BIG at (0,1), and should stay BIG after move.
     */
    @Test
    void testMaintainBigSnowball() {
        // Create a fake view
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        BoardModel board = new BoardModel(5, 7, fakeView);
        Position bigBallPos = new Position(0, 1);

        // Place BIG snowball at (0,1) and snow at (0,2)
        board.getBoard().get(0).set(1, PositionContent.SNOWBALL);
        board.getBoard().get(0).set(2, PositionContent.SNOW);
        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);

        board.getSnowballs().add(new Snowball(bigBallPos, SnowballType.BIG));
        board.moveMonster(Direction.RIGHT);

        assertEquals(SnowballType.BIG,board.getSnowballTypeAt(new Position(0, 2)), "The snowball should remain BIG even over snow");
    }

    /**
     * Tests if pushing an AVERAGE snowball into a BIG one creates
     * an incomplete snowman (type AVERAGE_BIG).
     * The resulting snowball should be of type AVERAGE_BIG.
     */
    @Test
    void testAverageBigSnowman() {
        // Create a fake view
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        BoardModel board = new BoardModel(5, 7, fakeView);

        Position averageBallPos = new Position(0, 1);
        Position bigBallPos = new Position(0, 2);

        // Place AVERAGE and BIG snowballs
        board.getBoard().get(0).set(1, PositionContent.SNOWBALL);
        board.getBoard().get(0).set(2, PositionContent.SNOWBALL);
        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);

        board.getSnowballs().add(new Snowball(averageBallPos, SnowballType.AVERAGE));
        board.getSnowballs().add(new Snowball(bigBallPos, SnowballType.BIG));
        board.moveMonster(Direction.RIGHT);


        assertEquals(SnowballType.AVERAGE_BIG,board.getSnowballTypeAt(new Position(0, 2)), "An incomplete snowman should be formed");
    }

    /**
     * Tests if pushing a SMALL snowball into an AVERAGE_BIG snowball
     * creates a complete snowman.
     * The final content at the position should be PositionContent.SNOWMAN.
     */
    @Test
    void testCompleteSnowman() {
        View fakeView = new View() {
            @Override
            public void update(Position pos, PositionContent content) {
            }

            @Override
            public void updateAllBoard() {
            }

            @Override
            public void onGameWon(PositionContent winner) {
            }
        };

        BoardModel board = new BoardModel(5, 7, fakeView);

        Position smallBallPos = new Position(0, 1);
        Position averageBigBallPos = new Position(0, 2);

        // Place SMALL and AVERAGE_BIG snowballs
        board.getBoard().get(0).set(1, PositionContent.SNOWBALL);
        board.getBoard().get(0).set(2, PositionContent.SNOWBALL);
        board.getMonster().setPosition(new Position(0, 0));
        board.updateCell(new Position(0, 0), PositionContent.MONSTER);

        board.getSnowballs().add(new Snowball(smallBallPos, SnowballType.SMALL));
        board.getSnowballs().add(new Snowball(averageBigBallPos, SnowballType.AVERAGE_BIG));
        board.moveMonster(Direction.RIGHT);


        assertEquals(PositionContent.SNOWMAN,board.getBoard().get(0).get(2), "A complete snowman should be created");
    }
}
