package com.server.aggregates.service;

import com.server.enums.TimeframeEnums;
import com.server.watchliststock.service.WatchlistStockService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
@EnableScheduling
public class AggregationScheduler {

    private final AggregationFactory aggregationFactory;
    private final WatchlistStockService watchlistStockService;

    public AggregationScheduler(AggregationFactory aggregationFactory, WatchlistStockService watchlistStockService) {
        this.aggregationFactory = aggregationFactory;
        this.watchlistStockService = watchlistStockService;
    }

    @Scheduled(cron = "0 */1 * * * MON-FRI", zone = "America/New_York")
    public void runFiveMinuteAggregation() {
        if (isMarketOpen(0)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIVE_MIN);
            service.execute(watchlistStockService.getDefaultStocks(), TimeframeEnums.ONE_MIN, TimeframeEnums.FIVE_MIN);
        }
    }

    @Scheduled(cron = "0 */15 * * * MON-FRI", zone = "America/New_York")
    public void runFifteenMinuteAggregation() {
        if (isMarketOpen(0)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.FIFTEEN_MIN);
            service.execute(watchlistStockService.getDefaultStocks(), TimeframeEnums.ONE_MIN, TimeframeEnums.FIFTEEN_MIN);
        }
    }

    @Scheduled(cron = "0 */30 * * * MON-FRI", zone = "America/New_York")
    public void runThirtyMinuteAggregation() {
        if (isMarketOpen(30)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.THIRTY_MIN);
            service.execute(watchlistStockService.getDefaultStocks(), TimeframeEnums.ONE_MIN, TimeframeEnums.THIRTY_MIN);
        }
    }

    @Scheduled(cron = "0 0 * * * MON-FRI", zone = "America/New_York")
    public void runSixtyMinuteAggregation() {
        if (isMarketOpen(60)) {
            AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.SIXTY_MIN);
            service.execute(watchlistStockService.getDefaultStocks(), TimeframeEnums.ONE_MIN, TimeframeEnums.SIXTY_MIN);
        }
    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "America/New_York")
    public void runDailyAggregation() {
        AggregationAbstractClass service = aggregationFactory.getService(TimeframeEnums.DAY);
        service.execute(watchlistStockService.getDefaultStocks(), TimeframeEnums.ONE_MIN, TimeframeEnums.DAY);
    }

    private boolean isMarketOpen(int minutesToRunTaskAfterOpen) {
        LocalTime currentTime = LocalTime.now(ZoneId.of("America/New_York"));
        LocalTime marketOpen = LocalTime.of(9, 45).plusMinutes(minutesToRunTaskAfterOpen);
        LocalTime marketClose = LocalTime.of(19, 15);
        return !currentTime.isBefore(marketOpen) && !currentTime.isAfter(marketClose);
    }
}
