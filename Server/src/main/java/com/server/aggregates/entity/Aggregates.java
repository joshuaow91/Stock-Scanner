package com.server.aggregates.entity;

import com.server.scenarios.enums.ScenarioEnums;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

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
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "scenario")
    private ScenarioEnums scenario;

    @Column(name = "trigger_price_up")
    private double triggerPriceUp;

    @Column(name = "trigger_price_down")
    private double triggerPriceDown;

    @Column(name = "target_price_up")
    private double targetPriceUp;

    @Column(name = "target_price_down")
    private double targetPriceDown;

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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ScenarioEnums getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioEnums scenario) {
        this.scenario = scenario;
    }

    public double getTriggerPriceUp() {
        return triggerPriceUp;
    }

    public void setTriggerPriceUp(double triggerPriceUp) {
        this.triggerPriceUp = triggerPriceUp;
    }

    public double getTriggerPriceDown() {
        return triggerPriceDown;
    }

    public void setTriggerPriceDown(double triggerPriceDown) {
        this.triggerPriceDown = triggerPriceDown;
    }

    public double getTargetPriceUp() {
        return targetPriceUp;
    }

    public void setTargetPriceUp(double targetPriceUp) {
        this.targetPriceUp = targetPriceUp;
    }

    public double getTargetPriceDown() {
        return targetPriceDown;
    }

    public void setTargetPriceDown(double targetPriceDown) {
        this.targetPriceDown = targetPriceDown;
    }
}
