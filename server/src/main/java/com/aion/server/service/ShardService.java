package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ShardService {

    private final AccountDataRepository accountDataRepository;

    //todo test
    public boolean giveShardsToPlayer(final int idPlayer,
                                      final int shardAmount) {

        final Optional<AccountData> byId = accountDataRepository.findById((long) idPlayer);
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
}
