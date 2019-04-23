package com.mbi.api.services;

import com.mbi.api.models.response.HealthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Health service.
 */
@Service
public class HealthService {

    public ResponseEntity<HealthResponse> checkHealth() {
        final var healthEntity = new HealthResponse();
        healthEntity.setStatus("ok");
        healthEntity.setVersion(System.getenv("APP__VERSION"));

        return new ResponseEntity<>(healthEntity, HttpStatus.OK);
    }

}
