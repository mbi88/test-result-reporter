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

    @Query(value = "\n"
            + "select m.id, m.duration, m.exception, m.status, m.name, m.class_id\n"
            + "from methods m\n"
            + "         inner join classes c on m.class_id = c.id\n"
            + "         inner join tests t on c.test_id = t.id\n"
            + "         inner join suites s on t.suite_id = s.id\n"
            + "         inner join test_runs tr on s.test_run_id = tr.id\n"
            + "where status = ?1\n"
            + "  and tr.id = ?2", nativeQuery = true)
    Optional<List<MethodEntity>> findAllByStatusAndTestRunId(String status, int id);
}
