package com.server.aggregates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatesRepository extends JpaRepository<Aggregates, Long> {

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol = :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 5", nativeQuery = true)
    List<Aggregates> findTop5ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") String stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol = :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 15", nativeQuery = true)
    List<Aggregates> findTop15ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") String stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol = :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 30", nativeQuery = true)
    List<Aggregates> findTop30ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") String stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol = :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 60", nativeQuery = true)
    List<Aggregates> findTop60ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") String stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol = :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 390", nativeQuery = true)
    List<Aggregates> findTop390ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") String stockSymbol, @Param("timeframe") String timeframe);
}
