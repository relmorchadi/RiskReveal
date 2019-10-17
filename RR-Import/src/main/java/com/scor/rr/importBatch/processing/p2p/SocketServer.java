package com.scor.rr.importBatch.processing.p2p;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * Created by U002629 on 11/04/2016.
 */
@ServerEndpoint(value = "/calcprog")
public class SocketServer {
    private static final boolean DBG = false;
    private static Logger logger = Logger.getLogger(SocketServer.class.getSimpleName());
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>(); // connected clients

    @OnOpen
    public void onOpen(Session session) {
        queue.add(session);
        if (DBG) logger.info("SocketServer onOpen... " + session.getId());
    }

    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        if (DBG) logger.info("SocketServer onError... session " + session.getId() + ": " + t.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (DBG) logger.info("SocketServer onMessage " + message + ", session " + session);
        switch (message) {
            case "quit":
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game ended"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        queue.remove(session);
        if (DBG) logger.info(String.format("SocketServer onClose, Session %s closed because of %s", session.getId(), closeReason));
    }

    public static void sendMessage(String msg) {
        try {
            ArrayList<Session> closedSessions = new ArrayList<Session>();
            for (Session session : queue) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(msg);
                    continue;
                }
                if (DBG) logger.info("Closed session: " + session.getId());
                closedSessions.add(session);
            }
            queue.removeAll(closedSessions);
            if (DBG) logger.info("Sending " + msg + " to " + queue.size() + " clients");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        for (Session session : queue) {
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        queue.clear();
    }
}
