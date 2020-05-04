package com.yoshi.chat.app.coordinator;

import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Component
@Transactional
@AllArgsConstructor
public class ChatCoordinator {
    private final UserService userService;
    private final ChatLogService chatLogService;

    //    TODO: exceptionが適当
    public User confirmUserAndCreateChatLog(String cookieValue, String message) {
        User user = userService.findByCookieValue(cookieValue)
                               .orElseThrow(() -> new RuntimeException("No User"));
        chatLogService.create(user.getId(), message);
        return user;
    }
}
