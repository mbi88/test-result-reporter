package com.mbi.api.services;

import com.mbi.api.entities.health.HealthEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public ResponseEntity<HealthEntity> checkHealth() {
        HealthEntity healthEntity = new HealthEntity();
        healthEntity.setStatus("ok");
        healthEntity.setVersion(System.getenv("APP__VERSION"));

        return new ResponseEntity<>(healthEntity, HttpStatus.OK);
    }

}
