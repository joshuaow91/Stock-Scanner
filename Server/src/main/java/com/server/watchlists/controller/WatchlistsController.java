package com.server.watchlists.controller;

import com.server.watchlists.dto.WatchlistResponseDTO;
import com.server.watchlists.dto.WatchlistRequestDTO;
import com.server.watchlists.service.WatchlistsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlists")
public class WatchlistsController {

    private final WatchlistsService watchlistsService;

    public WatchlistsController(WatchlistsService watchlistsService) {
        this.watchlistsService = watchlistsService;
    }

    @GetMapping("/user/{userId}")
    public List<WatchlistResponseDTO> getAllWatchlistsByUserId(@PathVariable Long userId) {
        return watchlistsService.getAllWatchlistsByUserId(userId);
    }

    @PostMapping
    public WatchlistResponseDTO createWatchlist(@RequestBody WatchlistRequestDTO request) {
        return watchlistsService.createWatchList(request);
    }

    @PutMapping("/{id}")
    public WatchlistResponseDTO updateWatchlistName(@PathVariable Long id, @RequestBody WatchlistRequestDTO request) {
        return watchlistsService.updateWatchlistName(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteWatchlist(@PathVariable Long id) {
        watchlistsService.deleteWatchlist(id);
        return "Watchlist Deleted";
    }
}
