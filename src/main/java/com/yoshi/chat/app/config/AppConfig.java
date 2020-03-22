package com.yoshi.chat.app.config;

import org.springframework.context.annotation.*;

import java.time.*;

@Configuration
public class AppConfig {

    @Bean
    public ZoneId zoneId() {
        return ZoneId.of("Asia/Tokyo");
    }

    @Bean
    public Clock clock(ZoneId zoneId) {
        return Clock.system(zoneId);
    }
}
