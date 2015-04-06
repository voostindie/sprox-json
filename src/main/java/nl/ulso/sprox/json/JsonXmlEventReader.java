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
    private final Stack<String> nameStack;
    private final Stack<XMLEvent> valueStack;

    public JsonXmlEventReader(InputStream inputStream, String rootNodeName) {
        this.parser = Json.createParser(new BufferedInputStream(inputStream));
        this.nameStack = createNameStack(rootNodeName);
        this.valueStack = new Stack<>();
    }

    JsonXmlEventReader(Reader reader, String rootNodeName) {
        this.parser = Json.createParser(new BufferedReader(reader));
        this.nameStack = createNameStack(rootNodeName);
        this.valueStack = new Stack<>();
    }

    private Stack<String> createNameStack(String rootNodeName) {
        final Stack<String> stack = new Stack<>();
        stack.push(rootNodeName);
        return stack;
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
            // The JSON parser is done. So the only reason we get here is that there are names on the stack still:
            return new EndJsonObjectEvent(nameStack.pop());
        }
        return convertEvent(parser.next());
    }

    private XMLEvent convertEvent(JsonParser.Event event) {
        switch (event) {
            case KEY_NAME:
                final String name = parser.getString();
                System.out.println("KEY_NAME = " + name);
                nameStack.push(name);
                return nextEventInternal();
            case START_OBJECT:
                System.out.println("START_OBJECT");
                return new StartJsonObjectEvent(nameStack.peek());
            case END_OBJECT:
                System.out.println("END_OBJECT");
                return new EndJsonObjectEvent(nameStack.pop());
            case START_ARRAY:
                System.out.println("START_ARRAY");
                nameStack.push(nameStack.peek());
                return nextEventInternal();
            case END_ARRAY:
                System.out.println("END_ARRAY");
                nameStack.pop();
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

    private void populateValueStack(String data) {
        valueStack.push(new EndJsonObjectEvent(nameStack.peek()));
        valueStack.push(new JsonCharactersEvent(data));
        valueStack.push(new StartJsonObjectEvent(nameStack.pop()));
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
