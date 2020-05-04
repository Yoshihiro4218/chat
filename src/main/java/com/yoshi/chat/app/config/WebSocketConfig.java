package com.yoshi.chat.app.config;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.*;

// https://qiita.com/xx2xyyy/items/ef083a29d4405b9845b2

@Configuration
@EnableWebSocketMessageBroker //STOMPを用いたブローカーベースのWebSocketを有効化する　
//古い資料だとAbstractWebSocketMessageBrokerConfigurerを使えと書いてあるが、今はこちら。
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");

//        config.enableSimpleBroker("/queue");//サーバーサイドから送り返す先のprefix
//        config.setApplicationDestinationPrefixes("/app");//MessageMappingのprefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();

//        registry.addEndpoint("/ws").withSockJS();//ブローカーに管理させるエンドポイント。クライアントサイドでSockJsを利用する場合は'/ws'
    }
}
