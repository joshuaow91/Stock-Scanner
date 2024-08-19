package com.server.watchliststock.dto;

import com.server.enums.StocksEnums;

public class DefaultWatchlistDTO {
    private StocksEnums stockSymbol;
    private Double triggerPriceUp;
    private Double triggerPriceDown;
    private Double targetPriceUp;
    private Double targetPriceDown;
    private String c1;
    private String c2;
    private String cc;

    public DefaultWatchlistDTO(StocksEnums stockSymbol, Double triggerPriceUp, Double triggerPriceDown, Double targetPriceUp, Double targetPriceDown, String c1, String c2, String cc) {
        this.stockSymbol = stockSymbol;
        this.triggerPriceUp = triggerPriceUp;
        this.triggerPriceDown = triggerPriceDown;
        this.targetPriceUp = targetPriceUp;
        this.targetPriceDown = targetPriceDown;
        this.c1 = c1;
        this.c2 = c2;
        this.cc = cc;
    }

    public StocksEnums getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StocksEnums stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public Double getTriggerPriceUp() {
        return triggerPriceUp;
    }

    public void setTriggerPriceUp(Double triggerPriceUp) {
        this.triggerPriceUp = triggerPriceUp;
    }

    public Double getTriggerPriceDown() {
        return triggerPriceDown;
    }

    public void setTriggerPriceDown(Double triggerPriceDown) {
        this.triggerPriceDown = triggerPriceDown;
    }

    public Double getTargetPriceUp() {
        return targetPriceUp;
    }

    public void setTargetPriceUp(Double targetPriceUp) {
        this.targetPriceUp = targetPriceUp;
    }

    public Double getTargetPriceDown() {
        return targetPriceDown;
    }

    public void setTargetPriceDown(Double targetPriceDown) {
        this.targetPriceDown = targetPriceDown;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
