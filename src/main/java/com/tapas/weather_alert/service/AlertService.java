package com.tapas.weather_alert.service;

import com.tapas.weather_alert.dao.*;
import com.tapas.weather_alert.model.request.AlertRequest;
import com.tapas.weather_alert.model.request.Field;
import com.tapas.weather_alert.model.request.Operation;
import com.tapas.weather_alert.model.response.TriggeredAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {
//    Integer UPDATE_TIME = 2000; // uncomment for testing
    Integer UPDATE_TIME = 5 * 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);
    private final AlertRepository alertRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final AlertHistoryRepository alertHistoryRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository, WeatherDataRepository weatherDataRepository, AlertHistoryRepository alertHistoryRepository) {
        this.alertRepository = alertRepository;
        this.weatherDataRepository = weatherDataRepository;
        this.alertHistoryRepository = alertHistoryRepository;
    }

    public Alert addAlert(AlertRequest alertRequest) {
        Alert alert1 = new com.tapas.weather_alert.dao.Alert(alertRequest.getField(), alertRequest.getOperation(), alertRequest.getValue(), alertRequest.getNumUpdate());
        return alertRepository.save(alert1);
    }

    public void sendAlert() {
        List<Alert> alerts = alertRepository.findAll();

        for (Alert alert : alerts) {
            Integer numUpdate = alert.getNumUpdate();
            List<WeatherData> weatherDatas = weatherDataRepository.retrievePastWeatherData(new Timestamp((System.currentTimeMillis() - ((long) UPDATE_TIME * numUpdate))));

            if (alert.getField().equals(Field.WEATHER)) {
                int weatherCount = 0;
                for (WeatherData weatherData : weatherDatas)
                    if (weatherData.getWeather().equals(alert.getValue())) weatherCount++;
                if (weatherCount == alert.getNumUpdate()) {
                    AlertHistory alertHistory = new AlertHistory(alert.getId(), new Timestamp(System.currentTimeMillis()));
                    alertHistoryRepository.save(alertHistory);
                }
            } else if (alert.getField().equals(Field.FEELS_LIKE)) {
                int validAlert = 0;

                for (WeatherData weatherData : weatherDatas) {
                    int feelsLike = weatherData.getFeelsLike();
                    int alertFeelsLike = Integer.parseInt(alert.getValue());
                    if (alert.getOperation().equals(Operation.LT) && feelsLike < alertFeelsLike) validAlert++;
                    else if (alert.getOperation().equals(Operation.GT) && feelsLike > alertFeelsLike) validAlert++;
                    else if (feelsLike == alertFeelsLike) validAlert++;
                }

                if (validAlert == weatherDatas.size()) {
                    AlertHistory alertHistory = new AlertHistory(alert.getId(), new Timestamp(System.currentTimeMillis()));
                    alertHistoryRepository.save(alertHistory);
                }
            } else if (alert.getField().equals(Field.TEMPERATURE)) {
                int validAlert = 0;

                for (WeatherData weatherData : weatherDatas) {
                    int temperature = weatherData.getTemperature();
                    int alertTemperature = Integer.parseInt(alert.getValue());
                    if (alert.getOperation().equals(Operation.LT) && temperature < alertTemperature) validAlert++;
                    else if (alert.getOperation().equals(Operation.GT) && temperature > alertTemperature) validAlert++;
                    else if (temperature == alertTemperature) validAlert++;
                }

                if (validAlert == weatherDatas.size()) {
                    AlertHistory alertHistory = new AlertHistory(alert.getId(), new Timestamp(System.currentTimeMillis()));
                    alertHistoryRepository.save(alertHistory);
                }
            }
        }
    }

    public List<TriggeredAlert> retrieveAlert() {
        List<Object[]> alertsObjects = alertHistoryRepository.retriveAlert();
        List<TriggeredAlert> triggeredAlerts = new ArrayList<>();
        for (Object[] o : alertsObjects) {
            String f = o[0].toString();
            String op = o[1].toString();
            String val = o[2].toString();
            Integer numUpdate = Integer.parseInt(o[3].toString());
            Long trigger = (((Timestamp) o[4]).getTime() / 1000);
            TriggeredAlert triggeredAlert = new TriggeredAlert(f, op, val, numUpdate, trigger);
            triggeredAlerts.add(triggeredAlert);
        }
        return triggeredAlerts;
    }
}
