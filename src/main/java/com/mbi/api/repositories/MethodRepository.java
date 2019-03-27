package com.mbi.api.repositories;

import com.mbi.api.entities.testrun.MethodEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Method repository.
 */
@Repository
public interface MethodRepository extends CrudRepository<MethodEntity, Long> {

    @Query(value = "select * from methods where status = ?1 and class_id in "
            + "(select id from classes where test_id in "
            + "(select id from tests where suite_id in "
            + "(select id from suites where test_run_id = ?2)))", nativeQuery = true)
    Optional<List<MethodEntity>> findAllByStatusAndTestRunId(String status, int id);
}
