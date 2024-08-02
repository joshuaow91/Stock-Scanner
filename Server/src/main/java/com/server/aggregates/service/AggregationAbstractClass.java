package com.server.aggregates.service;

import com.server.aggregates.entity.Aggregates;
import com.server.aggregates.repository.AggregatesRepository;
import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public abstract class AggregationAbstractClass {

    private static final Logger logger = LoggerFactory.getLogger(AggregationAbstractClass.class);

    protected final AggregatesRepository repository;
    private final AggregationCalculationService aggregationCalculationService;

    protected AggregationAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        this.repository = repository;
        this.aggregationCalculationService = aggregationCalculationService;
    }

    protected abstract List<Aggregates> queryAggregates(List<StocksEnums> stockSymbol, TimeframeEnums timeframe);

    protected Aggregates convertToHigherTimeframe(List<Aggregates> lowerTimeframe, TimeframeEnums targetTimeframe) {
        return aggregationCalculationService.processAggregateCalculations(lowerTimeframe, targetTimeframe);
    }

    protected void saveAggregates(Aggregates higherTimeframe) {
        repository.save(higherTimeframe);
    }

    public void execute(List<StocksEnums> stockSymbols, TimeframeEnums timeframe, TimeframeEnums targetTimeframe) {
        for (StocksEnums stockSymbol : stockSymbols) {
            List<Aggregates> lowerTimeframe = queryAggregates(Collections.singletonList(stockSymbol), timeframe);
            Aggregates higherTimeframe = convertToHigherTimeframe(lowerTimeframe, targetTimeframe);
            logger.info("Converted to higher timeframe: {} {}",targetTimeframe, higherTimeframe);
            saveAggregates(higherTimeframe);
        }
    }
}
