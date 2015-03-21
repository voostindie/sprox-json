package nl.ulso.sprox.json;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import java.util.Iterator;

/**
 *
 */
class EndJsonObjectEvent extends JsonEvent implements EndElement {

    private final QName name;

    public EndJsonObjectEvent(String name) {
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
}
