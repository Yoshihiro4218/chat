package com.yoshi.chat.app.controller;

import com.yoshi.chat.app.helper.*;
import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping("/entrance")
@AllArgsConstructor
public class EntranceController {
    private final UserService userService;
    private final UserCookieValueHelper userCookieValueHelper;
    private final CookieNameProperties cookieNameProperties;

    @GetMapping("")
    public String form() {
        return "pages/entrance";
    }

    @PostMapping("")
    public String entry(@RequestParam String userName,
                        Model model,
                        HttpServletResponse response) {
        if (userName.isEmpty()) {
            model.addAttribute("userNameNull", true);
            return "pages/entrance";
        }
        String cookieValue = userCookieValueHelper.generate();
        userService.create(cookieValue, userName);
        response.addCookie(new Cookie(cookieNameProperties.getUserCookie(), cookieValue));
        return "redirect:/form";
    }
}
