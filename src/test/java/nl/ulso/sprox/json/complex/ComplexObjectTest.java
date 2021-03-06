package nl.ulso.sprox.json.complex;

import nl.ulso.sprox.Node;
import org.junit.Test;

import java.util.List;

import static nl.ulso.sprox.json.SproxJsonTests.processJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ComplexObjectTest {
    @Test
    public void testComplexObject() throws Exception {
        final String json = "{\n" +
                "  \"id\": 42,\n" +
                "  \"container\": {\n" +
                "    \"string\": \"Value\",\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Item 1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Item 2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Item 3\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        final ComplexObject object = processJsonString(json, ComplexObject.class, ComplexObjectController.class);

        assertThat(object, notNullValue());
        assertThat(object.getId(), is(42L));
        final Container container = object.getContainer();
        assertThat(container, notNullValue());
        assertThat(container.getString(), is("Value"));
        final List<Item> items = container.getItems();
        assertThat(items.size(), is(3));
        final Item secondItem = items.get(1);
        assertThat(secondItem.getId(), is(2));
        assertThat(secondItem.getName(), is("Item 2"));
    }

    public static final class ComplexObjectController {

        @Node
        public ComplexObject root(@Node long id, Container object) {
            return new ComplexObject(id, object);
        }

        @Node
        public Container container(@Node String string, List<Item> items) {
            return new Container(string, items);
        }

        @Node
        public Item list(@Node int id, @Node String name) {
            return new Item(id, name);
        }

    }
}
