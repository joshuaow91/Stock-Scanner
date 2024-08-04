package com.server.aggregates.repository;

import java.util.List;

import com.server.aggregates.entity.Aggregates;
import com.server.enums.TimeframeEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatesRepository extends JpaRepository<Aggregates, Long> {

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol IN :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 5", nativeQuery = true)
    List<Aggregates> findTop5ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") List<String> stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol IN :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 15", nativeQuery = true)
    List<Aggregates> findTop15ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") List<String> stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol IN :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 30", nativeQuery = true)
    List<Aggregates> findTop30ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") List<String> stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol IN :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 60", nativeQuery = true)
    List<Aggregates> findTop60ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") List<String> stockSymbol, @Param("timeframe") String timeframe);

    @Query(value = "SELECT * FROM aggregates WHERE stock_symbol IN :stockSymbol AND timeframe = :timeframe ORDER BY start_time DESC LIMIT 390", nativeQuery = true)
    List<Aggregates> findTop390ByStockSymbolAndTimeframeOrderByStartTimeDesc(@Param("stockSymbol") List<String> stockSymbol, @Param("timeframe") String timeframe);

    @Query("SELECT a FROM Aggregates a WHERE a.stockSymbol IN :stockSymbols AND a.timeframe = :timeframe")
    List<Aggregates> findAggregatesByStockSymbolsAndTimeframe(@Param("stockSymbols") List<String> stockSymbols, @Param("timeframe") TimeframeEnums timeframe);
}
