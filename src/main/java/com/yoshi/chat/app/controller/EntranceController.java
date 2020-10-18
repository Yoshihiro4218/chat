package com.yoshi.chat.app.controller;

import com.yoshi.chat.app.helper.*;
import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.time.*;
import java.time.temporal.*;

import static com.yoshi.chat.app.controller.WebsocketController.NewMessage;

@Controller
@AllArgsConstructor
public class EntranceController {
    private final UserService userService;
    private final ChatLogService chatLogService;
    private final UserCookieValueHelper userCookieValueHelper;
    private final CookieNameProperties cookieNameProperties;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/entrance")
    public String form() {
        return "pages/entrance";
    }

    @PostMapping("/entrance")
    public String entry(@RequestParam String userName,
                        Model model,
                        HttpServletResponse response) {
        if (userName.isEmpty()) {
            model.addAttribute("userNameNull", true);
            return "pages/entrance";
        }
        String cookieValue = userCookieValueHelper.generate();
        userService.create(cookieValue, userName);
        //        TODO: いつかなおす。
        chatLogService.create(1L, userName + "さんが入室しました");
        simpMessagingTemplate.convertAndSend("/client/chat", NewMessage.builder()
                                                                       .newMessage(userName + "さんが入室しました")
                                                                       // TODO: ハードコードすんません
                                                                       .userName("[システム(^o^)]")
                                                                       .now(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                                                                       .build());
        response.addCookie(new Cookie(cookieNameProperties.getUserCookie(), cookieValue));
        return "redirect:/form";
    }

    @GetMapping("/websocket/entrance")
    public String formWebsocket() {
        return "pages/entranceWebsocket";
    }

    @PostMapping("/websocket/entrance")
    public String entryWebsocket(@RequestParam String userName,
                                 Model model,
                                 HttpServletResponse response) {
        if (userName.isEmpty()) {
            model.addAttribute("userNameNull", true);
            return "pages/entranceWebsocket";
        }
        String cookieValue = userCookieValueHelper.generate();
        userService.create(cookieValue, userName);
//        TODO: いつかなおす。
        chatLogService.create(1L, userName + "さんが入室しました");
        simpMessagingTemplate.convertAndSend("/client/chat", NewMessage.builder()
                                                                       .newMessage(userName + "さんが入室しました")
                                                                       // TODO: ハードコードすんません
                                                                       .userName("[システム(^o^)]")
                                                                       .now(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                                                                       .build());
        response.addCookie(new Cookie(cookieNameProperties.getUserCookie(), cookieValue));
        return "redirect:/websocket/form";
    }
}
