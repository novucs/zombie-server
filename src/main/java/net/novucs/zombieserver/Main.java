package net.novucs.zombieserver;

import net.novucs.zombieserver.network.ConnectionManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements Runnable {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void run() {
        // use a try/catch block to handle the case when opening
        // a socket fails...
        InetAddress address;

        try {
            // TODO you need to define classes to represent the world
            // then here you should use WorldLoader to load the world and
            // convert it to your representation of the world to play
            // the game with your version of GameManager.

            // create an instance of our server to communicate with the
            // web frontend.
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, "Localhost could not be resolved to an address", ex);
            return;
        }

        // now connect to the server
        // get host address, rather than using 127.0.0.1, as this
        // will then be displayed when server waits for connection
        // which allows the address to then be typed into client.
        new ConnectionManager(address.getHostAddress(), 8085, GameManager.create());
    }
}
