package com.server.websocket;

import com.server.aggregates.repository.AggregatesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketConfig {

    @Value("${POLYGON_WS_URL}")
    private String polygonWsUrl;

    @Value("${polygon.api.key}")
    private String apiKey;

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketConnectionManager wsConnectionManager(AggregatesRepository aggregatesRepository) {
        PolygonWebSocketMessageHandler handler = new PolygonWebSocketMessageHandler(aggregatesRepository, apiKey);
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClient(), handler, polygonWsUrl);
        manager.setAutoStartup(true);
        return manager;
    }
}
