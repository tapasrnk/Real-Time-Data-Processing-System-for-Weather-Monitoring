package com.tapas.weather_alert.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Integer> {

    @Query(value = "SELECT a.field, a.num_update, a.operation, a.value, ah.time_stamp FROM alert a JOIN alert_history ah ON a.id = ah.alter_id ORDER BY ah.time_stamp;", nativeQuery = true)
    List<Object[]> retriveAlert();

}
