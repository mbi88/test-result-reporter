package com.mbi.api.repositories;

import com.mbi.api.entities.testrun.TestRunEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunRepository extends CrudRepository<TestRunEntity, Long> {
}
