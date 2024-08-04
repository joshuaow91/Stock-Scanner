package com.server.watchliststock.repository;

import com.server.enums.StocksEnums;
import com.server.watchlists.entity.Watchlists;
import com.server.watchliststock.entity.WatchlistStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistStockRepository extends JpaRepository<WatchlistStock, Long> {

    @Query(value = "SELECT stock_symbol FROM watchlist_stock WHERE watchlist_id = :id", nativeQuery = true)
    List<StocksEnums> getDefaultStocksByWatchlistId(@Param("id") int id);

    @Query("SELECT ws FROM WatchlistStock ws WHERE ws.watchlists = :watchlist AND ws.stockSymbol = :stockSymbol")
    Optional<WatchlistStock> findByWatchlistsAndStockSymbol(@Param("watchlist") Watchlists watchlist, @Param("stockSymbol") StocksEnums stockSymbol);

    @Query("SELECT ws.stockSymbol FROM WatchlistStock ws WHERE ws.watchlists.id = :watchlistId")
    List<String> findStockSymbolsByWatchlistId(@Param("watchlistId") Long watchlistId);
}
