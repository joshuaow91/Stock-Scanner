package com.server.watchlists;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/watchlists")
public class WatchlistsController {

    private final WatchlistsService watchlistsService;

    public WatchlistsController(WatchlistsService watchlistsService) {
        this.watchlistsService = watchlistsService;
    }

    @GetMapping
    public List<Watchlists> getDefaultWatchlist() {
        return watchlistsService.getDefaultWatchlistStocks();
    }
}
