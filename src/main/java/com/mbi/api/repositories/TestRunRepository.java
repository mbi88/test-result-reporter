package com.mbi.api.repositories;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Test run repository.
 */
@Repository
public interface TestRunRepository extends CrudRepository<TestRunEntity, Integer> {

    Optional<List<TestRunEntity>> findAllByProduct(ProductEntity product);

    @Query(value = "\n"
            + "SELECT t.lag\n"
            + "FROM (SELECT id, lag(id) OVER (ORDER BY Id) as lag FROM test_runs WHERE product_id = ?2) t\n"
            + "WHERE id = ?1",
            nativeQuery = true)
    Optional<Integer> findPreviousById(int id, int productId);
}
