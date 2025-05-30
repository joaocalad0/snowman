package pt.ipbeja.estig.po2.snowman.gui.app.model;

public class Snowball {

    private int row;
    private int col;
    private SnowballType type;


    public Snowball(int row, int col, SnowballType type){
        this.row = row;
        this.col = col;
        this.type = type;

    }

    public int getRow(){return row;}
    public int getCol(){return col;}
    public SnowballType getType(){return type;}


    public void setRow(int row) {this.row = row;}
    public void setCol(int col) {this.col = col;}
    public void setType(SnowballType type) {this.type = type;}

    }

