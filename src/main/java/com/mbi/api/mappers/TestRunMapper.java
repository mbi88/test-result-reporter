package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.models.request.TestRunModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

public class TestRunMapper implements Mapper<TestRunEntity, TestRunModel> {

    @Override
    public TestRunEntity map(TestRunModel testRunModel) {
        var testRunEntity = new ModelMapper().map(testRunModel, TestRunEntity.class);

        var suitesSet = testRunModel.getSuites()
                .stream()
                .map(suiteModel -> {
                    var suiteEntity = new SuiteMapper().map(suiteModel);
                    suiteEntity.setTestRunEntity(testRunEntity);
                    return suiteEntity;
                })
                .collect(Collectors.toSet());

        testRunEntity.setSuites(suitesSet);

        int testsDuration = 0;
        for (var suite : testRunEntity.getSuites()) {
            testsDuration += Integer.parseInt(suite.getDuration());
        }
        testRunEntity.setDuration(testsDuration);

        return testRunEntity;
    }
}
