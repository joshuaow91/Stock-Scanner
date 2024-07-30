package com.server.aggregates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregateServiceTest {

    private AggregatesService aggregatesService;

    public AggregateServiceTest(AggregatesService aggregatesService) {
        this.aggregatesService = aggregatesService;
    }

    @BeforeEach
    public void setUp() {
        aggregatesService = new AggregatesService();
    }

    @Test
    public void processCalculationsTest() {
        List<Aggregates> aggregatesList = new ArrayList<>();
        Aggregates agg1 = new Aggregates();
        agg1.setHigh(198.20);
        agg1.setLow(100.32);
//        agg1.setOpen(99.00);
//        agg1.setClose(99.80);
        Aggregates agg2 = new Aggregates();
        agg2.setHigh(192.20);
        agg2.setLow(110.32);
//        agg2.setOpen(119.00);
//        agg2.setClose(119.80);
        aggregatesList.add(agg1);
        aggregatesList.add(agg2);

        Aggregates result = aggregatesService.processAggregateCalculations(aggregatesList);
        assertEquals(198.20, result.getHigh());
        assertEquals(100.32, result.getLow());
//        assertEquals(99.00, result.getOpen());
//        assertEquals(99.80, result.getClose());
    }
}
