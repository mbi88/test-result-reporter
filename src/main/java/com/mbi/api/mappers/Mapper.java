package com.mbi.api.mappers;

/**
 * Basic mapper.
 *
 * @param <E> entity
 * @param <M> response model
 */
public interface Mapper<E, M> {

    E map(M model);
}
