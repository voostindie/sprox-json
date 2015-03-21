package nl.ulso.sprox.json;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.Writer;


abstract class JsonEvent implements XMLEvent {

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public boolean isStartElement() {
        return false;
    }

    @Override
    public boolean isEndElement() {
        return false;
    }

    @Override
    public boolean isCharacters() {
        return false;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public boolean isNamespace() {
        return false;
    }

    @Override
    public boolean isEntityReference() {
        return false;
    }

    @Override
    public boolean isProcessingInstruction() {
        return false;
    }

    @Override
    public boolean isStartDocument() {
        return false;
    }

    @Override
    public boolean isEndDocument() {
        return false;
    }

    @Override
    public QName getSchemaType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StartElement asStartElement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EndElement asEndElement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Characters asCharacters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }
}
