package nl.ulso.sprox.json.complex;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Container {

    private final String string;
    private final List<Item> items;

    public Container(String string, List<Item> items) {
        this.string = string;
        this.items = unmodifiableList(items);
    }

    public String getString() {
        return string;
    }

    public List<Item> getItems() {
        return items;
    }
}
