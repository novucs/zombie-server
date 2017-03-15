package net.novucs.zombieserver.level.gson;

import com.google.gson.*;
import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.builder.EntranceBuilder;

import java.lang.reflect.Type;

/**
 * Deserialize an {@link Entrance} from JSON.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class EntranceDeserializer implements JsonDeserializer<Entrance> {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Entrance deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = (JsonObject) element;

        EntranceBuilder builder = new EntranceBuilder();
        builder.direction(Direction.getByShorthand(object.get("direction").getAsString()).get());
        builder.to(object.get("to").getAsString());
        builder.locked(object.get("locked").getAsBoolean());

        return builder.build();
    }
}
