package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import nl.ulso.sprox.XmlProcessorException;
import org.junit.Test;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;

public class InvalidJsonTest {

    @Test(expected = XmlProcessorException.class)
    public void testInvalidJson() throws Exception {
        processJsonString("{ value : 42 }", Integer.class, DummyController.class);
    }

    public static final class DummyController {
        @Node
        public Integer root(@Node int value) {
            return value;
        }
    }


}
