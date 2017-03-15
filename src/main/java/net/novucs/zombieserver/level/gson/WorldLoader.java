package net.novucs.zombieserver.level.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.novucs.zombieserver.Main;
import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Utility for loading a {@link World} from file.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class WorldLoader {

    /**
     * Loads the world from a given resource.
     *
     * @param resource the resource to open.
     * @return the loaded world or {@code Optional#empty()} if invalid.
     */
    public Optional<World> load(String resource) {
        Optional<InputStream> inputStream = getResource(resource);

        if (!inputStream.isPresent()) {
            return Optional.empty();
        }

        String worldData = readFully(inputStream.get());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(World.class, new WorldDeserializer())
                .registerTypeAdapter(Item.class, new ItemDeserializer())
                .registerTypeAdapter(Room.class, new RoomDeserializer())
                .registerTypeAdapter(Entrance.class, new EntranceDeserializer())
                .create();

        try {
            return Optional.of(gson.fromJson(worldData, World.class));
        } catch (Exception ex) {
            Main.getLogger().log(Level.SEVERE, resource + " is formatted incorrectly!", ex);
            return Optional.empty();
        }
    }

    /**
     * Reads an {@link InputStream} fully into a single {@link String}.
     *
     * @param inputStream the stream to read in.
     * @return the stream contents as a {@link String}.
     */
    private String readFully(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        return scanner.useDelimiter("\0").next();
    }

    /**
     * Gets a resource, either as a file in the same directory as the runnable
     * or from within the JAR resource itself.
     *
     * @param resource the resource to read.
     * @return the stream of the found resource or {@code Optional#empty()}.
     */
    private Optional<InputStream> getResource(String resource) {
        try {
            return Optional.of(new FileInputStream(new File(resource)));
        } catch (FileNotFoundException e) {
            return Optional.ofNullable(getClass().getResourceAsStream("/" + resource));
        }
    }
}
