package com.aion.server.database.repositories.login;

import com.aion.server.database.entity.login.AccountData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDataRepository extends CrudRepository<AccountData, Long> {

    Optional<AccountData> findByNameAndPassword(final String username, final String password);

    Optional<AccountData> findByToken(final String token);
}
