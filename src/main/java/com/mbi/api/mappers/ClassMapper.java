package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.ClassEntity;
import com.mbi.api.models.request.ClassModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

/**
 * Class mapper.
 */
class ClassMapper implements Mapper<ClassEntity, ClassModel> {

    @Override
    public ClassEntity map(final ClassModel classModel) {
        final var classEntity = new ModelMapper().map(classModel, ClassEntity.class);

        final var methodsSet = classModel.getMethods()
                .stream()
                .map(methodModel -> {
                    final var methodEntity = new MethodMapper().map(methodModel);
                    methodEntity.setClassEntity(classEntity);
                    return methodEntity;
                })
                .collect(Collectors.toSet());

        classEntity.setMethods(methodsSet);

        return classEntity;
    }
}
