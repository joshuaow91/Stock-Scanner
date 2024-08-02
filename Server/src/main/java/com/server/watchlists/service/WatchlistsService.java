package com.server.watchlists.service;

import com.server.watchlists.entity.Watchlists;
import com.server.watchlists.repository.WatchlistsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistsService {

    private final WatchlistsRepository watchlistsRepository;

    public WatchlistsService(WatchlistsRepository watchlistsRepository) {
        this.watchlistsRepository = watchlistsRepository;
    }

    public List<Watchlists> getDefaultWatchlistStocks() {
        return watchlistsRepository.getDefaultWatchListStocksById(1);
    }
}
