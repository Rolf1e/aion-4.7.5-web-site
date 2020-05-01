package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.exception.TokenRefresherException;
import com.aion.server.service.infra.utils.DateUtils;
import com.aion.server.service.infra.utils.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.aion.server.service.infra.utils.DateUtils.resolveDate;

@Slf4j
@Service
@AllArgsConstructor
public class TokenRefresherService {

    private final AccountDataRepository accountDataRepository;

    public AccountData refreshToken(final String token) throws TokenRefresherException {
        final Optional<AccountData> accountByToken = accountDataRepository.findByToken(token);
        if (accountByToken.isPresent()) {
            final AccountData accountData = accountByToken.get();
            return getAccountData(accountData, resolveDate(accountData.getUpdatedAt()));
        }
        throw new TokenRefresherException();
    }

    private AccountData getAccountData(final AccountData accountData,
                                       final Date updatedAt) {

        if (!updatedAt.equals(accountData.getUpdatedAt())) {
            accountData.setUpdatedAt(updatedAt);
            accountData.setToken(TokenGenerator.generate());
            log.info("Token for user {} is expired, let's renew it !", accountData.getId());
            return accountDataRepository.save(accountData);
        }
        return accountData;
    }
}
