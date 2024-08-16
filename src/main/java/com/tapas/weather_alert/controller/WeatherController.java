package com.tapas.weather_alert.controller;

import com.tapas.weather_alert.model.response.DailyUpdate;
import com.tapas.weather_alert.model.response.HistoricalTrend;
import com.tapas.weather_alert.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/daily_update")
    public ResponseEntity<DailyUpdate> getDailyUpdate() {
        return ResponseEntity.ok(weatherService.getDailyUpdate());
    }

    @GetMapping("/weather/historical_trend")
    public ResponseEntity<HistoricalTrend> getHistoricalTrend() {
        return ResponseEntity.ok(weatherService.getHistoricalTrend());
    }
}
