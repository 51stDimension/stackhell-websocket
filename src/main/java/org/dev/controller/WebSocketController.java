package org.dev.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


@Controller
public class WebSocketController {

    //TODO: What is the use of: @SubscribeMapping

    @MessageMapping("/hello")
    @SendTo("/topic/ingestion")
    public String greeting(String userName) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "This has been processed via backend and sent back:"+userName;
    }
}
