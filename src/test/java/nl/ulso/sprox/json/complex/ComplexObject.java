package nl.ulso.sprox.json.complex;

public class ComplexObject {

    private final long id;
    private final Container container;

    public ComplexObject(long id, Container container) {
        this.id = id;
        this.container = container;
    }

    public long getId() {
        return id;
    }

    public Container getContainer() {
        return container;
    }
}
