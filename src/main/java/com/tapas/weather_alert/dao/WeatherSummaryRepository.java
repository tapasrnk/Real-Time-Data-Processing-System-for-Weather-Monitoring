package com.tapas.weather_alert.dao;

import com.tapas.weather_alert.model.response.DailyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherSummaryRepository extends JpaRepository<WeatherSummary, Integer> {
    @Query("SELECT new com.tapas.weather_alert.model.response.DailyUpdate(" +
            "   AVG(ws.avgTemperature), " +
            "   MAX(ws.maxTemperature), " +
            "   MIN(ws.minTemperature), " +
            "   ws.dominantWeather) " +
            "FROM WeatherSummary ws " +
            "GROUP BY ws.date, ws.dominantWeather " +
            "ORDER BY ws.date")
    List<DailyUpdate> findAllDailyUpdates();
}
