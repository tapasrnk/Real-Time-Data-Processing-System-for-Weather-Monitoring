package com.tapas.weather_alert.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapas.weather_alert.dao.WeatherData;
import com.tapas.weather_alert.dao.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiFetchService {

    private static final Logger logger = LoggerFactory.getLogger(ApiFetchService.class);
    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AlertService alertService;
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public ApiFetchService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
//    @Scheduled(fixedRate = 2000) // uncomment this for testing
    public void fetchData() throws JsonProcessingException {
        System.out.println("[FETCH_INTERVAL]:" + "${app.fetchIntervalInMiliSec}");
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Hyderabad,IN&appid=" + apiKey;

        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        for (JsonNode jsonNodeWeather : rootNode.path("weather")) {
            WeatherData weatherData = new WeatherData();
            weatherData.setTemperature(rootNode.path("main").path("temp").asInt());
            weatherData.setFeelsLike(rootNode.path("main").path("feels_like").asInt());
            weatherData.setTimeStamp(new java.sql.Timestamp(rootNode.path("dt").asLong() * 1000L));
            weatherData.setWeather(jsonNodeWeather.path("main").asText());
            weatherDataRepository.save(weatherData);
        }

        alertService.sendAlert();
    }
}
