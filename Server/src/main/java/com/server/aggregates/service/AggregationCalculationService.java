package com.server.aggregates.service;

import com.server.aggregates.entity.Aggregates;
import com.server.enums.TimeframeEnums;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
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
        ZonedDateTime startTime = calculateStartTime(lowerTimeframeAggregates);
        ZonedDateTime endTime = calculateEndTime(lowerTimeframeAggregates);
        double triggerUp = calculateTriggerPriceUp(lowerTimeframeAggregates);
        double triggerDown = calculateTriggerPriceDown(lowerTimeframeAggregates);
        double targetUp = calculateTargetPriceUp(lowerTimeframeAggregates);
        double targetDown = calculateTargetPriceDown(lowerTimeframeAggregates);

        aggregated.setStockSymbol(lowerTimeframeAggregates.get(0).getStockSymbol());
        aggregated.setTimeframe(targetTimeframe);
        aggregated.setHigh(high);
        aggregated.setLow(low);
        aggregated.setOpen(open);
        aggregated.setClose(close);
        aggregated.setStartTime(startTime);
        aggregated.setEndTime(endTime);
        aggregated.setTriggerPriceUp(triggerUp);
        aggregated.setTriggerPriceDown(triggerDown);
        aggregated.setTargetPriceUp(targetUp);
        aggregated.setTargetPriceDown(targetDown);

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

    private ZonedDateTime calculateStartTime(List<Aggregates> aggregates) {
        return aggregates.stream()
                .map(Aggregates::getStartTime)
                .min(ZonedDateTime::compareTo)
                .orElse(ZonedDateTime.now());
    }

    private ZonedDateTime calculateEndTime(List<Aggregates> aggregates) {
        return aggregates.stream()
                .map(Aggregates::getEndTime)
                .max(ZonedDateTime::compareTo)
                .orElse(ZonedDateTime.now());
    }

    private double calculateTriggerPriceUp(List<Aggregates> aggregates) {
        return aggregates.stream()
                .max(Comparator.comparing(Aggregates::getEndTime))
                .map(Aggregates::getHigh)
                .orElse(Double.NaN);
    }

    private double calculateTriggerPriceDown(List<Aggregates> aggregates) {
        return aggregates.stream()
                .max(Comparator.comparing(Aggregates::getEndTime))
                .map(Aggregates::getLow)
                .orElse(Double.NaN);
    }

    private double calculateTargetPriceUp(List<Aggregates> aggregates) {
        return aggregates.stream()
                .sorted(Comparator.comparing(Aggregates::getEndTime).reversed())
                .skip(1)
                .findFirst()
                .map(Aggregates::getHigh)
                .orElse(Double.NaN);
    }

    private double calculateTargetPriceDown(List<Aggregates> aggregates) {
        return aggregates.stream()
                .sorted(Comparator.comparing(Aggregates::getEndTime).reversed())
                .skip(1)
                .findFirst()
                .map(Aggregates::getLow)
                .orElse(Double.NaN);
    }
}
