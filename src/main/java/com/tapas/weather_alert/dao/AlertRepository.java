package com.tapas.weather_alert.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
