package net.novucs.zombieserver;

import util.ZombieServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private final InetAddress address;
    private final int port;

    public Main(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // use a try/catch block to handle the case when opening
        // a socket fails...
        InetAddress address;

        try {
            // TODO you need to define classes to represent the world
            // then here you should use WorldLoader to load the world and 
            // convert it to your representation of the world to play 
            // the game with your version of ZombieBot.

            // create an instance of our server to communicate with the
            // web frontend.
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, "Failed to connect to the client", ex);
            return;
        }

        // now connect to the server
        // get host address, rather than using 127.0.0.1, as this
        // will then be displayed when server waits for connection
        // which allows the address to then be typed into client.
        ZombieServer server = new ZombieServer(address.getHostAddress(), 8085, ZombieBot.create());
    }
}
