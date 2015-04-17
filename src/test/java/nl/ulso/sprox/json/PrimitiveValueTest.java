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
import org.junit.Test;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrimitiveValueTest {

    @Test
    public void testObjectWithSingleIntegerValueAsString() throws Exception {
        final int value = processJsonString(
                "{\n" +
                        "  \"value\": \"42\"\n" +
                        "}",
                Integer.class,
                IntegerValueController.class);
        assertThat(value, is(42));
    }

    @Test
    public void testObjectWithSingleIntegerValueAsNumber() throws Exception {
        final int value = processJsonString(
                "{\n  \"value\": 42\n}\n",
                Integer.class,
                IntegerValueController.class);
        assertThat(value, is(42));
    }

    @Test
    public void testObjectWithComplexDouble() throws Exception {
        final double value = processJsonString(
                "{\n" +
                        "  \"value\": -1234.56789e-4\n" +
                        "}",
                Double.class,
                DoubleValueController.class);
        assertThat(value, is(-0.123456789));
    }

    public static final class IntegerValueController {
        @Node
        public Integer root(@Node int value) {
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
