package pt.ipbeja.estig.po2.snowman.model;

/**
 * Interface defining the methods for updating the game view.
 *
 * Classes implementing this interface are responsible for reflecting
 * changes in the game state visually, based on model updates.
 */
public interface View {

      /**
       * Updates the display of a specific position on the board with new content.
       *
       * @param position the position on the board to update
       * @param content the new content to display at the specified position
       */
      void update(Position position, PositionContent content);

      /**
       * Notifies the view that the game has been won.
       *
       * @param positionContent the content related to the winning state,
       *                        possibly indicating which player or element triggered the win
       */
      void onGameWon(PositionContent positionContent);

      /**
       * Refreshes or redraws the entire game board.
       *
       * This method is typically used to synchronize the view with the
       * current complete state of the model.
       */
      void updateAllBoard();

}
