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

import javax.xml.stream.*;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;
import java.io.InputStream;
import java.io.Reader;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link XMLInputFactory} that creates {@link XMLEventReader}s for JSON data. Only the methods used
 * by Sprox - that's two on a total of twenty four! - are implemented; all other methods throw an
 * {@link UnsupportedOperationException}.
 */
public class JsonXmlInputFactory extends XMLInputFactory {

    private static final int DEFAULT_MAXIMUM_STACK_DEPTH = 64;
    private static final String DEFAULT_ROOT_NODE_NAME = "root";

    private final String rootNodeName;
    private final int maximumStackDepth;

    public JsonXmlInputFactory() {
        this(DEFAULT_ROOT_NODE_NAME, DEFAULT_MAXIMUM_STACK_DEPTH);
    }

    public JsonXmlInputFactory(String rootNodeName) {
        this(rootNodeName, DEFAULT_MAXIMUM_STACK_DEPTH);
    }

    public JsonXmlInputFactory(int maximumStackDepth) {
        this(DEFAULT_ROOT_NODE_NAME, maximumStackDepth);
    }

    public JsonXmlInputFactory(String rootNodeName, int maximumStackDepth) {
        this.rootNodeName = requireNonNull(rootNodeName);
        if (maximumStackDepth < 1) {
            throw new IllegalArgumentException("Minimum stack depth required is 1");
        }
        this.maximumStackDepth = maximumStackDepth;
    }

    @Override
    public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
        return new JsonXmlEventReader(reader, rootNodeName, maximumStackDepth);
    }

    @Override
    public XMLEventReader createXMLEventReader(InputStream stream) throws XMLStreamException {
        return new JsonXmlEventReader(stream, rootNodeName, maximumStackDepth);
    }


    @Override
    public XMLStreamReader createXMLStreamReader(Reader reader) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createXMLStreamReader(InputStream stream) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createXMLStreamReader(InputStream stream, String encoding) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createXMLStreamReader(String systemId, InputStream stream) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createXMLStreamReader(String systemId, Reader reader) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createXMLEventReader(XMLStreamReader reader) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createXMLEventReader(InputStream stream, String encoding) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createXMLEventReader(String systemId, InputStream stream) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLResolver getXMLResolver() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setXMLResolver(XMLResolver resolver) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLReporter getXMLReporter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setXMLReporter(XMLReporter reporter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProperty(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getProperty(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPropertySupported(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEventAllocator(XMLEventAllocator allocator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMLEventAllocator getEventAllocator() {
        throw new UnsupportedOperationException();
    }
}
