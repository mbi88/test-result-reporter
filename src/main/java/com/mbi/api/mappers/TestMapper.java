package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.TestEntity;
import com.mbi.api.models.request.TestModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

/**
 * Tests mapper.
 */
class TestMapper implements Mapper<TestEntity, TestModel> {

    @Override
    public TestEntity map(final TestModel testModel) {
        final var testEntity = new ModelMapper().map(testModel, TestEntity.class);

        final var classesSet = testModel.getClasses()
                .stream()
                .map(classModel -> {
                    final var classEntity = new ClassMapper().map(classModel);
                    classEntity.setTestEntity(testEntity);
                    return classEntity;
                })
                .collect(Collectors.toSet());

        testEntity.setClasses(classesSet);

        return testEntity;
    }
}
