package com.server.watchlists.service;

import com.server.users.entity.Users;
import com.server.users.repository.UserRepository;
import com.server.watchlists.dto.WatchlistResponseDTO;
import com.server.watchlists.dto.WatchlistRequestDTO;
import com.server.watchlists.entity.Watchlists;
import com.server.watchlists.repository.WatchlistsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistsService {

    private final WatchlistsRepository watchlistsRepository;
    private final UserRepository userRepository;

    public WatchlistsService(WatchlistsRepository watchlistsRepository, UserRepository userRepository) {
        this.watchlistsRepository = watchlistsRepository;
        this.userRepository = userRepository;
    }

    public List<WatchlistResponseDTO> getAllWatchlistsByUserId (Long userId) {
        List<Watchlists> watchlists = watchlistsRepository.findByUserId(userId);
        return watchlists.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    public Watchlists findWatchlistById(Long id) {
        return watchlistsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Watchlist not found"));
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
