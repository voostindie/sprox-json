package nl.ulso.sprox.json;

import nl.ulso.sprox.XmlProcessor;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class JsonProcessingTest {

    private XmlProcessor<RootObject> xmlProcessor = new StaxBasedXmlProcessorBuilderFactory()
            .createXmlProcessorBuilder(RootObject.class)
            .setXmlInputFactory(new JsonXmlInputFactory())
            .addControllerClass(Controller.class)
            .buildXmlProcessor();

    @Test
    public void testInputStream() throws Exception {
        final RootObject object = xmlProcessor.execute(JsonProcessingTest.class.getResourceAsStream("/test.json"));
        assertRootObject(object);
    }

    @Test
    public void testReader() throws Exception {
        final RootObject object = xmlProcessor.execute(
                new InputStreamReader(JsonProcessingTest.class.getResourceAsStream("/test.json")));
        assertRootObject(object);
    }

    private void assertRootObject(RootObject object) {
        assertThat(object, notNullValue());
        assertThat(object.getId(), is(42));
        final NestedObject nestedObject = object.getNestedObject();
        assertThat(nestedObject.getString(), is("Value"));
        final List<Item> items = nestedObject.getItems();
        assertThat(items, notNullValue());
        assertThat(items.size(), is(3));
        for (int i = 0; i < 3; i++) {
            final Item item = items.get(i);
            assertThat(item.getId(), is(i + 1));
            assertThat(item.getName(), is("Item " + (i + 1)));
        }
    }
}
