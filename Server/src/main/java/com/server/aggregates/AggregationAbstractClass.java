package com.server.aggregates;

import com.server.enums.TimeframeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AggregationAbstractClass {

    private static final Logger logger = LoggerFactory.getLogger(AggregationAbstractClass.class);

    protected final AggregatesRepository repository;
    private final AggregationCalculationService aggregationCalculationService;

    protected AggregationAbstractClass(AggregatesRepository repository, AggregationCalculationService aggregationCalculationService) {
        this.repository = repository;
        this.aggregationCalculationService = aggregationCalculationService;
    }

    protected abstract List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe);

    protected Aggregates convertToHigherTimeframe(List<Aggregates> lowerTimeframe, TimeframeEnums targetTimeframe) {
        return aggregationCalculationService.processAggregateCalculations(lowerTimeframe, targetTimeframe);
    }

    protected void saveAggregates(Aggregates higherTimeframe) {
        repository.save(higherTimeframe);
    }

    public void execute(String stockSymbol, TimeframeEnums timeframe, TimeframeEnums targetTimeframe) {
        List<Aggregates> lowerTimeframe = queryAggregates(stockSymbol, timeframe);
        Aggregates higherTimeframe = convertToHigherTimeframe(lowerTimeframe, targetTimeframe);
        logger.info("Converted to higher timeframe: {}", higherTimeframe);
        saveAggregates(higherTimeframe);
    }
}
