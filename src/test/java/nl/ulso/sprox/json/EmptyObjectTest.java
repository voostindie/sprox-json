package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class EmptyObjectTest {

    @Test
    public void testEmptyObjectIsNotNull() throws Exception {
        final Object result = processJsonString(
                "{}",
                Object.class,
                EmptyObjectController.class);
        assertThat(result, notNullValue());
    }

    public static final class EmptyObjectController {
        @Node
        public Object root() {
            return new Object();
        }
    }
}
