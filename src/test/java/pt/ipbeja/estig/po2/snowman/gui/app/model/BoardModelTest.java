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
/*
    @Test
    void  testMonsterToTheLeft() {

        BoardModel model = new BoardModel(5, 7);

        // Prepara posição inicial alternativa (1,1)
        Position initialPos = model.getMonster().getPosition();
        model.updateCell(initialPos, PositionContent.NO_SNOW);

        Position newStartPos = new Position(1, 1); // Declaração da variável
        model.getMonster().setPosition(newStartPos);
        model.updateCell(newStartPos, PositionContent.MONSTER);

        // Move para esquerda
        model.moveMonster(Direction.LEFT);

        // Verificações
        assertEquals(new Position(1, 0), model.getMonster().getPosition());
        assertEquals(PositionContent.MONSTER, model.getBoard().get(1).get(0));
    }*/

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