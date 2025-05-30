package pt.ipbeja.estig.po2.snowman.gui.app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardModelTest {


    @Test
    void testMonsterToTheLeft() {
        // 1. Configuração inicial - tabuleiro 5x5
        BoardModel model = new BoardModel(5, 7);

        // 2. Posiciona o monstro em (1,1)
        Position initialPos = model.getMonster().getPosition();
        model.updateCell(initialPos, PositionContent.NO_SNOW);

        Position startPos = new Position(1, 1);
        model.getMonster().setPosition(startPos);
        model.updateCell(startPos, PositionContent.MONSTER);

        // 3. Verifica posição inicial
        Position currentPos = model.getMonster().getPosition();
        assertEquals(1, currentPos.getRow(), "Linha inicial incorreta");
        assertEquals(1, currentPos.getCol(), "Coluna inicial incorreta");

        // 4. Move para esquerda
        model.moveMonster(Direction.LEFT);

        // 5. Verifica nova posição
        Position newPos = model.getMonster().getPosition();
        assertEquals(1, newPos.getRow(), "Linha após movimento incorreta");
        assertEquals(0, newPos.getCol(), "Coluna após movimento incorreta");

        // 6. Verifica estado do tabuleiro
        assertEquals(PositionContent.MONSTER, model.getBoard().get(1).get(0),
                "Célula (1,0) deveria conter o monstro");
        assertEquals(PositionContent.NO_SNOW, model.getBoard().get(1).get(1),
                "Célula (1,1) deveria estar vazia");
    }
}


