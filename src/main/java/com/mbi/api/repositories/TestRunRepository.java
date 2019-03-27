package com.mbi.api.repositories;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Test run repository.
 */
@Repository
public interface TestRunRepository extends CrudRepository<TestRunEntity, Long> {
    Optional<List<TestRunEntity>> findAllByProduct(ProductEntity product);
}
