package com.mbi.api.mappers;

public interface Mapper<E, M> {

    E map(M model);
}
