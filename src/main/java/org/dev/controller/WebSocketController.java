package org.dev.controller;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


@Controller
public class WebSocketController {

    private RandomAccessFile raf;
    private static File logFile;

    @SubscribeMapping("/topic/ingestion")
    public List<String> chatInit() throws IOException {

        return new ArrayList<>();

//        logFile = new File("log.txt");
//        raf = new RandomAccessFile(logFile, "rw");
//
//        StringBuilder sb = new StringBuilder();
//
//        ArrayList<String> last10Logs = new ArrayList<>();
//
//        long fileLength = logFile.length();
//        for(long filePointer = fileLength-1; filePointer != -1; filePointer--){
//            raf.seek(filePointer);
//            int readBytes = raf.read();
//
//            if(readBytes == 10) {
//                last10Logs.add(sb.reverse().toString());
//                sb = new StringBuilder();
//                if(last10Logs.size() == 10){
//                    break;
//                }
//            }
//            else{
//                sb.append((char)readBytes);
//            }
//        }
//
//        for (int i = last10Logs.size()-1;i>=0;i--) {
//            System.out.println(last10Logs.get(i));
//        }
//
//        return last10Logs;
    }
}
