package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.utils.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                .isPresent();
    }

    public Optional<AccountData> getUserWithToken(final InputUserInfos userInfos) {
        final String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        return accountDataRepository.findByNameAndPassword(userInfos.getUsername(), encryptedPassword);
    }

    public Optional<AccountData> getUserFromToken(final String token) {
        return accountDataRepository.findByToken(token);
    }

    public String generateToken() {
        return TokenGenerator.generate();
    }

}
