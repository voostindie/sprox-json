package nl.ulso.sprox.json.complex;

public class Item {

    private final int id;
    private final String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }
}
