package com.server.watchliststock;

import com.server.enums.StocksEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistStockRepository extends JpaRepository<WatchlistStock, Long> {

    @Query(value = "SELECT * FROM watchlist_stock WHERE id = 1", nativeQuery = true)
    List<StocksEnums> getDefaultStocksByWatchlistId(int id);
}
