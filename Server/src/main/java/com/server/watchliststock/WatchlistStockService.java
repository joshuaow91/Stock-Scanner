package com.server.watchliststock;

import com.server.enums.StocksEnums;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistStockService {

    private final WatchlistStockRepository watchlistStockRepository;

    public WatchlistStockService(WatchlistStockRepository watchlistStockRepository) {
        this.watchlistStockRepository = watchlistStockRepository;
    }

    public List<StocksEnums> getDefaultStocks() {
        return watchlistStockRepository.getDefaultStocksByWatchlistId(1);
    }

    public StocksEnums addStockToWatchlist(Long watchlistId, StocksEnums stock) {
        return watchlistStockRepository.saveStockToWatchlist(watchlistId,stock);
    }
}
