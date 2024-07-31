package com.server.aggregates;

import com.server.enums.TimeframeEnums;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregateServiceTest {

    @InjectMocks
    private AggregationCalculationService aggregationCalculationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void processCalculationsTest() {
        List<Aggregates> aggregatesList = new ArrayList<>();
        Aggregates agg1 = new Aggregates();
        agg1.setHigh(198.20);
        agg1.setLow(100.32);
        agg1.setOpen(99.00);
        agg1.setClose(99.80);
        agg1.setStartTime(LocalDateTime.of(2024, 7, 29, 9, 30));
        agg1.setEndTime(LocalDateTime.of(2024, 7, 29, 9, 31));
        Aggregates agg2 = new Aggregates();
        agg2.setHigh(192.20);
        agg2.setLow(110.32);
        agg2.setOpen(119.00);
        agg2.setClose(119.80);
        agg2.setStartTime(LocalDateTime.of(2024, 7, 29, 9, 31));
        agg2.setEndTime(LocalDateTime.of(2024, 7, 29, 9, 32));
        aggregatesList.add(agg1);
        aggregatesList.add(agg2);

        Aggregates result = aggregationCalculationService.processAggregateCalculations(aggregatesList, TimeframeEnums.FIVE_MIN);
        assertEquals(198.20, result.getHigh());
        assertEquals(100.32, result.getLow());
        assertEquals(99.00, result.getOpen());
        assertEquals(119.80, result.getClose());
        assertEquals(LocalDateTime.of(2024, 7, 29, 9, 30), result.getStartTime());
        assertEquals(LocalDateTime.of(2024, 7, 29, 9, 32), result.getEndTime());
    }

//    @Test
//    public void calculateHighTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setHigh(198.20);
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setHigh(192.20);
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(198.20, result.getHigh());
//    }
//
//    @Test
//    public void calculateLowTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setLow(110.32);
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setLow(100.32);
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(100.32, result.getLow());
//    }
//
//    @Test
//    public void calculateOpenTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setOpen(119.00);
//        agg1.setStartTime(Timestamp.valueOf("2024-07-29 09:30:00"));
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setOpen(118.00);
//        agg2.setStartTime(Timestamp.valueOf("2024-07-29 09:31:00"));
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(119.00, result.getOpen());
//    }
//
//    @Test
//    public void calculateCloseTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setClose(119.00);
//        agg1.setEndTime(Timestamp.valueOf("2024-07-29 09:31:00"));
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setClose(118.00);
//        agg2.setEndTime(Timestamp.valueOf("2024-07-29 09:32:00"));
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(118.00, result.getClose());
//    }
//
//    @Test
//    public void calculateStartTimeTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setStartTime(Timestamp.valueOf("2024-07-29 09:30:00"));
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setStartTime(Timestamp.valueOf("2024-07-29 09:31:00"));
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(Timestamp.valueOf("2024-07-29 09:30:00"), result.getStartTime());
//    }
//
//    @Test
//    public void calculateEndTimeTest() {
//        List<Aggregates> aggregatesList = new ArrayList<>();
//        Aggregates agg1 = new Aggregates();
//        agg1.setEndTime(Timestamp.valueOf("2024-07-29 09:30:00"));
//        aggregatesList.add(agg1);
//        Aggregates agg2 = new Aggregates();
//        agg2.setEndTime(Timestamp.valueOf("2024-07-29 09:31:00"));
//        aggregatesList.add(agg2);
//
//        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
//        assertEquals(Timestamp.valueOf("2024-07-29 09:30:00"), result.getEndTime());
//    }
}
