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

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayTest {
    @Test
    public void testEmptyArray() throws Exception {
        final String json = "{ \"list\" : [] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[]"));
    }

    @Test
    public void testArrayWithOneElement() throws Exception {
        final String json = "{ \"list\" : [ \"one\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[one]"));
    }

    @Test
    public void testArrayWithMultipleElements() throws Exception {
        final String json = "{ \"list\" : [ \"one\", \"two\", \"three\" ] }";
        final String value = processJsonString(json, String.class, ArrayOfStringsController.class);
        assertThat(value, is("[one,two,three]"));
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
