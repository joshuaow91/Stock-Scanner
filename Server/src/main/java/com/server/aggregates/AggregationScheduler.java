package com.server.aggregates;

import com.server.enums.StocksEnums;
import com.server.enums.TimeframeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

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
        if (isMarketOpen(0)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIVE_MIN);
            logger.info("Running aggregation for 5 minutes");
            service.execute(StocksEnums.AAPL, TimeframeEnums.ONE_MIN, TimeframeEnums.FIVE_MIN);
        }
    }

    @Scheduled(cron = "0 */15 * * * MON-FRI", zone = "America/New_York")
    public void runFifteenMinuteAggregation() {
        if (isMarketOpen(0)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIFTEEN_MIN);
            logger.info("Running aggregation for 15 minutes");
            service.execute(StocksEnums.AAPL, TimeframeEnums.ONE_MIN, TimeframeEnums.FIFTEEN_MIN);
        }
    }

    @Scheduled(cron = "0 */30 * * * MON-FRI", zone = "America/New_York")
    public void runThirtyMinuteAggregation() {
        if (isMarketOpen(30)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.THIRTY_MIN);
            logger.info("Running aggregation for 30 minutes");
            service.execute(StocksEnums.AAPL, TimeframeEnums.ONE_MIN, TimeframeEnums.THIRTY_MIN);
        }
    }

    @Scheduled(cron = "0 0 * * * MON-FRI", zone = "America/New_York")
    public void runSixtyMinuteAggregation() {
        if (isMarketOpen(60)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.SIXTY_MIN);
            logger.info("Running aggregation for 60 minutes");
            service.execute(StocksEnums.AAPL, TimeframeEnums.ONE_MIN, TimeframeEnums.SIXTY_MIN);
        }
    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "America/New_York")
    public void runDailyAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.DAY);
        logger.info("Running aggregation for Daily");
        service.execute(StocksEnums.AAPL, TimeframeEnums.ONE_MIN, TimeframeEnums.DAY);
    }

    private boolean isMarketOpen(int minutesToRunTaskAfterOpen) {
        LocalTime currentTime = LocalTime.now(ZoneId.of("America/New_York"));
        LocalTime marketOpen = LocalTime.of(9, 45).plusMinutes(minutesToRunTaskAfterOpen);
        LocalTime marketClose = LocalTime.of(16, 15);
        return !currentTime.isBefore(marketOpen) && !currentTime.isAfter(marketClose);
    }
}
