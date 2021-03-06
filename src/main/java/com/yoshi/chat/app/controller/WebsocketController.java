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
import java.time.temporal.*;
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
        //        TODO: 応急処置でtry-catch
        String cookieValue;
        try {
            cookieValue = RetrieveCookieUtil.retrieveValue(request.getCookies(),
                                                           cookieNameProperties.getUserCookie());
        } catch (RuntimeException unused) {
            return "redirect:/websocket/entrance";
        }
        List<ChatLog> chatLogs =
                chatLogService.findAllLimit(chatLogProperties.getLimit());

        //        TODO: 応急処置でtry-catch
        List<ChatLogResponse> chatLogResponses;
        try {
            chatLogResponses =
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
        } catch (RuntimeException unused) {
            return "redirect:/websocket/entrance";
        }

        model.addAttribute("chatLogResponses", chatLogResponses);
        model.addAttribute("cookieValue", cookieValue);
        return "pages/formWebSocket";
    }

    @MessageMapping("/whisper")
    @SendTo("/client/chat")
    public NewMessage chat(ReceivedMessage receivedMessage) {
        log.info("Cookie={}", receivedMessage.getCookie());
        log.info("Message={}", receivedMessage.getMessage());
        User user = chatCoordinator.confirmUserAndCreateChatLog(receivedMessage.getCookie(),
                                                                receivedMessage.getMessage());
//        TODO: ログとnowがずれるが応急処置なのでとりあえずスルー
        return NewMessage.builder()
                         .newMessage(receivedMessage.getMessage())
                         .userName(user.getUserName())
                         .now(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                         .build();
    }

    @Value
    @Builder
    private static class ChatLogResponse {
        ChatLog chatLog;
        String userName;
    }


    //    TODO: 入室メッセージのために public にしているが、よくない
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReceivedMessage {
        String message;
        String cookie;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class NewMessage {
        String newMessage;
        String userName;
        LocalDateTime now;
    }
}
