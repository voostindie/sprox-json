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

import nl.ulso.sprox.XmlProcessorBuilder;
import nl.ulso.sprox.XmlProcessorException;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;

import javax.xml.stream.XMLInputFactory;
import java.io.StringReader;

import static nl.ulso.sprox.json.JsonValueParserWrapper.addDefaultJsonParsers;

/**
 * Convenience methods for tests.
 */
public class SproxJsonTests {

    private SproxJsonTests() {
    }

    public static <T> T processString(String string, Class<T> resultClass, Class controllerClass, XMLInputFactory factory)
            throws XmlProcessorException {
        final XmlProcessorBuilder<T> builder = new StaxBasedXmlProcessorBuilderFactory()
                .createXmlProcessorBuilder(resultClass)
                .setXmlInputFactory(factory)
                .addControllerClass(controllerClass);
        addDefaultJsonParsers(builder);
        return builder.buildXmlProcessor().execute(new StringReader(string));
    }

    public static <T> T processJsonString(String json, Class<T> resultClass, Class controllerClass)
            throws XmlProcessorException {
        return processString(json, resultClass, controllerClass, new JsonXmlInputFactory());
    }

    public static <T> T processXmlString(String xml, Class<T> resultClass, Class controllerClass)
            throws XmlProcessorException {
        return processString(xml, resultClass, controllerClass, XMLInputFactory.newFactory());
    }
}