package com.mbi.api.repositories;

import com.mbi.api.entities.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Product repository.
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findByName(String name);
}
