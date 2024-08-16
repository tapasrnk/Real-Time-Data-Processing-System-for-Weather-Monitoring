package com.tapas.weather_alert.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class AlertHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alter_id")
    private Integer alertId;

    @Column(name = "time_stamp")
    private Timestamp timestamp;

    public AlertHistory() {
    }

    public AlertHistory(Integer alertId, Timestamp timestamp) {
        this.alertId = alertId;
        this.timestamp = timestamp;
    }
}
