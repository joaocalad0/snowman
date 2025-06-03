package pt.ipbeja.estig.po2.snowman.gui.app.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.snowman.model.*;
import java.util.List;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardModelTest {

    //1º teste
        @Test
        void testMonsterToTheLeft() {
            // 1. Criar board com uma implementação mínima de View
            BoardModel board = new BoardModel(5, 7, new View() {
                public void update(Position position, PositionContent content) {}
                public void onGameWon(PositionContent c) {}
                public void updateAllBoard() {}
            });

            assertAll(
                    () -> assertEquals(new Position(0, 0), board.getMonster().getPosition()),
                    () -> { board.moveMonster(Direction.LEFT);
                        assertEquals(new Position(0, 0), board.getMonster().getPosition()); },
                    () -> { board.moveMonster(Direction.RIGHT);
                        assertEquals(new Position(0, 1), board.getMonster().getPosition()); },
                    () -> { board.moveMonster(Direction.LEFT);
                        assertEquals(new Position(0, 0), board.getMonster().getPosition()); }
            );


        }

            @Test
    void  testCreateAverageSnowball() {

                BoardModel board = new BoardModel(5, 7, new View() {
                    @Override public void update(Position p, PositionContent c) {}
                    @Override public void onGameWon(PositionContent c) {}
                    @Override public void updateAllBoard(){}
                });
                // 2. Limpa o tabuleiro
                for (List<PositionContent> row : board.getBoard()) {
                    Collections.fill(row, PositionContent.NO_SNOW);
                }
                // 3. Posicionar neve onde a bola será criada (2,3) e transformada (2,5)
                board.getBoard().get(2).set(3, PositionContent.SNOW);
                board.getBoard().get(2).set(5, PositionContent.SNOW);
                // 4. Posicionar monstro em (0,0) - POSIÇÃO INICIAL CORRIGIDA
                //board.getMonster().setPosition(new Position(0, 0));
                //board.getBoard().get(0).set(0, PositionContent.MONSTER);
                // 5. Movimentos para levar o monstro até (2,3) - CRIA BOLA PEQUENA
                board.moveMonster(Direction.DOWN);  // (0,0) → (1,0)
                board.moveMonster(Direction.DOWN);  // (1,0) → (2,0)
                board.moveMonster(Direction.RIGHT); // (2,0) → (2,1)
                board.moveMonster(Direction.RIGHT); // (2,1) → (2,2)
                board.moveMonster(Direction.RIGHT); // (2,2) → (2,3)
                board.moveMonster(Direction.RIGHT); // (2,2) → (2,4)// → CRIA BOLA PEQUENA
                // 6. Verificação intermediária (bola criada em (2,3))
                assertEquals(PositionContent.SNOWBALL, board.getBoard().get(2).get(3),
                        "Deveria ter criado uma bola de neve em (2,3)");
                assertEquals(SnowballType.SMALL, board.getSnowballTypeAt(new Position(2, 3)),
                        "A bola criada deveria ser pequena (SMALL)");
                // 7. Movimentos adicionais (como você especificou)
                board.moveMonster(Direction.DOWN);  // (2,4) → (3,4)
                board.moveMonster(Direction.LEFT);  // (3,4) → (3,3)
                board.moveMonster(Direction.LEFT);  // (3,3) → (3,2)
                board.moveMonster(Direction.UP);    // (3,2) → (2,2)
                // 8. Movimentos para a direita até (2,5) - TRANSFORMA BOLA EM MÉDIA
                board.moveMonster(Direction.RIGHT); // (2,2) → (2,3) → EMPURRA BOLA PARA (2,4)
                board.moveMonster(Direction.RIGHT);// (2,3) → (2,4) → EMPURRA BOLA PARA (2,5) → VIRA MÉDIA
                board.moveMonster(Direction.RIGHT);
                // 9. Verificações finais
                assertAll(
                        () -> assertEquals(PositionContent.MONSTER, board.getBoard().get(2).get(5),
                                "O monstro deveria estar em (2,5) após empurrar a bola"),
                        () -> assertEquals(PositionContent.SNOWBALL, board.getBoard().get(2).get(6),
                                "A bola deveria estar em (2,6) após ser empurrada"),
                        () -> assertEquals(SnowballType.AVERAGE, board.getSnowballTypeAt(new Position(2, 6)),
                                "A bola deveria ser média (AVERAGE) após passar pela neve em (2,6)")
                );


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