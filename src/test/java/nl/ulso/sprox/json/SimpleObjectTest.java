package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleObjectTest {
    @Test
    public void testObjectWithMultipleValues() throws Exception {
        final String value = processJsonString(
                "{\n" +
                        "  \"foo\": \"bar\",\n" +
                        "  \"int\" : 42\n" +
                        "}",
                String.class,
                SimpleObjectController.class);
        assertThat(value, is("bar,42"));
    }

    public static final class SimpleObjectController {
        @Node
        public String root(@Node String foo, @Node("int") int value) {
            return foo + "," + value;
        }
    }
}
