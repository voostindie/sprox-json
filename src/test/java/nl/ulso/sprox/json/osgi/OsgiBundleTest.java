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

package nl.ulso.sprox.json.osgi;

import nl.ulso.sprox.json.JsonValueParserWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class OsgiBundleTest {

    @Configuration
    public Option[] config() {
        return options(
                bundle("reference:file:target/classes"), // Add the compiler output as a bundle
                mavenBundle("javax.json", "javax.json-api", "1.0"),
                mavenBundle("nl.ulso.sprox", "sprox", "3.1.3"),
                junitBundles()
        );
    }

    @Test
    public void testCreateJsonXmlInputFactory() throws Exception {
        Class.forName("nl.ulso.sprox.json.JsonXmlInputFactory");
    }

    @Test
    public void testJsonXmlConstants() throws Exception {
        Class.forName("nl.ulso.sprox.json.JsonXmlConstants");
    }

    @Test
    public void testJsonValueParserWrapper() throws Exception {
        Class.forName("nl.ulso.sprox.json.JsonValueParserWrapper");
        JsonValueParserWrapper.withJsonNull(Integer::parseInt);
    }
}
