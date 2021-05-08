package nl.ulso.sprox.json;

import nl.ulso.sprox.Node;
import org.junit.Test;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.junit.Assert.assertEquals;

public class PrimitiveValuesTest {

    @Test
    public void testObjectWithSingleIntegerValueAsString() throws Exception {
        final int value = processJsonString(
                "{\n" +
                        "  \"value\": \"42\"\n" +
                        "}",
                Integer.class,
                IntegerValueController.class);
        assertEquals(42, value);
    }

    @Test
    public void testObjectWithSingleIntegerValueAsNumber() throws Exception {
        final int value = processJsonString(
                "{\n  \"value\": 42\n}\n",
                Integer.class,
                IntegerValueController.class);
        assertEquals(42, value);
    }

    @Test
    public void testObjectWithComplexDouble() throws Exception {
        final double value = processJsonString(
                "{\n" +
                        "  \"value\": -1234.56789e-4\n" +
                        "}",
                Double.class,
                DoubleValueController.class);
        assertEquals(-0.123456789, value, 0);
    }

    public static final class IntegerValueController {
        @Node
        public Integer root(@Node Integer value) {
            return value;
        }
    }

    public static final class DoubleValueController {
        @Node
        public Double root(@Node double value) {
            return value;
        }
    }

}
