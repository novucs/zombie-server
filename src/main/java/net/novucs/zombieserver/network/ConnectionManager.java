package net.novucs.zombieserver.network;

import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.Main;
import net.novucs.zombieserver.level.ZombieTimerState;

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
            response.forEach(this::sendOutput);
            sendScore(game.getScore());

            if (game.getState() == GameState.FINISHED) {
                closeServer();
                break;
            }

            updateZombieTimer();
        }
    }

    private void closeServer() {
        try {
            socket.stop((int) TimeUnit.SECONDS.toMillis(5));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

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

    private void sendOutput(String text) {
        socket.send("{ \"type\" : \"output\", \"text\" : \"" + text + "\" }");
    }

    private void sendZombieTimer(int duration) {
        socket.send("{ \"type\" : \"timer\", \"value\" : \"" + duration + "\" }");
    }

    private void sendScore(int score) {
        socket.send("{ \"type\" : \"score\", \"value\" : " + score + " }");
    }
}
