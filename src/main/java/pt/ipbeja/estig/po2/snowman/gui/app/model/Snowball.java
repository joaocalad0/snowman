package pt.ipbeja.estig.po2.snowman.gui.app.model;

public class Snowball {

    private PositionContent content;
    private SnowballType snowballType;

    public Snowball(PositionContent content) {
        this.content = content;
        this.snowballType = null;
    }

    public Snowball(PositionContent content, SnowballType snowballType) {
        this.content = content;
        this.snowballType = snowballType;
    }

    public PositionContent getContent() {
        return content;
    }

    public void setContent(PositionContent content) {
        this.content = content;
    }

    public SnowballType getSnowballType() {
        return snowballType;
    }

    public void setSnowballType(SnowballType snowballType) {
        this.snowballType = snowballType;
    }
}
