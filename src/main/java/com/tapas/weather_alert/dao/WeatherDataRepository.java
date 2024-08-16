package com.tapas.weather_alert.dao;

import com.tapas.weather_alert.model.response.DailyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
    @Query(value = "WITH weather_summary AS ( " +
            "    SELECT DATE(time_stamp) AS date, " +
            "           weather, " +
            "           COUNT(*) AS weather_count " +
            "    FROM weather_data " +
            "    GROUP BY DATE(time_stamp), weather " +
            "), " +
            "dominant_weather AS ( " +
            "    SELECT date, " +
            "           weather AS dominant_weather " +
            "    FROM weather_summary " +
            "    WHERE (date, weather_count) IN ( " +
            "        SELECT date, MAX(weather_count) " +
            "        FROM weather_summary " +
            "        GROUP BY date " +
            "    ) " +
            "), " +
            "intermediate_summary AS ( " +
            "    SELECT DATE(wd.time_stamp) AS date, " +
            "           AVG(wd.temperature) AS avg_temperature, " +
            "           MIN(wd.temperature) AS min_temperature, " +
            "           MAX(wd.temperature) AS max_temperature, " +
            "           dw.dominant_weather " +
            "    FROM weather_data wd " +
            "    JOIN dominant_weather dw " +
            "    ON DATE(wd.time_stamp) = dw.date " +
            "    GROUP BY DATE(wd.time_stamp), dw.dominant_weather " +
            ") " +
            "SELECT * " +
            "FROM intermediate_summary " +
            "WHERE date = CURDATE() - INTERVAL 1 DAY",
            nativeQuery = true)
    List<Object[]> retrieveWeatherSummaryNative();

    @Query(value = "WITH weather_summary AS ( " +
            "    SELECT DATE(time_stamp) AS date, " +
            "           weather, " +
            "           COUNT(*) AS weather_count " +
            "    FROM weather_data " +
            "    GROUP BY DATE(time_stamp), weather " +
            "), " +
            "dominant_weather AS ( " +
            "    SELECT date, " +
            "           weather AS dominant_weather " +
            "    FROM weather_summary " +
            "    WHERE (date, weather_count) IN ( " +
            "        SELECT date, MAX(weather_count) " +
            "        FROM weather_summary " +
            "        GROUP BY date " +
            "    ) " +
            "), " +
            "intermediate_summary AS ( " +
            "    SELECT DATE(wd.time_stamp) AS date, " +
            "           AVG(wd.temperature) AS avg_temperature, " +
            "           MIN(wd.temperature) AS min_temperature, " +
            "           MAX(wd.temperature) AS max_temperature, " +
            "           dw.dominant_weather " +
            "    FROM weather_data wd " +
            "    JOIN dominant_weather dw " +
            "    ON DATE(wd.time_stamp) = dw.date " +
            "    GROUP BY DATE(wd.time_stamp), dw.dominant_weather " +
            ") " +
            "SELECT * " +
            "FROM intermediate_summary " +
            "WHERE date = CURDATE()",
            nativeQuery = true)
    List<Object[]> retrieveWeatherSummaryNativeTest();

    @Query(value = "SELECT * FROM weather_data WHERE time_stamp >= :time", nativeQuery = true)
    List<WeatherData> retrievePastWeatherData(Timestamp time);

    @Query("SELECT new com.tapas.weather_alert.model.response.DailyUpdate(" +
            "   AVG(w.temperature), " +
            "   MAX(w.temperature), " +
            "   MIN(w.temperature), " +
            "   (SELECT w.weather FROM WeatherData w " +
            "    WHERE DATE(w.timeStamp) = CURDATE() " +
            "    GROUP BY w.weather " +
            "    ORDER BY COUNT(*) DESC " +
            "    LIMIT 1)" +
            ") " +
            "FROM WeatherData w " +
            "WHERE DATE(w.timeStamp) = CURDATE()")
    DailyUpdate getDailyUpdate();

}
