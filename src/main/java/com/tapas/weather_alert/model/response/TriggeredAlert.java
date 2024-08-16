package com.tapas.weather_alert.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TriggeredAlert {
    private String field;
    private String operation;
    private String value;
    private Integer numUpdate;
    private Long trigger;
}
