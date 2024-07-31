package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AggregationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AggregationScheduler.class);

    private final AggregationFactory aggregationFactory;

    public AggregationScheduler(AggregationFactory aggregationFactory) {
        this.aggregationFactory = aggregationFactory;
    }

    @Scheduled(cron = "0 */5 * * * MON-FRI", zone = "America/New_York")
    public void runFiveMinuteAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIVE_MIN);
        logger.info("Running aggregation for {}", service);
        service.execute(String.valueOf(StocksEnums.AAPL), TimeframeEnums.ONE_MIN, TimeframeEnums.FIVE_MIN);

    }

    @Scheduled(cron = "0 */15 * * * MON-FRI", zone = "America/New_York")
    public void runFifteenMinuteAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIFTEEN_MIN);
        logger.info("Running aggregation for {}", service);
        service.execute(String.valueOf(StocksEnums.AAPL), TimeframeEnums.ONE_MIN, TimeframeEnums.FIFTEEN_MIN);

    }

    @Scheduled(cron = "0 */30 * * * MON-FRI", zone = "America/New_York")
    public void runThirtyMinuteAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.THIRTY_MIN);
        logger.info("Running aggregation for {}", service);
        service.execute(String.valueOf(StocksEnums.AAPL), TimeframeEnums.ONE_MIN, TimeframeEnums.THIRTY_MIN);

    }

    @Scheduled(cron = "0 0 * * * MON-FRI", zone = "America/New_York")
    public void runSixtyMinuteAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.SIXTY_MIN);
        logger.info("Running aggregation for {}", service);
        service.execute(String.valueOf(StocksEnums.AAPL), TimeframeEnums.ONE_MIN, TimeframeEnums.SIXTY_MIN);

    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "America/New_York")
    public void runDailyAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.DAY);
        logger.info("Running aggregation for {}", service);
        service.execute(String.valueOf(StocksEnums.AAPL), TimeframeEnums.ONE_MIN, TimeframeEnums.DAY);

    }
}
