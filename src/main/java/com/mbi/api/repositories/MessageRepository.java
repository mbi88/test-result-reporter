package com.mbi.api.repositories;

import com.mbi.api.entities.message.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Message repository.
 */
@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

    Optional<MessageEntity> findByTs(String ts);
}
