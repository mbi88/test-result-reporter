package com.mbi.api.services;

import com.mbi.api.entities.testrun.SuiteEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    public ResponseEntity<CreatedModel> parseTestNG(TestRunModel testRunModel) {
        var testRunEntity = new ModelMapper().map(testRunModel, TestRunEntity.class);

        var suitesSet = testRunModel.getSuites()
                .stream()
                .map(model -> {
                    var suiteEntity = new ModelMapper().map(model, SuiteEntity.class);
                    suiteEntity.setTestRunEntity(testRunEntity);
                    return suiteEntity;
                })
                .collect(Collectors.toSet());

        testRunEntity.setSuites(suitesSet);

        testRunRepository.save(testRunEntity);

        var createdModel = new ModelMapper().map(testRunEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }
}
