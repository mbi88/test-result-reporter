package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.models.request.TestRunModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

/**
 * Test run mapper.
 */
public class TestRunMapper implements Mapper<TestRunEntity, TestRunModel> {

    @Override
    public TestRunEntity map(final TestRunModel testRunModel) {
        final var testRunEntity = new ModelMapper().map(testRunModel, TestRunEntity.class);

        final var suitesSet = testRunModel.getSuites()
                .stream()
                .map(suiteModel -> {
                    final var suiteEntity = new SuiteMapper().map(suiteModel);
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
