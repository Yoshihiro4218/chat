package com.yoshi.chat.app.controller;

import lombok.extern.slf4j.*;
import org.springframework.context.event.*;
import org.springframework.messaging.support.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.messaging.*;

@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        GenericMessage message = (GenericMessage) event.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");

        log.info("Event.getMessage()={}", message);

//        if (simpDestination.startsWith("/topic/group/1")) {
//            // do stuff
//        }
    }
}