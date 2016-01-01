package nl.ulso.sprox.json.osgi;

import nl.ulso.sprox.json.JsonValueParserWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import static java.lang.Class.forName;
import static java.lang.Integer.parseInt;
import static nl.ulso.sprox.json.JsonValueParserWrapper.withJsonNull;
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
        forName("nl.ulso.sprox.json.JsonXmlInputFactory");
    }

    @Test
    public void testJsonXmlConstants() throws Exception {
        forName("nl.ulso.sprox.json.JsonXmlConstants");
    }

    @Test
    public void testJsonValueParserWrapper() throws Exception {
        forName("nl.ulso.sprox.json.JsonValueParserWrapper");
        withJsonNull(::parseInt);
    }
}
