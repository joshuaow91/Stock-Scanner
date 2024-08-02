package com.server.aggregates.service;

import com.server.aggregates.repository.AggregatesRepository;
import com.server.enums.TimeframeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AggregationFactory {

    private final AggregatesRepository aggregatesRepository;
    private final AggregationCalculationService aggregationCalculationService;

    @Autowired
    public AggregationFactory(AggregatesRepository aggregatesRepository, AggregationCalculationService aggregationCalculationService) {
        this.aggregatesRepository = aggregatesRepository;
        this.aggregationCalculationService = aggregationCalculationService;
    }

    public AggregationAbstractClass getService(TimeframeEnums timeframe) {
        return switch (timeframe) {
            case FIVE_MIN ->
                    new FiveMinuteAggregatesService(aggregatesRepository, aggregationCalculationService);
            case FIFTEEN_MIN ->
                    new FifteenMinuteAggregatesService(aggregatesRepository, aggregationCalculationService);
            case THIRTY_MIN ->
                    new ThirtyMinuteAggregatesService(aggregatesRepository, aggregationCalculationService);
            case SIXTY_MIN ->
                    new SixtyMinuteAggregatesService(aggregatesRepository, aggregationCalculationService);
            case DAY -> new DailyAggregatesService(aggregatesRepository, aggregationCalculationService);
            default -> throw new IllegalArgumentException("Unsupported timeframe: " + timeframe);
        };
    }
}
