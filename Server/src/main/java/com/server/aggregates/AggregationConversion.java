package com.server.aggregates;

import com.server.enums.TimeframeEnums;

import java.util.List;

public abstract class AggregationConversion {

    protected abstract List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe);

    protected abstract Aggregates convertToHigherTimeframe(List<Aggregates> lowerTimeframe);

    protected void saveAggregates(Aggregates higherTimeframe) {

    }

    public void execute(String stockSymbol, TimeframeEnums timeframe) {

    }
}
