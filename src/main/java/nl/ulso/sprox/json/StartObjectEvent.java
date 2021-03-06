package nl.ulso.sprox.json;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

/**
 * Adapts a JSON Start Object event to an XML Start Element event.
 */
class StartObjectEvent extends JsonEvent implements StartElement {

    private final QName name;

    public StartObjectEvent(String name) {
        this.name = new QName(name);
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public Iterator getAttributes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator getNamespaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attribute getAttributeByName(QName name) {
        return null;
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return null;
    }

    @Override
    public int getEventType() {
        return START_ELEMENT;
    }

    @Override
    public boolean isStartElement() {
        return true;
    }

    @Override
    public StartElement asStartElement() {
        return this;
    }
}
