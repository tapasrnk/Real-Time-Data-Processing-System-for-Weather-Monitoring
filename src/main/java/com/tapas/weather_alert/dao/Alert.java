package com.tapas.weather_alert.dao;

import com.tapas.weather_alert.model.request.Field;
import com.tapas.weather_alert.model.request.Operation;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Alert {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "field")
    @Enumerated(EnumType.STRING)
    private Field field;

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Column(name = "value")
    private String value;

    @Column(name = "num_update")
    private Integer numUpdate;

    public Alert(Field field, Operation operation, String value, Integer numUpdate) {
        this.field = field;
        this.operation = operation;
        this.value = value;
        this.numUpdate = numUpdate;
    }

    public Alert() {

    }
}
