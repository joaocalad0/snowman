package pt.ipbeja.estig.po2.snowman.gui.app.model;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {

    private List<List<PositionContent>> board;


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
    }

    private void level1() {
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

    public void updateCell(Position position, PositionContent content){
        board.get(position.getRow()).set(position.getCol(), content);
    }

}
