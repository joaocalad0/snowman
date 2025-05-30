package pt.ipbeja.estig.po2.snowman.gui.app.model;

import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLogicTest {
    private BoardModel model;

    @BeforeEach
    void setUp() {
        //Configuração inicial para todos os testes
        model = new BoardModel(7, 7);

        //Posição inicial do monstro
        model.setMonsterPosition(new Position(2, 2));
    }

    @Test
    void testMonsterToTheLeft() {
        // Configuração
        Position initialPosition = new Position(2, 2);
        model.setMonsterPosition(initialPosition);

        // Ação
        model.moveMonster(Direction.LEFT);

        // Verificação
        Position expectedPosition = new Position(2, 1);
        Position actualPosition = model.getMonsterPosition();

        assertEquals(expectedPosition.getRow(), actualPosition.getRow(), "Linha incorreta após movimento");
        assertEquals(expectedPosition.getCol(), actualPosition.getCol(), "Coluna incorreta após movimento");

        // Ou simplesmente:
        assertEquals(expectedPosition, actualPosition, "Posição do monstro incorreta após mover para esquerda");
    }
}

    //@Test
   /* void testCreateAverageSnowball() {
        // Configuração
        Position snowPos = new Position(2, 2);
        Position smallBallPos = new Position(2, 1);
        Position monsterPos = new Position(2, 0);

        // Prepara o tabuleiro
        model.setPositionContentForTest(snowPos, PositionContent.SNOW);
        model.addTestSnowball(smallBallPos, SnowballType.SMALL);
        model.setMonsterPosition(monsterPos);

        // Execução
        model.moveMonster(Direction.RIGHT); // Move para (2,1) - onde está a bola pequena
        model.moveMonster(Direction.RIGHT); // Empurra a bola para (2,2) - onde tem neve

        // Verificação
        SnowballType type = model.getSnowballTypeAt(new Position(2, 2));
        assertEquals(SnowballType.AVERAGE, type,
                "A bola deveria ter crescido de SMALL para AVERAGE ao ser empurrada para a neve");

        assertEquals(PositionContent.SNOWBALL, model.getPositionContent(new Position(2, 2)),
                "A posição (2,2) deveria conter uma bola de neve");
    }
/*
    @Test
    void testCreateBigSnowball() {

            Position snowPosition = new Position(2, 3);
            model.setPositionContent(snowPosition, PositionContent.SNOW);
            Position averageBallPosition = new Position(2,1);
            model.addSnowBall(averageBallPosition, SnowballType.AVERAGE);


            model.moveMonster(Direction.LEFT);      //vai para (2,1)
            model.moveMonster(Direction.RIGHT);     //empurra para (2,2)


            assertEquals(SnowballType.BIG,model.getSnowballAt(new Position(2, 2)).getType());

    }

    @Test
    void testMaintainBigSnowball() {

            Position snowPosition = new Position(2,3);
            model.setPositionContetn(snowPosition, PositionContent.SNOW);
            Position bigBallPosition = new Position(2, 1);
            model.addSnowBall(bigBallPosition, SnowballType.BIG);

            model.moveMonster(Direction.LEFT);      //vai para (2,1)
            model.moveMonster(Direction.RIGHT);     //empura para (2,2)

            assertEquals(SnowballType.BIG, model.getSnowBallAt(new Position(2,2)).getType());

    }

    @Test
    void testAverageBigSnowman() {

            Position basePosition = new Position(3,2);
            model.addSnowBall(basePosition, SnowballType.BIG);
            Position averagePosition = new Position(1,2);
            model.addSnowBall(averagePosition, SnowballType.AVERAGE);


            model.moveMonster(Direction.UP);    //vai para (1,2)
            model.moveMonster(Direction.DOWN);  //empurra para (2,2)
            model.moveMonster(Direction.DOWN);  //empurra para (3,2)

            assertTrue(model.isPartialSnowMan(basePosition));
            assertEquals(SnowballType.BIG_AVERAGE, model.getSnowballAt(basePosition).getType());
    }

    @Test
    void testCompleteSnowman() {

            Position basePosition = new Position(3,2);
            model.addSnowBall(basePosition, SnowballType.BIG_AVERAGE);
            Position smallPosition = new Position(1,2);
            model.addSnowBall(smallPosition, SnowballType.SMALL);


            model.moveMonster(Direction.UP);    //vai para (1,2)
            model.moveMonster(Direction.DOWN);  //empurra para (2,2)
            model.moveMonster(Direction.DOWN);  //empurra para (3,2) */



