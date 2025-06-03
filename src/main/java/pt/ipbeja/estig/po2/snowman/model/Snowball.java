package pt.ipbeja.estig.po2.snowman.model;

public class Snowball extends MobileElement {
    private SnowballType type;  // mantém o campo type como estava

    public Snowball(Position position, SnowballType type) {
        super(position);  // chama o construtor da classe pai
        this.type = type;
    }

    public SnowballType getType() {
        return type;
    }

    public void setType(SnowballType type) {
        this.type = type;
    }
}