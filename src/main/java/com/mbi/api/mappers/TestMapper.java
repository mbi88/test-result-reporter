package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.TestEntity;
import com.mbi.api.models.request.TestModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

class TestMapper implements Mapper<TestEntity, TestModel> {

    @Override
    public TestEntity map(TestModel testModel) {
        var testEntity = new ModelMapper().map(testModel, TestEntity.class);

        var classesSet = testModel.getClasses()
                .stream()
                .map(classModel -> {
                    var classEntity = new ClassMapper().map(classModel);
                    classEntity.setTestEntity(testEntity);
                    return classEntity;
                })
                .collect(Collectors.toSet());

        testEntity.setClasses(classesSet);

        return testEntity;
    }
}
