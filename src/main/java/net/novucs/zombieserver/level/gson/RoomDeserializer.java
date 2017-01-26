package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.builder.RoomBuilder;

import java.lang.reflect.Type;

public class RoomDeserializer implements JsonDeserializer<Room> {

    @Override
    public Room deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = (JsonObject) element;

        RoomBuilder builder = new RoomBuilder();
        builder.name(object.get("name").getAsString());
        builder.description(object.get("description").getAsString());
        builder.zombies(object.get("zombies").getAsInt());

        for (JsonElement itemElement : object.getAsJsonArray("items")) {
            JsonObject itemObject = (JsonObject) itemElement;
            builder.addItem(new Item(itemObject.get("item").getAsString()));
        }

        return builder.build();
    }
}
