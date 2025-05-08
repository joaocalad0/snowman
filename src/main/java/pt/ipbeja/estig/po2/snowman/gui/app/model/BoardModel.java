package pt.ipbeja.estig.po2.snowman.gui.app.model;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {

    private List<List<PositionContent>> board;
    private Monster monster;


    public BoardModel(int rows, int cols) {
        board = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++)  {
             List<PositionContent>  row = new ArrayList<>(cols);
        for (int j = 0; j < cols; j++){
            row.add(PositionContent.NO_SNOW);
        }
        this.board.add(row);
      }
        level1();
        this.monster = new Monster(new Position(0, 0));
    }

    private void level1() {
        board.get(0).set(0, PositionContent.SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);
    }

    private void level2() {
        board.get(0).set(0, PositionContent.SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);
    }

    public List<List<PositionContent>> getBoard() {
        return board;
    }

    public Monster getMonster() {
        return monster;
    }

    public void updateCell(Position position, PositionContent content){
        board.get(position.getRow()).set(position.getCol(), content);
    }


    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position newPos = calculateNewPosition(currentPos, direction);
        if (isValidMove(newPos)) {
            monster.setPosition(newPos);
            updateBoard(newPos);
        }
    }

    private Position calculateNewPosition(Position currentPos, Direction direction) {
        int row = currentPos.getRow();
        int col = currentPos.getCol();

        // Dependendo da direção, calcula a nova posição
        switch (direction) {
            case UP: return new Position(row - 1, col);   // Move para cima
            case DOWN: return new Position(row + 1, col); // Move para baixo
            case LEFT: return new Position(row, col - 1); // Move para a esquerda
            case RIGHT: return new Position(row, col + 1); // Move para a direita
        }

        return currentPos;  // Caso algo falhe, retorna a posição atual
    }

    private boolean isValidMove(Position position) {
        return position.getRow() >= 0 && position.getRow() < board.size()
                && position.getCol() >= 0 && position.getCol() < board.get(0).size();
    }

    private void updateBoard(Position newPos) {
        // Atualiza a célula no tabuleiro onde o monstro se moveu
        updateCell(newPos, PositionContent.SNOWMAN);
    }



}
