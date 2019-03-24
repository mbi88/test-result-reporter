package com.mbi.api.repositories;

import com.mbi.api.entities.users.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

    List<UsersEntity> findByName(String name);
}
