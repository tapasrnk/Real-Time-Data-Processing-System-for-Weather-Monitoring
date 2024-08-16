package com.tapas.weather_alert.service;

import com.tapas.weather_alert.dao.WeatherDataRepository;
import com.tapas.weather_alert.dao.WeatherSummary;
import com.tapas.weather_alert.dao.WeatherSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class Aggregate {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherSummaryRepository weatherSummaryRepository;

    @Autowired
    public Aggregate(WeatherDataRepository weatherDataRepository, WeatherSummaryRepository weatherSummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherSummaryRepository = weatherSummaryRepository;
    }

    @Scheduled(cron = "0 1 0 * * *")
//    @Scheduled(fixedRate = 3000) // uncomment for testing
    public void AggregateData() {
        List<Object[]> results = weatherDataRepository.retrieveWeatherSummaryNative();
//        List<Object>[] results = weatherDataRepository.retrieveWeatherSummaryNativeTest(); // uncomment for testing
        for (Object[] result : results) {
            LocalDate date = Date.valueOf(result[0].toString()).toLocalDate();
            Double avgTemperature = ((BigDecimal) result[1]).doubleValue();
            Integer minTemperature = (Integer) result[2];
            Integer maxTemperature = (Integer) result[3];
            String dominantWeather = (String) result[4];
            WeatherSummary summary = new WeatherSummary(date, avgTemperature, minTemperature, maxTemperature, dominantWeather);
            weatherSummaryRepository.save(summary);
        }
    }
}
