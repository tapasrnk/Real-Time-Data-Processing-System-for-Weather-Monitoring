package com.tapas.weather_alert.service;

import com.tapas.weather_alert.dao.WeatherDataRepository;
import com.tapas.weather_alert.dao.WeatherSummaryRepository;
import com.tapas.weather_alert.model.response.DailyUpdate;
import com.tapas.weather_alert.model.response.HistoricalTrend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherSummaryRepository weatherSummaryRepository;

    @Autowired
    public WeatherService(WeatherDataRepository weatherDataRepository, WeatherSummaryRepository weatherSummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherSummaryRepository = weatherSummaryRepository;
    }

    public DailyUpdate getDailyUpdate() {
        return weatherDataRepository.getDailyUpdate();
    }

    public HistoricalTrend getHistoricalTrend() {
        return new HistoricalTrend(weatherSummaryRepository.findAllDailyUpdates());
    }
}
