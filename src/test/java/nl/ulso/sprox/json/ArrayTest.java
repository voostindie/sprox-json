package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.junit.Assert.assertEquals;

public class ArrayTest {
    @Test
    public void testEmptyArray() throws Exception {
        final String json = "{ \"list\" : [] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertEquals("[]", value);
    }

    @Test
    public void testArrayWithOneElement() throws Exception {
        final String json = "{ \"list\" : [ \"one\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertEquals("[one]", value);
    }

    @Test
    public void testArrayWithMultipleElements() throws Exception {
        final String json = "{ \"list\" : [ \"one\", \"two\", \"three\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertEquals("[one,two,three]", value);
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
