package com.yoshi.chat.app.properties;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@ConfigurationProperties("chat.log")
@Data
public class ChatLogProperties {
    private int limit;
}
