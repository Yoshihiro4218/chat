package com.yoshi.chat.app.controller;

import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/form")
@AllArgsConstructor
public class ChatFormController {
    private final ChatLogService chatLogService;
    private final ChatLogProperties chatLogProperties;

    @GetMapping("")
    public String form(Model model) {
        List<ChatLog> chatLogs =
                chatLogService.findAllLimit(chatLogProperties.getLimit());
        model.addAttribute("chatLogs", chatLogs);
        return "pages/form";
    }
}
