package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.exception.InputInformationException;
import com.aion.server.service.infra.utils.DateUtils;
import com.aion.server.service.infra.utils.EncryptionUtils;
import com.aion.server.service.infra.utils.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class TokenService {

    private final AccountDataRepository accountDataRepository;

    public TokenService(final AccountDataRepository accountDataRepository) {
        this.accountDataRepository = accountDataRepository;
    }

    /**
     * @param token
     * @return true if token is valid
     */
    public boolean checkToken(final String token) {
        return accountDataRepository.findByToken(token)
                .filter(accountData -> {
                    final Date updatedAt = accountData.getUpdatedAt();
                    return accountData.getUpdatedAt().equals(DateUtils.resolveDate(updatedAt));
                }).isPresent();
    }

    public Optional<AccountData> getUserWithToken(final InputUserInfos userInfos) throws InputInformationException, EncodeException {
        if (userInfos.getUsername().isEmpty() && userInfos.getPassword().isEmpty()) {
            throw new InputInformationException();
        }
        final String encryptedPassword = EncryptionUtils.toEncode(userInfos.getPassword());
        return accountDataRepository.findByNameAndPassword(userInfos.getUsername(), encryptedPassword);
    }

    public Optional<AccountData> getUserFromToken(final String token) {
        return accountDataRepository.findByToken(token);
    }

    public String generateToken() {
        return TokenGenerator.generate();
    }

}
