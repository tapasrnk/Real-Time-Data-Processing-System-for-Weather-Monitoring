package com.tapas.weather_alert.model.request;

import lombok.Data;

@Data
public class AlertRequest {
    private Field field;
    private Operation operation;
    private String value;
    private Integer numUpdate;
}
