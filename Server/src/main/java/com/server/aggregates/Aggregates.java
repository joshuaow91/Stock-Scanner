package com.server.aggregates;

import com.server.enums.ScenarioEnums;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aggregates")
public class Aggregates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "timeframe")
    private TimeframeEnums timeframe;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_symbol")
    private StocksEnums stockSymbol;

    private double open;
    private double close;
    private double high;
    private double low;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "scenario")
    private ScenarioEnums scenario;

    @Column(name = "trigger_price")
    private double triggerPrice;

    @Column(name = "target_price")
    private double targetPrice;

    @Override
    public String toString() {
        return "Aggregates{" +
                "high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stockSymbol=" + stockSymbol +
                ", timeframe=" + timeframe +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeframeEnums getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(TimeframeEnums timeframe) {
        this.timeframe = timeframe;
    }

    public StocksEnums getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StocksEnums stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ScenarioEnums getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioEnums scenario) {
        this.scenario = scenario;
    }

    public double getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(double triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }
}
