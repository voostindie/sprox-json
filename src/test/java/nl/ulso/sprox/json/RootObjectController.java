package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;

import java.util.List;

/**
 *
 */
public class RootObjectController {

    @Node
    public RootObject root(@Node long id, NestedObject object) {
        return new RootObject(id, object);
    }

    @Node
    public NestedObject object(@Node String string, List<Item> items) {
        return new NestedObject(string, items);
    }

    @Node
    public Item list(@Node int id, @Node String name) {
        return new Item(id, name);
    }

}
