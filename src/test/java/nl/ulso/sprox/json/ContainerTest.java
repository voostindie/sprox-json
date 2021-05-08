package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import java.util.StringJoiner;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static nl.ulso.sprox.json.SproxJsonTests.processXmlString;
import static org.junit.Assert.assertEquals;

public class ContainerTest {
    @Test
    public void testNestedObjects() throws Exception {
        final String value = processJsonString(
                "{ \"o1\" : { \"int\": 1 }, \"o2\" : { \"string\": \"2\" } }",
                String.class,
                NestedObjectController.class);
        assertEquals("1,2", value);
    }

    @Test
    public void testNestedObjectsXmlVersion() throws Exception {
        final String xml = "<root><o1><int>1</int></o1><o2><string>2</string></o2></root>";
        final String value = processXmlString(xml, String.class, NestedObjectController.class);
        assertEquals("1,2", value);
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
}
