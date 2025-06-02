package pt.ipbeja.estig.po2.snowman.model;

public class Monster {

    private Position position;
    private int snowballCount;

    public Monster(Position position) {
        this.position = new Position(0,0);
        this.snowballCount = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

