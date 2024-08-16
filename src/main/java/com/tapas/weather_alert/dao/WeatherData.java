package com.tapas.weather_alert.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class WeatherData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weather")
    private String weather;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "feels_like")
    private Integer feelsLike;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;
}
