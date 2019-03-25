package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.SuiteEntity;
import com.mbi.api.models.request.SuiteModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

class SuiteMapper implements Mapper<SuiteEntity, SuiteModel> {

    @Override
    public SuiteEntity map(SuiteModel suiteModel) {
        var suiteEntity = new ModelMapper().map(suiteModel, SuiteEntity.class);

        var testsSet = suiteModel.getTests()
                .stream()
                .map(testModel -> {
                    var testEntity = new TestMapper().map(testModel);
                    testEntity.setSuiteEntity(suiteEntity);
                    return testEntity;
                })
                .collect(Collectors.toSet());

        suiteEntity.setTests(testsSet);

        return suiteEntity;
    }
}
