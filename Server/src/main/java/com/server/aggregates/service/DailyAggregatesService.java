package com.server.aggregates.service;

import com.server.aggregates.repository.AggregatesRepository;
import com.server.aggregates.entity.Aggregates;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;
import java.util.stream.Collectors;

public class DailyAggregatesService extends AggregationAbstractClass {

    protected DailyAggregatesService(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(List<StocksEnums> stockSymbol, TimeframeEnums timeframe) {
        List<String> stockSymbols = stockSymbol.stream().map(StocksEnums::name).toList();
        return repository.findTop390ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbols, String.valueOf(TimeframeEnums.ONE_MIN));
    }

}
