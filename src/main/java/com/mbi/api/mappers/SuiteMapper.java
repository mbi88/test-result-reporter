package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.SuiteEntity;
import com.mbi.api.models.request.SuiteModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

/**
 * Suite mapper.
 */
class SuiteMapper implements Mapper<SuiteEntity, SuiteModel> {

    @Override
    public SuiteEntity map(final SuiteModel suiteModel) {
        final var suiteEntity = new ModelMapper().map(suiteModel, SuiteEntity.class);

        final var testsSet = suiteModel.getTests()
                .stream()
                .map(testModel -> {
                    final var testEntity = new TestMapper().map(testModel);
                    testEntity.setSuiteEntity(suiteEntity);
                    return testEntity;
                })
                .collect(Collectors.toSet());

        suiteEntity.setTests(testsSet);

        return suiteEntity;
    }
}
