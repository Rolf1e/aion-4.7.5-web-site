package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {

    private final AccountDataRepository accountDataRepository;

    public boolean checkRegistered(final InputUserInfos userInfos) throws EncodeException {
        final String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        return accountDataRepository.existsByNameAndPassword(userInfos.getUsername(), encryptedPassword);
    }

    public boolean checkAccountIsActivated(final long idPlayer) {
        final Optional<AccountData> byId = accountDataRepository.findById(idPlayer);
        return byId.filter(accountData -> accountData.getActivated() != 0)
                .isPresent();
    }
}
