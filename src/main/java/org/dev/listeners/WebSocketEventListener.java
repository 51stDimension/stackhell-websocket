package org.dev.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class WebSocketEventListener {

    @Autowired
    SimpMessagingTemplate template;

    //TODO: Difference between subscribe and connect?

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event){

    }

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {

    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("Session has been disconnected!");
    }

    //Handles code for new connections.
    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) throws IOException {
        System.out.println("New connection connected");
        template.convertAndSend("/topic/ingestion", "Session connect event!");

        //TODO: What is convertAndSendToUser?
    }
}