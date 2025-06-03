package pt.ipbeja.estig.po2.snowman.model;

public abstract class MobileElement {
    private Position position;

    public MobileElement(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRow() {
        return position.getRow();
    }

    public int getCol() {
        return position.getCol();
    }


}
