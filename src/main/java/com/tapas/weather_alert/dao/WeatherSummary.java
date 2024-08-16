package com.tapas.weather_alert.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class WeatherSummary {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "local_date")
    private LocalDate date;

    @Column(name = "avg_temperature")
    private Double avgTemperature;

    @Column(name = "min_temperature")
    private Integer minTemperature;

    @Column(name = "max_temperature")
    private Integer maxTemperature;

    @Column(name = "dominant_weather")
    private String dominantWeather;

    public WeatherSummary(LocalDate date, Double avgTemperature, Integer minTemperature, Integer maxTemperature, String dominantWeather) {
        this.date = date;
        this.avgTemperature = avgTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.dominantWeather = dominantWeather;
    }
}
