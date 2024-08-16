# Real-Time-Data-Processing-System-for-Weather-Monitoring
## Design 
###### Tables:
    1. alert
      ![image](https://github.com/user-attachments/assets/1ab95230-681e-47a1-934c-7b282ba1554d)

    2. alert_history
      ![image](https://github.com/user-attachments/assets/e1a3b8b2-32c6-46dc-8243-96a2338321b6)

    3. weather_data
      ![image](https://github.com/user-attachments/assets/013a75f1-81ff-4fc0-ac43-c1778fc81758)

    4. weather_summary
      ![image](https://github.com/user-attachments/assets/22b05ddc-28db-42b2-8248-42a7543c106a)

## Test in local
1. Start my-sql database on port 3306.
    - One way is to start my-sql on docker container.
2. Create database name `weather_alert`
    ```
    create database weather_alert;
    ```
3. Set the admin username, admin password and your-api-key in application.properties.
    ```
    spring.datasource.username=root
    spring.datasource.password=password
    api.key=your_api_key_here
    ```
4. Uncomment the followings.
    - `@Scheduled(fixedRate = 2000) // uncomment this for testing` in `com.tapas.weather_alert.service.ApiFetchService.java`
    - `@Scheduled(fixedRate = 3000) // uncomment for testing` in `com.tapas.weather_alert.service.Aggregate.java`
    - `List<Object>[] results = weatherDataRepository.retrieveWeatherSummaryNativeTest(); // uncomment for testing` in `com.tapas.weather_alert.service.Aggregate.java`
    - `Integer UPDATE_TIME = 2000; // uncomment for testing` in `package com.tapas.weather_alert.service.AlertService.java;`
5. Comment the followings.
    -`@Scheduled(fixedRate = 5 * 60 * 1000)` in `com.tapas.weather_alert.service.ApiFetchService.java`
    - `@Scheduled(cron = "0 1 0 * * *")` in `com.tapas.weather_alert.service.Aggregate.java`
    - `List<Object[]> results = weatherDataRepository.retrieveWeatherSummaryNative();` in `com.tapas.weather_alert.service.Aggregate.java`
    - `Integer UPDATE_TIME = 5 * 60 * 1000;` in `package com.tapas.weather_alert.service.AlertService.java;`
6. Run the application on port 8080
7. Creating a alert by sending POST reqeust to () using the curl command
    ```
        curl --location 'http://localhost:8080/alert' \
        --header 'Content-Type: application/json' \
        --data '{
            "field":"TEMPERATURE",
            "operation":"GT",
            "value":"250",
            "numUpdate":2
        }'
    ```
8. Retrive all alerts by sending GET request to () using the curl command
    ```
    curl --location 'http://localhost:8080/alert'
    ```
9. Get the daily update by sending GET request to () using the curl command
    ```
    curl --location 'http://localhost:8080/weather/daily_update'
    ```
10. Get the historical trend by sending GET reqest to () using the curl command
    ```
    curl --location 'http://localhost:8080/weather/historical_trend'
    ```
