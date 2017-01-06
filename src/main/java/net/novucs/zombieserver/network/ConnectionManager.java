package net.novucs.zombieserver.network;

import net.novucs.zombieserver.GameManager;
import rx.Observable;

import java.util.List;

public class ConnectionManager {

    private final ZombieWebSocket zsocket;
    private final Observable<String> messages;
    private boolean playing = false;

    public ConnectionManager(String host, int port, GameManager game) {
        zsocket = new ZombieWebSocket(host, port);
        zsocket.start();

        messages = zsocket.messageStream();
        messages.subscribe(msg -> {
            if (playing) {
                List<String> response = game.processCmd(msg);

                if (game.shouldQuit()) {
                    sendOutput("<b>bye bye</b>");
                    playing = false;
                    System.exit(0);
                }

                sendScore(game.currentScore());
                response.forEach(this::sendOutput);

                if (game.enableTimer()) {
                    sendTimer(5);
                } else if (game.disableTimer()) {
                    sendTimer(0);
                }
            } else if (msg.equals("begin")) {
                playing = true;
                sendOutput(game.begin());
            }
        });
    }

    private void sendOutput(final String text) {
        zsocket.send("{ \"type\" : \"output\", \"text\" : \"" + text + "\" }");
    }

    private void sendTimer(final int duration) {
        zsocket.send("{ \"type\" : \"timer\", \"value\" : \"" + duration + "\" }");
    }

    private void sendScore(final int score) {
        zsocket.send("{ \"type\" : \"score\", \"value\" : " + score + " }");
    }
}
