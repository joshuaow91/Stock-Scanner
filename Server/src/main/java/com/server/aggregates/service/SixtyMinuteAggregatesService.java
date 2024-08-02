package com.server.aggregates.service;

import com.server.aggregates.repository.AggregatesRepository;
import com.server.aggregates.entity.Aggregates;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class SixtyMinuteAggregatesService extends AggregationAbstractClass {

    protected SixtyMinuteAggregatesService(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(List<StocksEnums> stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop60ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbol, timeframe);
    }

}
