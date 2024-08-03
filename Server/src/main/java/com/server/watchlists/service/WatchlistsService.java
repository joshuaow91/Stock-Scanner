package com.server.watchlists.service;

import com.server.users.entity.Users;
import com.server.users.repository.UserRepository;
import com.server.watchlists.dto.WatchlistResponseDTO;
import com.server.watchlists.dto.WatchlistRequestDTO;
import com.server.watchlists.entity.Watchlists;
import com.server.watchlists.repository.WatchlistsRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchlistsService {

    private final WatchlistsRepository watchlistsRepository;
    private final UserRepository userRepository;

    public WatchlistsService(WatchlistsRepository watchlistsRepository, UserRepository userRepository) {
        this.watchlistsRepository = watchlistsRepository;
        this.userRepository = userRepository;
    }

    public WatchlistResponseDTO createWatchList(WatchlistRequestDTO request) {
        Watchlists watchlist = new Watchlists();
        watchlist.setName(request.getWatchlistName());
        watchlist.setUser(findUsersById(request.getUserId()));
        Watchlists savedWatchlist = watchlistsRepository.save(watchlist);

        return convertToDTO(savedWatchlist);
    }

    public WatchlistResponseDTO updateWatchlistName(Long id, WatchlistRequestDTO request) {
        Watchlists watchlist = findWatchlistById(id);
        watchlist.setName(request.getWatchlistName());
        watchlist.setUser(findUsersById(request.getUserId()));
        Watchlists updatedWatchlist = watchlistsRepository.save(watchlist);

        return convertToDTO(updatedWatchlist);
    }

    public void deleteWatchlist(Long id) {
        watchlistsRepository.deleteById(id);
    }

    private Watchlists findWatchlistById(Long id) {
        return watchlistsRepository.findById(id).orElseThrow(() -> new RuntimeException("Watchlist not found"));
    }

    private Users findUsersById(Long userId) {
        return userRepository.findUsersById(userId);
    }

    private WatchlistResponseDTO convertToDTO(Watchlists watchlist) {
        WatchlistResponseDTO watchlistDTO = new WatchlistResponseDTO();
        watchlistDTO.setId(watchlist.getId());
        watchlistDTO.setWatchlistName(watchlist.getName());
        watchlistDTO.setUserId(watchlist.getUser().getId());
        return watchlistDTO;
    }
}
