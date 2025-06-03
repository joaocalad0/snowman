package pt.ipbeja.estig.po2.snowman.model;

public class Monster extends MobileElement {
    private int snowballCount;

    public Monster(Position position) {
        super(position);  // chama o construtor da classe pai
        this.snowballCount = 0;
    }

    public int getSnowballCount() {
        return snowballCount;
    }

    public void incrementSnowballCount() {
        if (snowballCount < 3) {
            snowballCount++;
        }
    }

    public void resetSnowballCount() {
        snowballCount = 0;
    }

    public void pushSnowball(BoardModel board, Position newPos) {
        PositionContent content = board.getBoard().get(newPos.getRow()).get(newPos.getCol());
        if (content == PositionContent.SNOW) {
            incrementSnowballCount();
            board.updateCell(newPos, PositionContent.NO_SNOW);
        }
    }

    public boolean canFormSnowman(BoardModel board) {
        return snowballCount == 3;
    }

    public void createSnowman(BoardModel board, Position newPos) {
        if (canFormSnowman(board)) {
            board.updateCell(newPos, PositionContent.SNOWMAN);
            resetSnowballCount();
        }
    }
}