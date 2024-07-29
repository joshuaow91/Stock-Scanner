package com.server.watchliststock;

import com.server.enums.StocksEnums;
import com.server.stockalert.StockAlert;
import com.server.watchlists.Watchlists;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "watchlist_stock")
public class WatchlistStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "watchlist_id")
    private Watchlists watchlists;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_symbol")
    private StocksEnums stockSymbol;

    @OneToMany (mappedBy = "watchlistStock")
    private List<StockAlert> stockAlert;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Watchlists getWatchlists() {
        return watchlists;
    }

    public void setWatchlists(Watchlists watchlists) {
        this.watchlists = watchlists;
    }

    public StocksEnums getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StocksEnums stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public List<StockAlert> getStockAlert() {
        return stockAlert;
    }

    public void setStockAlert(List<StockAlert> stockAlert) {
        this.stockAlert = stockAlert;
    }
}
