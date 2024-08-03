package com.server.watchliststock.dto;

import com.server.enums.StocksEnums;

public class StockRequestDTO {
    private StocksEnums stock;

    public StocksEnums getStock() {
        return stock;
    }

    public void setStock(StocksEnums stock) {
        this.stock = stock;
    }
}
