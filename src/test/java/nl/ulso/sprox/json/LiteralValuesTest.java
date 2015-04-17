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

import java.util.Optional;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LiteralValuesTest {

    @Test
    public void testLiteralValues() throws Exception {
        final String json = "{ \"foo\" : null, \"bar\" : false, \"baz\" : true }";
        final String value = processJsonString(json, String.class, LiteralValuesController.class);
        assertThat(value, is("NULL|false|true"));
    }

    public static final class LiteralValuesController {
        @Node
        public String root(@Node Optional<String> foo, @Node boolean bar, @Node boolean baz) {
            return foo.orElse("NULL") + "|" + bar + "|" + baz;
        }
    }
}
