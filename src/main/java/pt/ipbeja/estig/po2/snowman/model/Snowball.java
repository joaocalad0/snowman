package pt.ipbeja.estig.po2.snowman.model;

public class Snowball extends MobileElement {
    private SnowballType type;

    public Snowball(Position position, SnowballType type) {
        super(position);
        this.type = type;
    }

    public SnowballType getType() {
        return type;
    }

    public void setType(SnowballType type) {
        this.type = type;
    }
}