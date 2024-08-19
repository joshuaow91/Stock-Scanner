package com.server.watchliststock.controller;

import com.server.aggregates.entity.Aggregates;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import com.server.watchliststock.dto.DefaultWatchlistDTO;
import com.server.watchliststock.dto.StockRequestDTO;
import com.server.watchliststock.dto.StockResponseDTO;
import com.server.watchliststock.service.WatchlistStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlists")
public class WatchlistStockController {

    private final WatchlistStockService watchlistStockService;

    public WatchlistStockController(WatchlistStockService watchlistStockService) {
        this.watchlistStockService = watchlistStockService;
    }

    @GetMapping("/default/stocks/aggregates")
    public List<DefaultWatchlistDTO> getDefaultWatchlistStocks(@RequestParam TimeframeEnums timeframe) {
        return watchlistStockService.getDefaultWatchlistStocks(timeframe);
    }

    @GetMapping("/{watchlistId}/stocks/aggregates")
    public List<Aggregates> getAggregatesByWatchlistIdStockAndTimeframe(@PathVariable Long watchlistId, @RequestParam TimeframeEnums timeframe) {
        return watchlistStockService.getAggregatesByWatchlistIdStockAndTimeframe(watchlistId, timeframe);
    }

    @PostMapping("/{watchlistId}/stocks")
    public StockResponseDTO addStockToWatchList(@PathVariable Long watchlistId,
                                                @RequestBody StockRequestDTO request) {
        return watchlistStockService.addStockToWatchlist(watchlistId, request.getStock());
    }

    @DeleteMapping("/{watchlistId}/stocks/{stock}")
    public String deleteStockFromWatchlist(@PathVariable Long watchlistId, @PathVariable StocksEnums stock) {
        watchlistStockService.deleteStockFromWatchlist(watchlistId, stock);
        return "Removed stock from watchlist.";
    }
}
