package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.login.Payment;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.database.repositories.login.PaymentsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ShardService {

    private final AccountDataRepository accountDataRepository;
    private final PaymentsRepository paymentsRepository;

    //todo test
    public boolean giveShardsToPlayer(final long idPlayer,
                                      final int shardAmount) {

        final Optional<AccountData> byId = accountDataRepository.findById(idPlayer);
        if (!byId.isPresent()) {
            log.info("Failed to get old shard amount for player {}", idPlayer);
            return false;
        }
        final AccountData accountData = byId.get();
        accountData.giveToll(shardAmount);
        accountDataRepository.save(accountData);
        log.info("Give {} shards to player {}", shardAmount, idPlayer);
        return true;
    }

    public Payment savePayments(final String idPayment,
                                final long accountId) {

        return paymentsRepository.save(new Payment(idPayment, accountId));
    }

    public boolean checkAlreadyUsed(final String idPayment) {
        return paymentsRepository.existsById(idPayment);
    }
}
