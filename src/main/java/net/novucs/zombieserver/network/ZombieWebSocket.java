package net.novucs.zombieserver.network;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.net.InetSocketAddress;
import java.util.Collection;

public class ZombieWebSocket extends WebSocketServer {

    private final BehaviorSubject<String> messages = BehaviorSubject.create();

    public ZombieWebSocket(String host, int port) {
        super(new InetSocketAddress(port));
        System.out.println("Waiting for connection " + host + ":" + port);
    }

    public Observable<String> messageStream() {
        return this.messages.asObservable();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("connected");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error:");
        ex.printStackTrace();
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        this.messages.onNext(message);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        FrameBuilder builder = (FrameBuilder) frame;
        builder.setTransferemasked(false);
        conn.sendFrame(frame);
    }

    public void send(String msg) {
        Collection<WebSocket> con = this.connections();
        synchronized (con) {
            for (WebSocket c : con) {
                c.send(msg);
            }
        }
    }
}
