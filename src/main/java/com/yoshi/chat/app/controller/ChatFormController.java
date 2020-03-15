package com.yoshi.chat.app.controller;

import com.yoshi.chat.app.common.util.*;
import com.yoshi.chat.app.coordinator.*;
import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;
import java.util.stream.*;

@Controller
@RequestMapping("/form")
@AllArgsConstructor
public class ChatFormController {
    private final ChatCoordinator chatCoordinator;
    private final ChatLogService chatLogService;
    private final UserService userService;
    private final ChatLogProperties chatLogProperties;
    private final CookieNameProperties cookieNameProperties;

    @GetMapping("")
    public String form(Model model) {
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
        return "pages/form";
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<String> chat(@RequestParam String message,
                                       HttpServletRequest request) {
        String cookieValue = RetrieveCookieUtil.retrieveValue(request.getCookies(),
                                                              cookieNameProperties.getUserCookie());
        chatCoordinator.confirmUserAndCreateChatLog(cookieValue, message);
        return ResponseEntity.ok("ok");
    }

    @Value
    @Builder
    private static class ChatLogResponse {
        ChatLog chatLog;
        String userName;
    }
}
