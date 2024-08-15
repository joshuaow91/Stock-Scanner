package com.server.websocket;

import com.server.enums.StocksEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentPriceWebSocketMessageHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(AggregatesWebSocketMessageHandler.class);

    private final String apiKey;

    public CurrentPriceWebSocketMessageHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String authMessage = "{ \"action\":\"auth\", \"params\":\"" + apiKey + "\" }";
        session.sendMessage(new TextMessage(authMessage));

        String stocks = Stream.of(StocksEnums.values())
                .map(stock -> "A." + stock.name())
                .collect(Collectors.joining(","));
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"params\":\"%s\"}", stocks);
        session.sendMessage(new TextMessage(subscribeMessage));
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        logger.info("Current price data: {}", payload);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        logger.info("Connection closed: {}", status);
    }
}
