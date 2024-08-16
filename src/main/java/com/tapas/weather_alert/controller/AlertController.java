package com.tapas.weather_alert.controller;

import com.tapas.weather_alert.dao.Alert;
import com.tapas.weather_alert.model.request.AlertRequest;
import com.tapas.weather_alert.model.response.TriggeredAlert;
import com.tapas.weather_alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/alert")
    public ResponseEntity<Alert> addAlert(@RequestBody AlertRequest alert) {
        return ResponseEntity.ok(alertService.addAlert(alert));
    }

    @GetMapping("/alert")
    public ResponseEntity<List<TriggeredAlert>> retriveAlert() {
        return ResponseEntity.ok(alertService.retrieveAlert());
    }

    @GetMapping("/check")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test passed (**)");
    }

}
