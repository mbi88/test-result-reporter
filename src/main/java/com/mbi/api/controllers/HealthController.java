package com.mbi.api.controllers;

import com.mbi.api.entities.health.HealthEntity;
import com.mbi.api.services.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HealthController {

    @Autowired
    private HealthService service;

    @RequestMapping(method = GET, path = "/health/local", produces = "application/json")
    public ResponseEntity<HealthEntity> health() {
        return service.checkHealth();
    }
}
