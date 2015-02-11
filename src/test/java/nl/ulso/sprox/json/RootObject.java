package nl.ulso.sprox.json;

/**
 *
 */
public class RootObject {

    private final long id;
    private final NestedObject nestedObject;

    public RootObject(long id, NestedObject nestedObject) {
        this.id = id;
        this.nestedObject = nestedObject;
    }

    public long getId() {
        return id;
    }

    public NestedObject getNestedObject() {
        return nestedObject;
    }
}
