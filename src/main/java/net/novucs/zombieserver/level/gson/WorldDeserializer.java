package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.ItemType;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;
import net.novucs.zombieserver.level.builder.EntranceBuilder;
import net.novucs.zombieserver.level.builder.WorldBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldDeserializer implements JsonDeserializer<World> {

    @Override
    public World deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = (JsonObject) element;

        WorldBuilder builder = new WorldBuilder();
        builder.info(object.get("info").getAsString());
        builder.startHtml(object.get("startHtml").getAsString());
        builder.inventoryHtml(object.get("inventoryHtml").getAsString());

        for (JsonElement itemElement : object.getAsJsonArray("items")) {
            builder.addItem(context.deserialize(itemElement, ItemType.class));
        }

        Map<String, Room> rooms = new HashMap<>();

        for (JsonElement roomElement : object.getAsJsonArray("rooms")) {
            Room room = context.deserialize(roomElement, Room.class);
            builder.addRoom(room);
            rooms.put(room.getName(), room);
        }

        for (JsonElement roomElement : object.getAsJsonArray("rooms")) {
            JsonObject roomObject = (JsonObject) roomElement;
            for (JsonElement entranceElement : roomObject.getAsJsonArray("entrances")) {
                JsonObject entranceObject = (JsonObject) entranceElement;
                Optional<Direction> direction = Direction.getByShorthand(entranceObject.get("direction").getAsString());
                Room from = rooms.get(roomObject.get("name").getAsString());
                Room to = rooms.get(entranceObject.get("to").getAsString());
                boolean locked = entranceObject.get("locked").getAsBoolean();

                direction.ifPresent(d -> {
                    EntranceBuilder entranceBuilder = new EntranceBuilder();
                    entranceBuilder.direction(d);
                    entranceBuilder.to(to);
                    entranceBuilder.locked(locked);

                    from.getEntrances().add(entranceBuilder.build());
                });
            }
        }

        builder.start(rooms.get(object.get("start").getAsString()));
        builder.finish(rooms.get(object.get("finish").getAsString()));

        return builder.build();
    }
}
