package com.aion.server.database.repositories.login;

import com.aion.server.database.entity.AccountData;
import org.springframework.data.repository.CrudRepository;

public interface AccountDataRepository extends CrudRepository<AccountData, Long> {

    AccountData findById(final int it);

    AccountData findByNameAndPassword(final String username, final String password);
}
