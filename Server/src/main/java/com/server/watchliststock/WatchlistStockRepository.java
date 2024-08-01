package com.server.watchliststock;

import com.server.enums.StocksEnums;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistStockRepository extends JpaRepository<WatchlistStock, Long> {

    @Query(value = "SELECT * FROM watchlist_stock WHERE id = 1", nativeQuery = true)
    List<StocksEnums> getDefaultStocksByWatchlistId(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO watchlist_stock (watchlist_id, stock_symbol) VALUES (:watchlistId, :stockSymbol)", nativeQuery = true)
    StocksEnums saveStockToWatchlist(@Param("watchlistId") Long watchlistId, @Param("stock") StocksEnums stock);
}
