package com.tapas.weather_alert.model.response;

import lombok.Data;

import java.util.List;

@Data
public class HistoricalTrend {
    private List<DailyUpdate> trends;

    public HistoricalTrend(List<DailyUpdate> trends) {
        this.trends = trends;
    }
}
