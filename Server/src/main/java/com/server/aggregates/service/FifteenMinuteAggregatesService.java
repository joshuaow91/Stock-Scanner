package com.server.aggregates.service;

import com.server.aggregates.repository.AggregatesRepository;
import com.server.aggregates.entity.Aggregates;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class FifteenMinuteAggregatesService extends AggregationAbstractClass {

    protected FifteenMinuteAggregatesService(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(List<StocksEnums> stockSymbol, TimeframeEnums timeframe) {
        List<String> stockSymbols = stockSymbol.stream().map(StocksEnums::name).toList();
        return repository.findTop15ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbols, String.valueOf(TimeframeEnums.ONE_MIN));
    }
}
