package com.mbi.api.services;

import com.mbi.api.entities.testrun.RunResultEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.models.response.UserCreatedModel;
import com.mbi.api.models.testrun.TestRunModel;
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

    public ResponseEntity<UserCreatedModel> parse(TestRunModel testRunModel) {
        RunResultEntity runResultEntity = new RunResultEntity();
        runResultEntity.setTotal(testRunModel.total);
        runResultEntity.setFailed(testRunModel.failed);
        runResultEntity.setPassed(testRunModel.passed);
        runResultEntity.setSkipped(testRunModel.skipped);
        runResultEntity.setIgnored(testRunModel.ignored);

        TestRunEntity testRunEntity = new TestRunEntity();
        testRunEntity.setRunResult(runResultEntity);
        testRunEntity.setName("test");

        testRunRepository.save(testRunEntity);

        UserCreatedModel userCreatedModel = new ModelMapper().map(testRunEntity, UserCreatedModel.class);

        return new ResponseEntity<>(userCreatedModel, HttpStatus.CREATED);
    }
}
