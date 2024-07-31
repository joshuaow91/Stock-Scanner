package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatesRepository extends JpaRepository<Aggregates, Long> {

    List<Aggregates> findTop5ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums stockSymbol, TimeframeEnums timeframe);
    List<Aggregates> findTop15ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums stockSymbol, TimeframeEnums timeframe);
    List<Aggregates> findTop30ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums stockSymbol, TimeframeEnums timeframe);
    List<Aggregates> findTop60ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums stockSymbol, TimeframeEnums timeframe);
    List<Aggregates> findTop390ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums stockSymbol, TimeframeEnums timeframe);
}
