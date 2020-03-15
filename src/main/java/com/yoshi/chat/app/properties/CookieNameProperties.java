package com.yoshi.chat.app.properties;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@ConfigurationProperties("cookie-name")
@Data
public class CookieNameProperties {
    private String userCookie;
}
