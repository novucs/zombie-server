package net.novucs.zombieserver;

import net.novucs.zombieserver.level.World;
import net.novucs.zombieserver.level.generator.WorldGenerator;
import net.novucs.zombieserver.level.gson.WorldLoader;
import net.novucs.zombieserver.network.ConnectionManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String DEFAULT_WORLD = "world.json";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String BIND_ADDRESS = "localhost";
    private static final int PORT = 8085;

    /**
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        // Attempt to load or generate world depending on arguments provided.
        Optional<World> world;

        if (args.length == 0) {
            world = new WorldLoader().load(DEFAULT_WORLD);
        } else if (args[0].equalsIgnoreCase("generate")) {
            world = Optional.of(new WorldGenerator().generate());
        } else {
            world = new WorldLoader().load(args[0]);
        }

        // Exit program when unable to load world from file.
        if (!world.isPresent()) {
            LOGGER.severe("Unable to load world specified!");
            LOGGER.severe("If you do not wish to load a world, provide a \"generate\" argument.");
            LOGGER.severe("e.g. java -jar ZombieServer.jar generate");
            return;
        }

        // Attempt to get the server IP address.
        InetAddress address;

        try {
            address = InetAddress.getByName(BIND_ADDRESS);
        } catch (UnknownHostException ex) {
            getLogger().log(Level.SEVERE, "Localhost could not be resolved to an address", ex);
            return;
        }

        // Create and run the game and connection managers.
        GameManager game = GameManager.create(world.get());
        ConnectionManager connectionManager = ConnectionManager.create(game, address, PORT);
        connectionManager.initialize();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
