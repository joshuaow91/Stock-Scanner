package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class FifteenMinuteAggregatesServiceAbstractClass extends AggregationAbstractClass {

    protected FifteenMinuteAggregatesServiceAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop15ByStockSymbolAndTimeframeOrderByStartTimeDesc(StocksEnums.valueOf(stockSymbol), TimeframeEnums.ONE_MIN);
    }

}
