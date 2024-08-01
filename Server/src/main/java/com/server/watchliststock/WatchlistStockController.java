package com.server.watchliststock;

import com.server.enums.StocksEnums;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class WatchlistStockController {

    private final WatchlistStockService watchlistStockService;

    public WatchlistStockController(WatchlistStockService watchlistStockService) {
        this.watchlistStockService = watchlistStockService;
    }

    @GetMapping
    public List<StocksEnums> getDefaultStocks() {
        return watchlistStockService.getDefaultStocks();
    }
}
