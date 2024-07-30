package com.server.aggregates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AggregatesService {

//    private final AggregatesRepository aggregatesRepository;
//
//    @Autowired
//    public AggregatesService(AggregatesRepository aggregatesRepository) {
//        this.aggregatesRepository = aggregatesRepository;
//    }

    public Aggregates createAggregatesInstance() {
        return new Aggregates();
    }

    public Aggregates processAggregateCalculations(List<Aggregates> lowerTimeframeAggregates) {
        Aggregates aggregated = new Aggregates();

        double high = calculateHighPrice(lowerTimeframeAggregates);
        double low = calculateLowPrice(lowerTimeframeAggregates);
        double open = calculateOpenPrice(lowerTimeframeAggregates);
        double close = calculateClosePrice(lowerTimeframeAggregates);
//        Date startTime = calculateStartTime(lowerTimeframeAggregates);
//        Date endTime = calculateEndTime(lowerTimeframeAggregates);

        aggregated.setHigh(high);
        aggregated.setLow(low);
        aggregated.setOpen(open);
        aggregated.setClose(close);
//        aggregated.setStartTime(startTime);
//        aggregated.setEndTime(endTime);

        return aggregated;
    }

    private static double calculateHighPrice(List<Aggregates> aggregates) {

        return 100.00;
    }

    private double calculateLowPrice(List<Aggregates> aggregates) {
        return 100.00;
    }

    private double calculateOpenPrice(List<Aggregates> aggregates) {
        return 100.00;
    }

    private double calculateClosePrice(List<Aggregates> aggregates) {
        return 100.00;
    }

//    private Date calculateStartTime(List<Aggregates> aggregates) {
//
//    }
//
//    private Date calculateEndTime(List<Aggregates> aggregates) {
//
//    }
}
