package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class SixtyMinuteAggregatesServiceAbstractClass extends AggregationAbstractClass {

    protected SixtyMinuteAggregatesServiceAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(StocksEnums stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop60ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbol, timeframe);
    }

}
