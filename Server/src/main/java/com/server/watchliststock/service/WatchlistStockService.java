package com.server.watchliststock.service;

import com.server.aggregates.entity.Aggregates;
import com.server.aggregates.repository.AggregatesRepository;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import com.server.watchlists.entity.Watchlists;
import com.server.watchlists.service.WatchlistsService;
import com.server.watchliststock.dto.DefaultWatchlistDTO;
import com.server.watchliststock.dto.StockResponseDTO;
import com.server.watchliststock.entity.WatchlistStock;
import com.server.watchliststock.repository.WatchlistStockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<DefaultWatchlistDTO> getDefaultWatchlistStocks(TimeframeEnums timeframe) {
        List<String> stockSymbols = getStockSymbolsByWatchlistId(1L);
        return stockSymbols.stream()
                .map(symbol -> mapToDefaultWatchlistDTO(StocksEnums.valueOf(symbol), timeframe))
                .collect(Collectors.toList());
    }

    private DefaultWatchlistDTO mapToDefaultWatchlistDTO (StocksEnums stock, TimeframeEnums timeframe) {
        List<Aggregates> recentAggregates = aggregatesRepository.findTop3ByStockSymbolAndTimeframeOrderByEndTimeDesc(stock, timeframe);

        String c1 = recentAggregates.get(0).getScenario().name();
        String c2 = recentAggregates.get(1).getScenario().name();
        String cc = recentAggregates.get(2).getScenario().name();

        Aggregates mostRecent = recentAggregates.get(0);

        return new DefaultWatchlistDTO(
                stock,
                mostRecent.getTargetPriceUp(),
                mostRecent.getTargetPriceDown(),
                mostRecent.getTriggerPriceDown(),
                mostRecent.getTriggerPriceUp(),
                c1,
                c2,
                cc
        );
    }

    public List<Aggregates> getAggregatesByWatchlistIdStockAndTimeframe(Long watchlistId, TimeframeEnums timeframe) {
        List<String> stockSymbols = getStockSymbolsByWatchlistId(watchlistId);
        return getAggregatesByStockSymbolsAndTimeframe(stockSymbols, timeframe);
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

    private List<String> getStockSymbolsByWatchlistId(Long watchlistId) {
        return watchlistStockRepository.findStockSymbolsByWatchlistId(watchlistId);
    }

    private List<Aggregates> getAggregatesByStockSymbolsAndTimeframe(List<String> stockSymbols, TimeframeEnums timeframe) {
        return aggregatesRepository.findAggregatesByStockSymbolsAndTimeframe(stockSymbols, timeframe);
    }

    private StockResponseDTO convertToDTO(WatchlistStock stock) {
        StockResponseDTO dto = new StockResponseDTO();
        dto.setId(stock.getId());
        dto.setStock(stock.getStockSymbol());
        dto.setWatchlistId(stock.getWatchlists().getId());
        return dto;
    }
}
