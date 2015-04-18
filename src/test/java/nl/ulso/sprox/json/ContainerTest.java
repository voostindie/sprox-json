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

import java.util.StringJoiner;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static nl.ulso.sprox.json.SproxJsonTests.processXmlString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContainerTest {
    @Test
    public void testNestedObjects() throws Exception {
        final String value = processJsonString(
                "{ \"o1\" : { \"int\": 1 }, \"o2\" : { \"string\": \"2\" } }",
                String.class,
                NestedObjectController.class);
        assertThat(value, is("1,2"));
    }

    @Test
    public void testNestedObjectsXmlVersion() throws Exception {
        final String xml = "<root><o1><int>1</int></o1><o2><string>2</string></o2></root>";
        final String value = processXmlString(xml, String.class, NestedObjectController.class);
        assertThat(value, is("1,2"));
    }

    public static final class NestedObjectController {
        @Node
        public String root(Integer one, String two) {
            return new StringJoiner(",").add(Integer.toString(one)).add(two).toString();
        }

        @Node
        public Integer o1(@Node("int") int value) {
            return value;
        }

        @Node
        public String o2(@Node("string") String value) {
            return value;
        }
    }
}
