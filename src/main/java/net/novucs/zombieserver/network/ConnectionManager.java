package net.novucs.zombieserver.network;

import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.Main;
import net.novucs.zombieserver.level.ZombieTimerState;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * Manages all connections to the game server.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class ConnectionManager implements Runnable {

    private final GameManager game;
    private final GameServerSocket socket;

    /**
     * Constructs a new {@link ConnectionManager}.
     *
     * @param game   the game manager players should be connected to.
     * @param socket the socket the players connect with.
     */
    public ConnectionManager(GameManager game, GameServerSocket socket) {
        this.game = game;
        this.socket = socket;
    }

    /**
     * Creates a new {@link ConnectionManager}.
     *
     * @param game    the game players should connect to.
     * @param address the server address.
     * @param port    the server port.
     * @return the newly created {@link ConnectionManager}.
     */
    public static ConnectionManager create(GameManager game, InetAddress address, int port) {
        GameServerSocket socket = new GameServerSocket(address, port);
        return new ConnectionManager(game, socket);
    }

    /**
     * Starts the connection manager, ready for handling new connections.
     */
    public void initialize() {
        socket.start();
        Main.getLogger().info("Waiting for connection " + socket.getAddress().getHostName() + ":" + socket.getPort());
        run();
    }

    @Override
    public void run() {
        String message;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                message = socket.getMessageQueue().take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            List<String> response = game.executeCommand(message);
            response.forEach(this::sendOutput);
            sendScore(game.getScore());

            if (game.getState() == GameState.FINISHED) {
                closeServer();
                break;
            }

            updateZombieTimer();
        }
    }

    /**
     * Closes the connection manager, stopping all new connections.
     */
    private void closeServer() {
        try {
            socket.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Updates the zombie timer state.
     */
    private void updateZombieTimer() {
        switch (game.getZombieTimerState()) {
            case START:
                sendZombieTimer(5);
                break;
            case STOP:
                sendZombieTimer(0);
                break;
        }

        game.setZombieTimerState(ZombieTimerState.UNCHANGED);
    }

    /**
     * Sends the client a message/
     *
     * @param text the message to send.
     */
    private void sendOutput(String text) {
        socket.send("{ \"type\" : \"output\", \"text\" : \"" + text + "\" }");
    }

    /**
     * Sends the client a new zombie timer duration.
     *
     * @param duration the new duration to set the zombie timer at.
     */
    private void sendZombieTimer(int duration) {
        socket.send("{ \"type\" : \"timer\", \"value\" : \"" + duration + "\" }");
    }

    /**
     * Sends the client the current score of the game
     *
     * @param score the current score.
     */
    private void sendScore(int score) {
        socket.send("{ \"type\" : \"score\", \"value\" : " + score + " }");
    }
}
