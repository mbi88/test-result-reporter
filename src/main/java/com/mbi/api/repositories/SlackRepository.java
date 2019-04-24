package com.mbi.api.repositories;

import com.mbi.api.entities.slack.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Slack repository.
 */
@Repository
public interface SlackRepository extends CrudRepository<MessageEntity, Integer> {

    Optional<MessageEntity> findByTs(String ts);
}
