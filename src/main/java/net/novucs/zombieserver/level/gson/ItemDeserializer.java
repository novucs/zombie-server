package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Item;

import java.lang.reflect.Type;

/**
 * Deserialize an {@link Item} from JSON.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class ItemDeserializer implements JsonDeserializer<Item> {

    @Override
    public Item deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = (JsonObject) element;

        String name = object.get("name").getAsString();
        String html = object.get("html").getAsString();

        return Item.of(name, html);
    }
}
