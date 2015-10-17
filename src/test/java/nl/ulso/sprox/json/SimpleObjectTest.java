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
