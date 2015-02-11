package nl.ulso.sprox.json;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class NestedObject {

    private final String string;
    private final List<Item> items;

    public NestedObject(String string, List<Item> items) {
        this.string = string;
        this.items = Collections.unmodifiableList(items);
    }

    public String getString() {
        return string;
    }

    public List<Item> getItems() {
        return items;
    }
}
