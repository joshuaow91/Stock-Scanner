package com.server.watchliststock.service;

import com.server.aggregates.entity.Aggregates;
import com.server.aggregates.repository.AggregatesRepository;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import com.server.watchlists.entity.Watchlists;
import com.server.watchlists.service.WatchlistsService;
import com.server.watchliststock.dto.StockResponseDTO;
import com.server.watchliststock.entity.WatchlistStock;
import com.server.watchliststock.repository.WatchlistStockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistStockService {

    private final WatchlistStockRepository watchlistStockRepository;
    private final WatchlistsService watchlistsService;
    private final AggregatesRepository aggregatesRepository;

    public WatchlistStockService(WatchlistStockRepository watchlistStockRepository, WatchlistsService watchlistsService, AggregatesRepository aggregatesRepository) {
        this.watchlistStockRepository = watchlistStockRepository;
        this.watchlistsService = watchlistsService;
        this.aggregatesRepository = aggregatesRepository;
    }

    public List<StocksEnums> getDefaultStocks() {
        return watchlistStockRepository.getDefaultStocksByWatchlistId(1);
    }

    public List<Aggregates> getAggregatesByWatchlistIdStockAndTimeframe(Long watchlistId, TimeframeEnums timeframe) {
        List<String> stockSymbols = watchlistStockRepository.findStockSymbolsByWatchlistId(watchlistId);
        return aggregatesRepository.findAggregatesByStockSymbolsAndTimeframe(stockSymbols, timeframe);
    }

    public StockResponseDTO addStockToWatchlist(Long watchlistId, StocksEnums stock) {
        Watchlists watchlists = watchlistsService.findWatchlistById(watchlistId);

        WatchlistStock newStock = new WatchlistStock();
        newStock.setWatchlists(watchlists);
        newStock.setStockSymbol(stock);

        watchlistStockRepository.save(newStock);
        return convertToDTO(newStock);
    }

    public void deleteStockFromWatchlist(Long watchlistId, StocksEnums stock) {
        Watchlists watchlists = watchlistsService.findWatchlistById(watchlistId);
        WatchlistStock watchlistStock = watchlistStockRepository.findByWatchlistsAndStockSymbol(watchlists, stock)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found in watchlist"));

        watchlistStockRepository.delete(watchlistStock);
    }

    private StockResponseDTO convertToDTO(WatchlistStock stock) {
        StockResponseDTO dto = new StockResponseDTO();
        dto.setId(stock.getId());
        dto.setStock(stock.getStockSymbol());
        dto.setWatchlistId(stock.getWatchlists().getId());
        return dto;
    }
}
