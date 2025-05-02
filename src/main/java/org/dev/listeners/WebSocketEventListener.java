package org.dev.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

@Component
public class WebSocketEventListener {

    private RandomAccessFile raf;
    private static File logFile;

    @Autowired
    SimpMessagingTemplate template;

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event){

    }

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {

    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) throws IOException {
        System.out.println("New connection connected");
        logFile = new File("log.txt");
        raf = new RandomAccessFile(logFile, "rw");

        StringBuilder sb = new StringBuilder();

        ArrayList<String> last10Logs = new ArrayList<>();

        long fileLength = logFile.length();
        for(long filePointer = fileLength-1; filePointer != -1; filePointer--){
            raf.seek(filePointer);
            int readBytes = raf.read();

            if(readBytes == 10) {
                last10Logs.add(sb.reverse().toString());
                sb = new StringBuilder();
                if(last10Logs.size() == 10){
                    break;
                }
            }
            else{
                sb.append((char)readBytes);
            }
        }

        ArrayList<String> rev = new ArrayList<>();

        for (int i = last10Logs.size()-1;i>=0;i--) {
            rev.add(last10Logs.get(i));
        }
        System.out.println(event.getUser());
//
//        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
//
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
//                .create(SimpMessageType.MESSAGE);
//        headerAccessor.setLeaveMutable(true);
//        headerAccessor.setSessionId(sessionId);

        template.convertAndSendToUser(event.getUser().getName(),"/topic/ingestion", rev);
        template.convertAndSend("/topic/ingestion", rev);
    }
}