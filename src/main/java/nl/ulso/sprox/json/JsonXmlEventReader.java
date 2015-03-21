package nl.ulso.sprox.json;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.Stack;

class JsonXmlEventReader implements XMLEventReader {

    private final Reader reader;
    private final JSONParser parser;
    private final JsonContentHandler handler;

    public JsonXmlEventReader(InputStream inputStream, String rootNodeName) {
        this(new InputStreamReader(inputStream), rootNodeName);
    }

    JsonXmlEventReader(Reader reader, String rootNodeName) {
        this.reader = new BufferedReader(reader);
        this.parser = new JSONParser();
        this.handler = new JsonContentHandler(rootNodeName);
    }

    @Override
    public boolean hasNext() {
        return handler.hasNext;
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        try {
            if (handler.currentEvent instanceof JsonCharactersEvent) {
                // We need to inject an additional event here by hand, because JSONParser emits 1 OR 2 at a time,
                // while we always want 1 at a time. See line 428 to 431 in JSONParser.java (version 1.1.1).
                handler.endObjectEntry();
            } else {
                parser.parse(reader, handler, true);
            }
        } catch (IOException | ParseException e) {
            throw new XMLStreamException(e);
        }
        return handler.currentEvent;
    }

    @Override
    public XMLEvent peek() throws XMLStreamException {
        return handler.currentEvent;
    }

    @Override
    public void close() throws XMLStreamException {
    }

    @Override
    public Object next() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getElementText() throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEvent nextTag() throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    private static class JsonContentHandler implements ContentHandler {

        private final String rootNodeName;
        private final Stack<String> names;
        private boolean hasNext;
        private JsonEvent currentEvent;

        public JsonContentHandler(String rootNodeName) {
            this.rootNodeName = rootNodeName;
            this.names = new Stack<>();
            this.hasNext = true;
            this.currentEvent = null;
        }

        @Override
        public void startJSON() throws ParseException, IOException {
            names.push(rootNodeName);
            currentEvent = new StartJsonDocumentEvent();
            System.out.println("startJSON");
        }

        @Override
        public void endJSON() throws ParseException, IOException {
            currentEvent = new EndJsonDocumentEvent();
            names.pop();
            hasNext = false;
            System.out.println("endJSON");
        }

        @Override
        public boolean startObject() throws ParseException, IOException {
            if (currentEvent instanceof StartJsonObjectEntryEvent) {
                currentEvent = null;
                return true;
            }
            System.out.println("startObject: " + names.peek());
            currentEvent = new StartJsonObjectEvent(names.peek());
            return false;
        }

        @Override
        public boolean endObject() throws ParseException, IOException {
            if (currentEvent instanceof EndJsonObjectEvent) {
                currentEvent = null;
                return true;
            }
            System.out.println("endObject: " + names.peek());
            currentEvent = new EndJsonObjectEvent(names.peek());
            return false;
        }

        @Override
        public boolean startObjectEntry(String key) throws ParseException, IOException {
            System.out.println("startObjectEntry: " + key);
            currentEvent = new StartJsonObjectEntryEvent(names.push(key));
            return false;
        }

        @Override
        public boolean endObjectEntry() throws ParseException, IOException {
            final String name = names.pop();
            System.out.println("endObjectEntry: " + name);
            currentEvent = new EndJsonObjectEntryEvent(name);
            return false;
        }

        @Override
        public boolean startArray() throws ParseException, IOException {
            System.out.println("startArray");
            return false;
        }

        @Override
        public boolean endArray() throws ParseException, IOException {
            System.out.println("endArray");
            return false;
        }

        @Override
        public boolean primitive(Object value) throws ParseException, IOException {
            System.out.println("primitive: " + value);
            currentEvent = new JsonCharactersEvent(value.toString());
            return false;
        }
    }
}
