package org.dev.monitoring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Monitor {
    private static long lastReadEpochTimeStamp = 0L;
    private static File logFile;
    private static Logger logger;
    private static Timer t;
    private RandomAccessFile raf;

    @Autowired
    SimpMessagingTemplate template;

    @PostConstruct
    public void init() throws IOException {
        logger = LoggerFactory.getLogger(Monitor.class);
        logger.info("Starting to monitor the file!");
        logFile = new File("log.txt");
        raf = new RandomAccessFile(logFile, "rw");

        t = new Timer();

        pollFile();
    }

    private void pollFile() {
        logger.info("Watching file");
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(logFile.lastModified() > lastReadEpochTimeStamp) {
                    logger.info("Looks like the file is changed. Lets detect the changes at time: {}", logFile.lastModified());
                    lastReadEpochTimeStamp = logFile.lastModified();
                    try {
                        sendContentsToSocket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        },500,1000);
    }

    private void sendContentsToSocket() throws IOException {
        ArrayList<String> logDataLineByLine = new ArrayList<>();

        System.out.println("File pointer at:" + raf.getFilePointer());

        try {
            String line = raf.readLine();

            while (line != null) {
                System.out.println(line);
                logDataLineByLine.add(line);
                line = raf.readLine();
            }
            //FilePointer
        } catch (IOException e) {
            e.printStackTrace();
        }

        template.convertAndSend("/topic/ingestion", logDataLineByLine);
    }
}
