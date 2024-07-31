package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class FiveMinuteAggregatesServiceAbstractClass extends AggregationAbstractClass {

    protected FiveMinuteAggregatesServiceAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(StocksEnums stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop5ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbol, TimeframeEnums.ONE_MIN);
    }

}
