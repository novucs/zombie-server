package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Entrance;
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

        for (JsonElement itemElement : object.getAsJsonArray("items")) {
            JsonObject itemObject = (JsonObject) itemElement;
            Item.get(itemObject.get("item").getAsString())
                    .ifPresent(builder::addItem);
        }

        for (JsonElement entranceElement : object.getAsJsonArray("entrances")) {
            builder.addEntrance(context.deserialize(entranceElement, Entrance.class));
        }

        builder.zombies(object.get("zombies").getAsInt());

        return builder.build();
    }
}
