//package com.server.aggregates;
//
//import com.server.enums.TimeframeEnums;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AggregationScheduler {
//
//    private final AggregationFactory aggregationFactory;
//
//    public AggregationScheduler(AggregationFactory aggregationFactory) {
//        this.aggregationFactory = aggregationFactory;
//    }
//
//    @Scheduled(cron = "0 */5 * * * MON-FRI", zone = "America/New_York")
//    public void runFiveMinuteAggregation() {
//        AggregationConversion service = AggregationFactory.getService(TimeframeEnums.FIVE_MIN);
//        // retrieve user, get all watchlists and stocks
//        //if no user then process default watchlist
//        service.execute();
//
//    }
//}
