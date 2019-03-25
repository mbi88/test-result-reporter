package com.mbi.api.repositories;

import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Test case repository.
 */
@Repository
public interface TestCaseRepository extends CrudRepository<TestCaseEntity, Integer> {

    Optional<List<TestCaseEntity>> findAllByStatusAndTestRunEntity(String status, TestRunEntity testRunEntity);

    Optional<List<TestCaseEntity>> findAllByTestRunEntity(TestRunEntity testRunEntity);
}
