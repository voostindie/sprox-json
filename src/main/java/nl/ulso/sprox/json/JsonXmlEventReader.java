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
    private final Stack<Name> nameStack;
    private final Stack<XMLEvent> valueStack;

    public JsonXmlEventReader(InputStream inputStream, String rootNodeName) {
        this(Json.createParser(new BufferedInputStream(inputStream)), rootNodeName);
    }

    public JsonXmlEventReader(Reader reader, String rootNodeName) {
        this(Json.createParser(new BufferedReader(reader)), rootNodeName);
    }

    private JsonXmlEventReader(JsonParser parser, String rootNodeName) {
        this.parser = parser;
        this.nameStack = new Stack<>();
        this.valueStack = new Stack<>();
        pushName(rootNodeName);
    }

    @Override
    public boolean hasNext() {
        return !valueStack.empty() || !nameStack.empty() || parser.hasNext();
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        final XMLEvent event = nextEventInternal();
        System.out.println("                                        " + event.toString() + " (" + nameStack.size() + ")");
        return event;
    }

    private XMLEvent nextEventInternal() {
        if (!valueStack.empty()) {
            return valueStack.pop();
        }
        if (!parser.hasNext()) {
            return new EndObjectEvent(peekOrPopName());
        }
        return convertEvent(parser.next());
    }

    private XMLEvent convertEvent(JsonParser.Event event) {
        switch (event) {
            case KEY_NAME:
                final String name = parser.getString();
                System.out.println("KEY_NAME = " + name);
                pushName(name);
                return nextEventInternal();
            case START_OBJECT:
                System.out.println("START_OBJECT");
                return new StartObjectEvent(peekName());
            case END_OBJECT:
                System.out.println("END_OBJECT");
                return new EndObjectEvent(peekOrPopName());
            case START_ARRAY:
                System.out.println("START_ARRAY");
                markNameAsArray();
                return nextEventInternal();
            case END_ARRAY:
                System.out.println("END_ARRAY");
                popName();
                return nextEventInternal();
            case VALUE_FALSE:
                System.out.println("VALUE = false");
                populateValueStack("false");
                return nextEventInternal();
            case VALUE_TRUE:
                System.out.println("VALUE = true");
                populateValueStack("true");
                return nextEventInternal();
            case VALUE_NULL:
                System.out.println("VALUE = null");
                populateValueStack(null);
                return nextEventInternal();
            case VALUE_NUMBER:
                System.out.println("VALUE = " + parser.getBigDecimal().toString());
                populateValueStack(parser.getBigDecimal().toString());
                return nextEventInternal();
            case VALUE_STRING:
                System.out.println("VALUE = " + parser.getString());
                populateValueStack(parser.getString());
                return nextEventInternal();
            default:
                throw new IllegalStateException("Unsupported JSON event: " + event);
        }
    }

    private void populateValueStack(String value) {
        final String name = peekOrPopName();
        valueStack.push(new EndObjectEvent(name));
        valueStack.push(new ValueEvent(value));
        valueStack.push(new StartObjectEvent(name));
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

    private void pushName(String name) {
        this.nameStack.push(new Name(name));
    }

    private String peekName() {
        return nameStack.peek().value;
    }

    private String peekOrPopName() {
        final Name name = nameStack.peek();
        if (!name.isArray) {
            nameStack.pop();
        }
        return name.value;
    }

    private void popName() {
        nameStack.pop();
    }

    private void markNameAsArray() {
        nameStack.peek().isArray = true;
    }

    private static final class Name {
        private final String value;
        private boolean isArray;

        public Name(String name) {
            this.value = name;
        }
    }
}
