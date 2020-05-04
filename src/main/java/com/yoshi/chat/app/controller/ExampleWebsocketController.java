package com.yoshi.chat.app.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

@Controller
@AllArgsConstructor
@Slf4j
public class ExampleWebsocketController {
    @GetMapping("/example")
    public String example() {
        return "pages/example";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Greeting {
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HelloMessage {
        private String name;
    }
}
