package net.novucs.zombieserver;

import net.novucs.zombieserver.network.ConnectionManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String BIND_ADDRESS = "localhost";
    private static final int PORT = 8085;

    /**
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        InetAddress address;

        try {
            address = InetAddress.getByName(BIND_ADDRESS);
        } catch (UnknownHostException ex) {
            getLogger().log(Level.SEVERE, "Localhost could not be resolved to an address", ex);
            return;
        }

        GameManager game = GameManager.create();
        ConnectionManager connectionManager = ConnectionManager.create(game, address, PORT);
        connectionManager.initialize();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
