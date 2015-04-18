/*
 * Copyright 2015 Vincent Oostindië
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package nl.ulso.sprox.json;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonParser;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Queue;

import static nl.ulso.sprox.json.JsonXmlConstants.NULL_VALUE;

class JsonXmlEventReader implements XMLEventReader {

    private final JsonParser parser;
    private final NameStack nameStack;
    private final Queue<XMLEvent> valueQueue;

    public JsonXmlEventReader(InputStream inputStream, String rootNodeName, int maximumStackDepth) {
        this(Json.createParser(new BufferedInputStream(inputStream)), rootNodeName, maximumStackDepth);
    }

    public JsonXmlEventReader(Reader reader, String rootNodeName, int maximumStackDepth) {
        this(Json.createParser(new BufferedReader(reader)), rootNodeName, maximumStackDepth);
    }

    private JsonXmlEventReader(JsonParser parser, String rootNodeName, int maximumStackDepth) {
        this.parser = parser;
        this.nameStack = new NameStack(rootNodeName, maximumStackDepth);
        this.valueQueue = new ArrayDeque<>(3);
    }

    @Override
    public boolean hasNext() {
        return !valueQueue.isEmpty() || parser.hasNext();
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException {
        if (!valueQueue.isEmpty()) {
            return valueQueue.remove();
        }
        final JsonParser.Event next;
        try {
            next = parser.next();
        } catch (JsonException e) {
            throw new XMLStreamException(e);
        }
        return convertEvent(next);
    }

    private XMLEvent convertEvent(JsonParser.Event event) throws XMLStreamException {
        switch (event) {
            case START_OBJECT:
                return new StartObjectEvent(nameStack.peek());
            case END_OBJECT:
                return new EndObjectEvent(nameStack.conditionalPop());
            case KEY_NAME:
                nameStack.push(parser.getString());
                break;
            case START_ARRAY:
                nameStack.markAsArray();
                break;
            case END_ARRAY:
                nameStack.forcePop();
                break;
            case VALUE_FALSE:
                populateValueStack("false");
                break;
            case VALUE_TRUE:
                populateValueStack("true");
                break;
            case VALUE_NULL:
                populateValueStack(NULL_VALUE);
                break;
            case VALUE_NUMBER:
                populateValueStack(parser.getBigDecimal().toString());
                break;
            case VALUE_STRING:
                populateValueStack(parser.getString());
                break;
            default:
                throw new XMLStreamException("Unsupported JSON event: " + event);
        }
        return nextEvent();
    }

    private void populateValueStack(String value) {
        final String name = nameStack.conditionalPop();
        valueQueue.add(new StartObjectEvent(name));
        valueQueue.add(new ValueEvent(value));
        valueQueue.add(new EndObjectEvent(name));
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
    public Object getProperty(String name) {
        throw new UnsupportedOperationException();
    }
}
