package nl.ulso.sprox.json;

import nl.ulso.sprox.XmlProcessorBuilder;
import nl.ulso.sprox.XmlProcessorException;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;

import javax.xml.stream.XMLInputFactory;
import java.io.StringReader;

import static javax.xml.stream.XMLInputFactory.newFactory;
import static nl.ulso.sprox.json.JsonValueParserWrapper.addDefaultJsonParsers;

/**
 * Convenience methods for tests.
 */
public final class SproxJsonTests {

    private SproxJsonTests() {
    }

    public static <T> T processString(String string, Class<T> resultClass, Class<?> controllerClass, XMLInputFactory factory)
            throws XmlProcessorException {
        final XmlProcessorBuilder<T> builder = new StaxBasedXmlProcessorBuilderFactory()
                .createXmlProcessorBuilder(resultClass)
                .setXmlInputFactory(factory)
                .addControllerClass(controllerClass);
        addDefaultJsonParsers(builder);
        return builder.buildXmlProcessor().execute(new StringReader(string));
    }

    public static <T> T processJsonString(String json, Class<T> resultClass, Class<?> controllerClass)
            throws XmlProcessorException {
        return processString(json, resultClass, controllerClass, new JsonXmlInputFactory());
    }

    public static <T> T processXmlString(String xml, Class<T> resultClass, Class<?> controllerClass)
            throws XmlProcessorException {
        return processString(xml, resultClass, controllerClass, newFactory());
    }
}
