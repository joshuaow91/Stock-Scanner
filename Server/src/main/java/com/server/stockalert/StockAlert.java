package com.server.stockalert;

import com.server.enums.AlertStatusEnums;
import com.server.enums.AlertTypeEnums;
import com.server.enums.TimeframeEnums;
import com.server.watchliststock.WatchlistStock;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "stock_alert")
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "timeframe")
    private TimeframeEnums timeframe;

    @Column(name = "alert_time")
    private Date alertTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "alert_status")
    private AlertStatusEnums alertStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "alert_type")
    private AlertTypeEnums alertType;

    @ManyToOne
    @JoinColumn(name = "watchlist_stock_id")
    private WatchlistStock watchlistStock;

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

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public AlertStatusEnums getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(AlertStatusEnums alertStatus) {
        this.alertStatus = alertStatus;
    }

    public AlertTypeEnums getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertTypeEnums alertType) {
        this.alertType = alertType;
    }

    public WatchlistStock getWatchlistStock() {
        return watchlistStock;
    }

    public void setWatchlistStock(WatchlistStock watchlistStock) {
        this.watchlistStock = watchlistStock;
    }
}
