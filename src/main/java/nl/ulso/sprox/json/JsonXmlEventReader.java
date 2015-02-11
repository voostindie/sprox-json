package nl.ulso.sprox.json;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 */
public class JsonXmlEventReader implements XMLEventReader {
    public JsonXmlEventReader(Reader reader, String rootNodeName) {
    }

    public JsonXmlEventReader(InputStream stream, String rootNodeName) {

    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public XMLEvent peek() throws XMLStreamException {
        return null;
    }

    @Override
    public String getElementText() throws XMLStreamException {
        return null;
    }

    @Override
    public XMLEvent nextTag() throws XMLStreamException {
        return null;
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void close() throws XMLStreamException {

    }

    @Override
    public Object next() {
        return null;
    }
}
