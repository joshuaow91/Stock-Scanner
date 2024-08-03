package com.server.watchliststock.dto;

import com.server.enums.StocksEnums;

public class StockResponseDTO {
    private StocksEnums stock;
    private Long id;
    private Long watchlistId;

    public StocksEnums getStock() {
        return stock;
    }

    public void setStock(StocksEnums stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(Long watchlistId) {
        this.watchlistId = watchlistId;
    }
}
