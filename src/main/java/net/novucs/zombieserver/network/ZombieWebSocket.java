package net.novucs.zombieserver.network;

import net.novucs.zombieserver.Main;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

public class ZombieWebSocket extends WebSocketServer {

    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public ZombieWebSocket(InetAddress address, int port) {
        super(new InetSocketAddress(address, port));
    }

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Main.getLogger().info("Connection opened.");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Main.getLogger().info("Connection closed.");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Main.getLogger().log(Level.SEVERE, "Error: ", ex);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        messageQueue.add(message);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        FrameBuilder builder = (FrameBuilder) frame;
        builder.setTransferemasked(false);
        conn.sendFrame(frame);
    }

    /**
     * Sends a message to all connected clients.
     *
     * @param message the message to send.
     */
    public void send(String message) {
        for (WebSocket socket : connections()) {
            socket.send(message);
        }
    }
}
