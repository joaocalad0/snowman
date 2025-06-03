package pt.ipbeja.estig.po2.snowman.model;

public interface View {

      void update(Position position, PositionContent content);

      void onGameWon(PositionContent positionContent);

      void updateAllBoard();
}



