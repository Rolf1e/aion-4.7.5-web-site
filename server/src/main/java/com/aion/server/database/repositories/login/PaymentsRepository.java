package com.aion.server.database.repositories.login;

import com.aion.server.database.entity.login.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends CrudRepository<Payment, Long> {

    boolean existsById(final String idPayment);

}
