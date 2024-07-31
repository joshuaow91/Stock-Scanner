package com.server.aggregates;

import com.server.enums.TimeframeEnums;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AggregationCalculationService {

    public Aggregates createAggregatesInstance() {
        return new Aggregates();
    }

    public Aggregates processAggregateCalculations(List<Aggregates> lowerTimeframeAggregates, TimeframeEnums targetTimeframe) {
        Aggregates aggregated = createAggregatesInstance();

        double high = calculateHighPrice(lowerTimeframeAggregates);
        double low = calculateLowPrice(lowerTimeframeAggregates);
        double open = calculateOpenPrice(lowerTimeframeAggregates);
        double close = calculateClosePrice(lowerTimeframeAggregates);
        LocalDateTime startTime = calculateStartTime(lowerTimeframeAggregates);
        LocalDateTime endTime = calculateEndTime(lowerTimeframeAggregates);

        aggregated.setStockSymbol(lowerTimeframeAggregates.get(0).getStockSymbol());
        aggregated.setTimeframe(targetTimeframe);
        aggregated.setHigh(high);
        aggregated.setLow(low);
        aggregated.setOpen(open);
        aggregated.setClose(close);
        aggregated.setStartTime(startTime);
        aggregated.setEndTime(endTime);

        return aggregated;
    }


    private double calculateHighPrice(List<Aggregates> aggregates) {
        return aggregates.stream()
                .mapToDouble(Aggregates::getHigh)
                .max()
                .orElse(Double.NaN);
    }

    private double calculateLowPrice(List<Aggregates> aggregates) {
        return aggregates.stream()
                .mapToDouble(Aggregates::getLow)
                .min()
                .orElse(Double.NaN);
    }

    private double calculateOpenPrice(List<Aggregates> aggregates) {
        return aggregates.stream()
                .min(Comparator.comparing(Aggregates::getStartTime))
                .map(Aggregates::getOpen)
                .orElse(Double.NaN);
    }

    private double calculateClosePrice(List<Aggregates> aggregates) {
        return aggregates.stream()
                .max(Comparator.comparing(Aggregates::getEndTime))
                .map(Aggregates::getClose)
                .orElse(Double.NaN);
    }

    private LocalDateTime calculateStartTime(List<Aggregates> aggregates) {
        return aggregates.stream()
                .map(Aggregates::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MIN); // Use a default value if necessary
    }

    private LocalDateTime calculateEndTime(List<Aggregates> aggregates) {
        return aggregates.stream()
                .map(Aggregates::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MAX); // Use a default value if necessary
    }
}
