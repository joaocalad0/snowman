package pt.ipbeja.estig.po2.snowman.gui.app.model;

public class Model {

    private final String name;

    public Model(String name) {

        this.name = name;
    }

    public String sayHello() {
        return String.format("Hello, %s!", name);
    }
}
