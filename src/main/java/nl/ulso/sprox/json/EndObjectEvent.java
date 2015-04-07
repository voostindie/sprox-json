package nl.ulso.sprox.json;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import java.util.Iterator;

/**
 * Adapts a JSON End Object event to an XML End Element event.
 */
class EndObjectEvent extends JsonEvent implements EndElement {

    private final QName name;

    public EndObjectEvent(String name) {
        this.name = new QName(name);
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public Iterator getNamespaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getEventType() {
        return END_ELEMENT;
    }

    @Override
    public boolean isEndElement() {
        return true;
    }

    @Override
    public EndElement asEndElement() {
        return this;
    }

    @Override
    public String toString() {
        return "END_ELEMENT: " + name;
    }
}
