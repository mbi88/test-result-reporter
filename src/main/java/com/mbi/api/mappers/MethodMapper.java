package com.mbi.api.mappers;

import com.mbi.api.entities.testrun.MethodEntity;
import com.mbi.api.models.request.MethodModel;
import org.modelmapper.ModelMapper;

import java.util.Objects;

/**
 * Method mapper.
 */
class MethodMapper implements Mapper<MethodEntity, MethodModel> {

    @Override
    public MethodEntity map(final MethodModel methodModel) {
        final var methodEntity = new ModelMapper().map(methodModel, MethodEntity.class);

        if (Objects.nonNull(methodModel.getException())) {
            methodEntity.setException(methodModel.getException().getException().trim());
        }

        return methodEntity;
    }
}
