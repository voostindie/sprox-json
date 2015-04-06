package nl.ulso.sprox.json;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Stack;

class JsonXmlEventReader implements XMLEventReader {

    private final JsonParser parser;
    private final Stack<String> names;
    private boolean isStart;
    private boolean isValue;

    public JsonXmlEventReader(InputStream inputStream, String rootNodeName) {
        this.parser = Json.createParser(new BufferedInputStream(inputStream));
        this.names = createNameStack(rootNodeName);
        this.isStart = true;
        this.isValue = false;
    }

    JsonXmlEventReader(Reader reader, String rootNodeName) {
        this.parser = Json.createParser(new BufferedReader(reader));
        this.names = createNameStack(rootNodeName);
        this.isStart = true;
        this.isValue = false;
    }

    private Stack<String> createNameStack(String rootNodeName) {
        final Stack<String> stack = new Stack<>();
        stack.push(rootNodeName);
        return stack;
    }

    @Override
    public boolean hasNext() {
        return parser.hasNext() || !names.empty();
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        final XMLEvent event = nextEventInternal();
        System.out.println("                                        " + event.toString());
        return event;
    }

    private XMLEvent nextEventInternal() {
        if (this.isStart) {
            // Insert a dummy start node for the root node, at the top of the stack.
            this.isStart = false;
            return new StartJsonObjectEvent(names.peek());
        }
        if (this.isValue) {
            // The previous event was a value. Now insert a end node
            this.isValue = false;
            return new EndJsonObjectEvent(names.pop());
        }
        if (!parser.hasNext()) {
            // Insert a dummy end node for the root node, at the top of the stack.
            return new EndJsonObjectEvent(names.pop());
        }
        return convertEvent(parser.next());
    }

    private XMLEvent convertEvent(JsonParser.Event event) {
        switch (event) {
            case KEY_NAME:
                final String name = parser.getString();
                System.out.println("KEY_NAME = " + name);
                names.push(name);
                return new StartJsonObjectEvent(name);
            case START_OBJECT:
                System.out.println("START_OBJECT");
                return nextEventInternal();
            case END_OBJECT:
                System.out.println("END_OBJECT");
                return nextEventInternal();
            case START_ARRAY:
                System.out.println("START_ARRAY");
                return nextEventInternal();
            case END_ARRAY:
                System.out.println("END_ARRAY");
                return nextEventInternal();
            case VALUE_FALSE:
                System.out.println("VALUE = false");
                this.isValue = true;
                return new JsonCharactersEvent("false");
            case VALUE_TRUE:
                System.out.println("VALUE = true");
                this.isValue = true;
                return new JsonCharactersEvent("true");
            case VALUE_NULL:
                System.out.println("VALUE = null");
                this.isValue = true;
                return new JsonCharactersEvent("null");
            case VALUE_NUMBER:
                System.out.println("VALUE = " + parser.getBigDecimal().toString());
                this.isValue = true;
                return new JsonCharactersEvent(parser.getBigDecimal().toString());
            case VALUE_STRING:
                System.out.println("VALUE = " + parser.getString());
                this.isValue = true;
                return new JsonCharactersEvent(parser.getString());
            default:
                throw new IllegalStateException("Unsupported Json event: " + event);
        }
    }

    @Override
    public XMLEvent peek() throws XMLStreamException {
        throw new UnsupportedOperationException();
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
}
