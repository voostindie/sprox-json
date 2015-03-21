package nl.ulso.sprox.json;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import java.util.Iterator;

/**
 *
 */
public class EndJsonObjectEntryEvent extends JsonEvent implements EndElement {
    private final QName name;

    public EndJsonObjectEntryEvent(String name) {
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
