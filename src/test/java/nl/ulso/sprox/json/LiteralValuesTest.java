package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import java.util.Optional;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.junit.Assert.assertEquals;

public class LiteralValuesTest {

    @Test
    public void testLiteralValues() throws Exception {
        final String json = "{ \"foo\" : null, \"bar\" : false, \"baz\" : true }";
        final String value = processJsonString(json, String.class, LiteralValuesController.class);
        assertEquals("NULL|false|true", value);
    }

    public static final class LiteralValuesController {
        @Node
        public String root(@Node Optional<String> foo, @Node boolean bar, @Node boolean baz) {
            return foo.orElse("NULL") + "|" + bar + "|" + baz;
        }
    }
}
