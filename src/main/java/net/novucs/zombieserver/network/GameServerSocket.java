package net.novucs.zombieserver.network;

import net.novucs.zombieserver.Main;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

/**
 * The server Socket.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class GameServerSocket extends WebSocketServer {

    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    /**
     * Constructs a new game server socket.
     *
     * @param address the address to bind to.
     * @param port    the port to bind to.
     */
    public GameServerSocket(InetAddress address, int port) {
        super(new InetSocketAddress(address, port));
    }

    /**
     * Gets the message queue.
     *
     * @return the message queue.
     */
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
        if (!(ex instanceof ClosedByInterruptException)) {
            Main.getLogger().log(Level.SEVERE, "Error: ", ex);
        }
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
