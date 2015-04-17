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

import nl.ulso.sprox.Node;
import nl.ulso.sprox.XmlProcessorBuilder;
import nl.ulso.sprox.XmlProcessorException;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;
import org.junit.Test;

import javax.xml.stream.XMLInputFactory;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static nl.ulso.sprox.json.JsonValueParserWrapper.addDefaultJsonParsers;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class JsonProcessingTest {

    @Test
    public void testEmptyObjectIsNotNull() throws Exception {
        final Object result = processJsonString(
                "{}",
                Object.class,
                EmptyObjectController.class);
        assertThat(result, notNullValue());
    }

    @Test
    public void testObjectWithSingleIntegerValueAsString() throws Exception {
        final int value = processJsonString(
                "{\n" +
                        "  \"value\": \"42\"\n" +
                        "}",
                Integer.class,
                ObjectWithIntegerController.class);
        assertThat(value, is(42));
    }

    @Test
    public void testObjectWithSingleIntegerValueAsNumber() throws Exception {
        final int value = processJsonString(
                "{\n  \"value\": 42\n}\n",
                Integer.class,
                ObjectWithIntegerController.class);
        assertThat(value, is(42));
    }

    @Test
    public void testObjectWithComplexDouble() throws Exception {
        final double value = processJsonString(
                "{\n" +
                        "  \"value\": -1234.56789e-4\n" +
                        "}",
                Double.class,
                ObjectWithDoubleController.class);
        assertThat(value, is(-0.123456789));
    }

    @Test
    public void testObjectWithMultipleValues() throws Exception {
        final String value = processJsonString(
                "{\n" +
                        "  \"foo\": \"bar\",\n" +
                        "  \"int\" : 42\n" +
                        "}",
                String.class,
                ObjectWithMultipleValuesController.class);
        assertThat(value, is("bar,42"));
    }

    @Test
    public void testNestedObjects() throws Exception {
        final String value = processJsonString(
                "{ \"o1\" : { \"int\": 1 }, \"o2\" : { \"string\": \"2\" } }",
                String.class,
                NestedObjectController.class);
        assertThat(value, is("1,2"));
    }

    @Test
    public void testNestedObjectsXmlVersion() throws Exception {
        final String xml = "<root><o1><int>1</int></o1><o2><string>2</string></o2></root>";
        final String value = processXmlString(xml, String.class, NestedObjectController.class);
        assertThat(value, is("1,2"));
    }

    @Test
    public void testEmptyArray() throws Exception {
        final String json = "{ \"list\" : [] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[]"));
    }

    @Test
    public void testArrayWithOneElement() throws Exception {
        final String json = "{ \"list\" : [ \"one\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[one]"));
    }

    @Test
    public void testArrayWithMultipleElements() throws Exception {
        final String json = "{ \"list\" : [ \"one\", \"two\", \"three\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[one,two,three]"));
    }

    @Test
    public void testLiteralValues() throws Exception {
        final String json = "{ \"foo\" : null, \"bar\" : false, \"baz\" : true }";
        final String value = processJsonString(json, String.class, LiteralValuesController.class);
        assertThat(value, is("NULL|false|true"));
    }

    @Test
    public void testComplexObject() throws Exception {
        final String json = "{\n" +
                "  \"id\": 42,\n" +
                "  \"object\": {\n" +
                "    \"string\": \"Value\",\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Item 1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Item 2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Item 3\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        final RootObject object = processJsonString(json, RootObject.class, RootObjectController.class);
        assertThat(object, notNullValue());
        assertThat(object.getNestedObject(), notNullValue());
        assertThat(object.getNestedObject().getItems().size(), is(3));
    }

    @Test
    public void testCustomRootNodeName() throws Exception {
        final int value = processString(
                "{\n" +
                        "  \"value\": \"42\"\n" +
                        "}",
                Integer.class,
                ObjectWithIntegerAndCustomRootNodeNameController.class,
                new JsonXmlInputFactory("custom"));
        assertThat(value, is(42));
    }

    @Test(expected = XmlProcessorException.class)
    public void testCustomMaximumDepth() throws Exception {
        processString(
                "{ \"o1\" : { \"int\": 1 }, \"o2\" : { \"string\": \"2\" } }",
                String.class,
                NestedObjectController.class,
                new JsonXmlInputFactory(1));
    }

    @Test
    public void testCustomRootNodeNameAndCustomMaximumDepth() throws Exception {
        final int value = processString(
                "{\n" +
                        "  \"value\": \"42\"\n" +
                        "}",
                Integer.class,
                ObjectWithIntegerAndCustomRootNodeNameController.class,
                new JsonXmlInputFactory("custom", 2));
        assertThat(value, is(42));
    }

    @Test(expected = XmlProcessorException.class)
    public void testInvalidJson() throws Exception {
        processJsonString("{ value : 42 }", Integer.class, ObjectWithIntegerController.class);
    }


    private <T> T processString(String string, Class<T> resultClass, Class controllerClass, XMLInputFactory factory)
            throws XmlProcessorException {
        final XmlProcessorBuilder<T> builder = new StaxBasedXmlProcessorBuilderFactory()
                .createXmlProcessorBuilder(resultClass)
                .setXmlInputFactory(factory)
                .addControllerClass(controllerClass);
        addDefaultJsonParsers(builder);
        return builder.buildXmlProcessor()
                .execute(new StringReader(string));
    }


    private <T> T processJsonString(String json, Class<T> resultClass, Class controllerClass)
            throws XmlProcessorException {
        return processString(json, resultClass, controllerClass, new JsonXmlInputFactory());
    }

    private <T> T processXmlString(String xml, Class<T> resultClass, Class controllerClass)
            throws XmlProcessorException {
        return processString(xml, resultClass, controllerClass, XMLInputFactory.newFactory());
    }

    public static final class EmptyObjectController {
        @Node
        public Object root() {
            return new Object();
        }
    }

    public static final class ObjectWithIntegerController {
        @Node
        public Integer root(@Node int value) {
            return value;
        }
    }

    public static final class ObjectWithIntegerAndCustomRootNodeNameController {
        @Node
        public Integer custom(@Node int value) {
            return value;
        }
    }

    public static final class ObjectWithDoubleController {
        @Node
        public Double root(@Node double value) {
            return value;
        }
    }

    public static final class LiteralValuesController {
        @Node
        public String root(@Node Optional<String> foo, @Node boolean bar, @Node boolean baz) {
            return foo.orElse("NULL") + "|" + bar + "|" + baz;
        }
    }

    public static final class ObjectWithMultipleValuesController {
        @Node
        public String root(@Node String foo, @Node("int") int value) {
            return new StringJoiner(",").add(foo).add(Integer.toString(value)).toString();
        }
    }

    public static final class NestedObjectController {
        @Node
        public String root(Integer one, String two) {
            return new StringJoiner(",").add(Integer.toString(one)).add(two).toString();
        }

        @Node
        public Integer o1(@Node("int") int value) {
            return value;
        }

        @Node
        public String o2(@Node("string") String value) {
            return value;
        }
    }

    public static final class ArrayOfStringsController {
        @Node
        public String root(Optional<List<String>> items) {
            final StringJoiner joiner = new StringJoiner(",", "[", "]");
            items.ifPresent(list -> list.forEach(joiner::add));
            return joiner.toString();
        }

        @Node
        public String list(@Node("list") String value) {
            return value;
        }
    }
}
