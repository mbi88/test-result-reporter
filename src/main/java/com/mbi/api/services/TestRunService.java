package com.mbi.api.services;

import com.mbi.api.mappers.TestRunMapper;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    public ResponseEntity<CreatedModel> parseTestNG(TestRunModel testRunModel) {
        var testRunEntity = new TestRunMapper().map(testRunModel);
        testRunRepository.save(testRunEntity);
        var createdModel = new ModelMapper().map(testRunEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }
}
