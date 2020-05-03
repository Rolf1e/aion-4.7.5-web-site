package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.utils.EncryptionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {

    private final AccountDataRepository accountDataRepository;

    public boolean checkRegistered(final InputUserInfos userInfos) {
        return accountDataRepository.existsByName(userInfos.getUsername());
    }

    public boolean checkAccountIsActivated(final long idPlayer) {
        final Optional<AccountData> byId = accountDataRepository.findById(idPlayer);
        return byId.filter(accountData -> accountData.getActivated() != 0)
                .isPresent();
    }
}
