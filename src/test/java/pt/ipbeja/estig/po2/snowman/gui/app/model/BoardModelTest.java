package pt.ipbeja.estig.po2.snowman.gui.app.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardModelTest {
    @Test
    void boardtest() {
    List<List<PositionContent>> board = new ArrayList<>(List.of(
            new ArrayList<>(List.of(PositionContent.NO_SNOW)),
            new ArrayList<>(List.of())
    ));

   // BoardModel boardModel = new BoardModel(board);
    }

    @Test
    void  testMonsterToTheLeft() {

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