package com.server.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.server.aggregates.Aggregates;
import com.server.aggregates.AggregatesRepository;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.server.dto.AggregatesDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolygonWebSocketMessageHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(PolygonWebSocketMessageHandler.class);

    private final AggregatesRepository aggregatesRepository;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public PolygonWebSocketMessageHandler(AggregatesRepository aggregatesRepository, String apiKey) {
        this.aggregatesRepository = aggregatesRepository;
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("{\"action\":\"auth\",\"params\":\"" + apiKey + "\"}"));
        logger.info("Connection established with session id: {}", session.getId());

        String stocks = Stream.of(StocksEnums.values())
                .map(stock -> "AM." + stock.name())
                .collect(Collectors.joining(","));
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"params\":\"%s\"}", stocks);
        session.sendMessage(new TextMessage(subscribeMessage));
        logger.info("Subscribing to stocks: {}", stocks);
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws JsonProcessingException {
        String payload = message.getPayload();
        logger.info("Received message: {}", payload);

        List<AggregatesDTO> aggregateDataList = parsePayload(payload);
        aggregateDataList.forEach(this::processAggregateDTO);
    }

    private List<AggregatesDTO> parsePayload(String payload) throws JsonProcessingException {
        return objectMapper.readValue(payload, new TypeReference<>() {});
    }

    private void processAggregateDTO(AggregatesDTO dto) {
        String rawSymbol = dto.getSym();

        if (rawSymbol == null) {
            logger.warn("Received null symbol, skipping this entry.");
            return;
        }

        try {
            StocksEnums stockEnum = StocksEnums.valueOf(rawSymbol);
            Aggregates aggregates = createAggregates(dto, stockEnum);
            saveAggregates(aggregates);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid stock symbol received: {}", rawSymbol, e);
        }
    }

    private Aggregates createAggregates(AggregatesDTO dto, StocksEnums stockEnum) {
        Aggregates aggregates = new Aggregates();
        aggregates.setStockSymbol(stockEnum);
        aggregates.setTimeframe(TimeframeEnums.ONE_MIN);
        aggregates.setOpen(dto.getO());
        aggregates.setClose(dto.getC());
        aggregates.setHigh(dto.getH());
        aggregates.setLow(dto.getL());
        aggregates.setStartTime(dto.getS());
        aggregates.setEndTime(dto.getE());
        return aggregates;
    }

    private void saveAggregates(Aggregates aggregates) {
        aggregatesRepository.save(aggregates);
    }

    @Override
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) throws Exception {
        logger.error("Transport error: {}", exception.getMessage(), exception);
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        logger.info("Connection closed with status: {}", status);
    }
}
