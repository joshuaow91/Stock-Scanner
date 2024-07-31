package com.server.aggregates;

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
                    new FiveMinuteAggregatesServiceAbstractClass(aggregatesRepository, aggregationCalculationService);
            case FIFTEEN_MIN ->
                    new FifteenMinuteAggregatesServiceAbstractClass(aggregatesRepository, aggregationCalculationService);
            case THIRTY_MIN ->
                    new ThirtyMinuteAggregatesServiceAbstractClass(aggregatesRepository, aggregationCalculationService);
            case SIXTY_MIN ->
                    new SixtyMinuteAggregatesServiceAbstractClass(aggregatesRepository, aggregationCalculationService);
            case DAY -> new DailyAggregatesServiceAbstractClass(aggregatesRepository, aggregationCalculationService);
            default -> throw new IllegalArgumentException("Unsupported timeframe: " + timeframe);
        };
    }
}
