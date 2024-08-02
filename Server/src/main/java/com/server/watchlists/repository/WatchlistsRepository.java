package com.server.watchlists.repository;

import com.server.watchlists.entity.Watchlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistsRepository extends JpaRepository<Watchlists, Long> {


    List<Watchlists> getDefaultWatchListStocksById(long id);
}
