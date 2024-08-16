package com.tapas.weather_alert.model.response;

import lombok.Data;

@Data
public class DailyUpdate {
    private Double averageTemperature;
    private Integer maximumTemperature;
    private Integer minimumTemperature;
    private String dominateWeather;

    public DailyUpdate(Double averageTemperature, Integer maximumTemperature, Integer minimumTemperature, String dominateWeather) {
        this.averageTemperature = averageTemperature;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
        this.dominateWeather = dominateWeather;
    }
}
