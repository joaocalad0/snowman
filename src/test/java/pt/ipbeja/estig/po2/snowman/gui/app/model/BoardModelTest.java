package pt.ipbeja.estig.po2.snowman.gui.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardModelTest {


    @Test
    void testMonsterToTheLeft() {
        BoardModel model = new BoardModel(5, 7, new View() {
            public void update(Position p, PositionContent c) {}
            public void onGameWon(PositionContent w) {}
        });

        model.updateCell(model.getMonster().getPosition(), PositionContent.NO_SNOW);
        model.getMonster().setPosition(new Position(1, 1));
        model.moveMonster(Direction.LEFT);

        assertAll(
                () -> assertEquals(1, model.getMonster().getPosition().getRow()),
                () -> assertEquals(0, model.getMonster().getPosition().getCol()),
                () -> assertEquals(PositionContent.MONSTER, model.getBoard().get(1).get(0)),
                () -> assertEquals(PositionContent.NO_SNOW, model.getBoard().get(1).get(1))
        );
    }

    @Test
    void  testCreateAverageSnowball() {

    }

    @Test
    void testCreateBigSnowball() {

    }

    @Test
    void testMaintainBigSnowball() {

    }

    @Test
    void testAverageBigSnowman() {

    }

    @Test
    void testCompleteSnowman() {

    }

}