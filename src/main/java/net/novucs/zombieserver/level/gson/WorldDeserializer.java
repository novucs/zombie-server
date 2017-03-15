package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;
import net.novucs.zombieserver.level.builder.WorldBuilder;

import java.lang.reflect.Type;

/**
 * Deserialize a {@link World} from JSON.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class WorldDeserializer implements JsonDeserializer<World> {

    @Override
    public World deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = (JsonObject) element;

        WorldBuilder builder = new WorldBuilder();
        builder.info(object.get("info").getAsString());
        builder.startHtml(object.get("startHtml").getAsString());
        builder.inventoryHtml(object.get("inventoryHtml").getAsString());

        for (JsonElement itemElement : object.getAsJsonArray("items")) {
            builder.addItem(context.deserialize(itemElement, Item.class));
        }

        for (JsonElement roomElement : object.getAsJsonArray("rooms")) {
            builder.addRoom(context.deserialize(roomElement, Room.class));
        }

        builder.start(builder.getRooms().get(object.get("start").getAsString()));
        builder.finish(builder.getRooms().get(object.get("finish").getAsString()));

        return builder.build();
    }
}
