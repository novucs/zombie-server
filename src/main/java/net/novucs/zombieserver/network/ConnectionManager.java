package net.novucs.zombieserver.network;

import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.Main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConnectionManager implements Runnable {

    private final GameManager game;
    private final GameServerSocket socket;

    public ConnectionManager(GameManager game, GameServerSocket socket) {
        this.game = game;
        this.socket = socket;
    }

    public static ConnectionManager create(GameManager game, InetAddress address, int port) {
        GameServerSocket socket = new GameServerSocket(address, port);
        return new ConnectionManager(game, socket);
    }

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
            sendScore(game.getScore());
            response.forEach(this::sendOutput);

            if (game.getState() == GameState.FINISHED) {
                try {
                    socket.stop((int) TimeUnit.SECONDS.toMillis(5));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                break;
            }

            if (game.enableTimer()) {
                sendTimer(5);
            } else if (game.disableTimer()) {
                sendTimer(0);
            }
        }
    }

    private void sendOutput(String text) {
        socket.send("{ \"type\" : \"output\", \"text\" : \"" + text + "\" }");
    }

    private void sendTimer(int duration) {
        socket.send("{ \"type\" : \"timer\", \"value\" : \"" + duration + "\" }");
    }

    private void sendScore(int score) {
        socket.send("{ \"type\" : \"score\", \"value\" : " + score + " }");
    }
}
