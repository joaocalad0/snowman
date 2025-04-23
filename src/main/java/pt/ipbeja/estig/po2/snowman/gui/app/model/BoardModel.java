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
    }

    public List<List<PositionContent>> getBoard() {
        return board;
    }


}
