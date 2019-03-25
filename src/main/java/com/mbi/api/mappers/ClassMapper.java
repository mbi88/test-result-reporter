package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.ClassEntity;
import com.mbi.api.models.request.ClassModel;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

class ClassMapper implements Mapper<ClassEntity, ClassModel> {

    @Override
    public ClassEntity map(ClassModel classModel) {
        var classEntity = new ModelMapper().map(classModel, ClassEntity.class);

        var methodsSet = classModel.getMethods()
                .stream()
                .map(methodModel -> {
                    var methodEntity = new MethodMapper().map(methodModel);
                    methodEntity.setClassEntity(classEntity);
                    return methodEntity;
                })
                .collect(Collectors.toSet());

        classEntity.setMethods(methodsSet);

        return classEntity;
    }
}
