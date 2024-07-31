package com.server.aggregates;

import com.server.enums.TimeframeEnums;

import java.util.List;

public class FiveMinuteAggregatesServiceAbstractClass extends AggregationAbstractClass {

    protected FiveMinuteAggregatesServiceAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop5ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbol, String.valueOf(TimeframeEnums.ONE_MIN));
    }

}
