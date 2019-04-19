package com.mbi.api.repositories;

import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Test case repository.
 */
@Repository
public interface TestCaseRepository extends CrudRepository<TestCaseEntity, Integer> {

    Optional<Page<TestCaseEntity>> findAllByStatusAndTestRunEntity(String status,
                                                                   TestRunEntity testRunEntity,
                                                                   Pageable pageable);

    Optional<Page<TestCaseEntity>> findAllByTestRunEntity(TestRunEntity testRunEntity, Pageable pageable);
}
