package com.yoshi.chat.app.controller;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.yoshi.chat.app.common.util.*;
import com.yoshi.chat.app.coordinator.*;
import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

// 練習として、こちらはWebSocketを使用してみます。。。
@Controller
@AllArgsConstructor
@Slf4j
public class WebsocketController {
    private final ChatCoordinator chatCoordinator;
    private final ChatLogService chatLogService;
    private final UserService userService;
    private final ChatLogProperties chatLogProperties;
    private final CookieNameProperties cookieNameProperties;

    @GetMapping("/websocket/form")
    public String form(Model model,
                       HttpServletRequest request) {
        String myName = userService.findByCookieValue(RetrieveCookieUtil.retrieveValue(request.getCookies(),
                                                                                       cookieNameProperties.getUserCookie()))
                                   .orElseThrow(() -> new RuntimeException("No User"))
                                   .getUserName();
        List<ChatLog> chatLogs =
                chatLogService.findAllLimit(chatLogProperties.getLimit());
        List<ChatLogResponse> chatLogResponses =
                chatLogs.stream()
                        .map(chatLog -> {
                            String userName = userService.find(chatLog.getUserId())
                                                         .orElseThrow(() -> new RuntimeException("No User"))
                                                         .getUserName();
                            return ChatLogResponse.builder()
                                                  .chatLog(chatLog)
                                                  .userName(userName)
                                                  .build();
                        })
                        .collect(Collectors.toList());
        model.addAttribute("chatLogResponses", chatLogResponses);
        model.addAttribute("myName", myName);
        return "pages/formWebSocket";
    }

    @MessageMapping("/whisper")
    @SendTo("/topic/chat")
    public NewMessage greeting(ReceivedMessage receivedMessage) {
        log.info("Message={}", receivedMessage);
        return NewMessage.builder()
                         .newMessage(receivedMessage.getMessage())
                         .userName("MyNameIs...")
                         .build();
    }

//    @MessageMapping(value = "/websocket/new" /* 宛先名 */)
//    // Controller内の@MessageMappingアノテーションをつけたメソッドが、メッセージを受け付ける
//    @SendTo(value = "/queue/chat") // 処理結果の送り先
//    @ResponseBody
//    public String chat(String message) {
////        String cookieValue = RetrieveCookieUtil.retrieveValue(request.getCookies(),
////                                                              cookieNameProperties.getUserCookie());
////        chatCoordinator.confirmUserAndCreateChatLog(cookieValue, message);
//        log.info("WebSocket:{}", message);
//        return message;
//    }

    @Value
    @Builder
    private static class ChatLogResponse {
        ChatLog chatLog;
        String userName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ReceivedMessage {
        String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    private static class NewMessage {
        String newMessage;
        String userName;
    }
}
