package com.server.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.WebSocketHandler;

@Configuration
public class WebSocketConfig {

    @Value("${POLYGON_WS_URL}")
    private String polygonWsUrl;

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        return new WebSocketMessaageHandler();
//    }

//    @Bean
//    public WebSocketConnectionManager wsConnectionManager() {
//        WebSocketConnectionManager manager = new WebSocketConnectionManager(
//                webSocketClient(), webSocketHandler(), polygonWsUrl);
//
//        manager.setAutoStartup(true);
//        return manager;
//    }
}
