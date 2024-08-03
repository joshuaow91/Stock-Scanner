package com.server.watchliststock.controller;

import com.server.enums.StocksEnums;
import com.server.watchliststock.dto.StockRequestDTO;
import com.server.watchliststock.dto.StockResponseDTO;
import com.server.watchliststock.entity.WatchlistStock;
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

    @GetMapping("/default/stocks")
    public List<StocksEnums> getDefaultStocks() {
        return watchlistStockService.getDefaultStocks();
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
