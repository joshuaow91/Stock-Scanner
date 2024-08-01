package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;

import java.util.List;

public class DailyAggregatesServiceAbstractClass extends AggregationAbstractClass {

    protected DailyAggregatesServiceAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        super(repository, aggregationCalculationService);
    }

    @Override
    protected List<Aggregates> queryAggregates(List<StocksEnums> stockSymbol, TimeframeEnums timeframe) {
        return repository.findTop390ByStockSymbolAndTimeframeOrderByStartTimeDesc(stockSymbol, TimeframeEnums.ONE_MIN);
    }

}
