package com.yoshi.chat.app.config;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/client");
        config.setApplicationDestinationPrefixes("/server");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-endpoint")
//                TODO: ベタ書きｨ！
                .setAllowedOrigins("https://fuga.kadono.work", "https://home.kadono.work")
                .withSockJS();
    }
}
