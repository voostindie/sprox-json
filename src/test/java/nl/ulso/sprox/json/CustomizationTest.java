package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import nl.ulso.sprox.XmlProcessorException;
import org.junit.Test;

import static nl.ulso.sprox.json.ContainerTest.NestedObjectController;
import static nl.ulso.sprox.json.SproxJsonTests.processString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomizationTest {

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

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCustomMaximumDepth() throws Exception {
        new JsonXmlInputFactory(0);
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

    public static final class ObjectWithIntegerAndCustomRootNodeNameController {
        @Node
        public Integer custom(@Node int value) {
            return value;
        }
    }
}
