//package com.server.aggregates;
//
//import com.server.enums.TimeframeEnums;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AggregationFactory {
//
//    protected static AggregationConversion getService(TimeframeEnums timeframe) {
//        switch (timeframe) {
//            case FIVE_MIN:
//                return new FiveMinuteAggregationService();
//            case FIFTEEN_MIN:
//                return new FifteenMinuteAggregationService();
//            case THIRTY_MIN:
//                return new ThirtyMinuteAggregationService();
//            case SIXTY_MIN:
//                return new SixtyMinuteAggregationService();
//            case DAY:
//                return new DailyAggregationService();
//        }
//    }
//}
