package net.novucs.zombieserver.network;

import net.novucs.zombieserver.GameManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class ConnectionManager {

    private final ZombieWebSocket socket;
    private boolean playing = true;

    public ConnectionManager(ZombieWebSocket socket) {
        this.socket = socket;
    }

    public static ConnectionManager create(InetAddress address, int port) {
        ZombieWebSocket socket = new ZombieWebSocket(address, port);
        return new ConnectionManager(socket);
    }

    public void initialize(GameManager game) {
        socket.start();

        String message;

        while (playing) {

            try {
                message = socket.getMessageQueue().take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            List<String> response = game.executeCommand(message);

            if (game.shouldQuit()) {
                sendOutput("<b>bye bye</b>");
                playing = false;

                try {
                    socket.stop();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            sendScore(game.currentScore());
            response.forEach(this::sendOutput);

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
