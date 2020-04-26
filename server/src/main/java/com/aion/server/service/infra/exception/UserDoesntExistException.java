package com.aion.server.service.infra.exception;

import com.aion.server.database.entity.login.AccountData;

public class UserDoesntExistException extends LoginException {

    public UserDoesntExistException(final String token) {
        super("User does not exist for token :" + token);
    }

    public UserDoesntExistException(final AccountData accountData) {
        super("User" + accountData.getName() + " does not exist for token :");
    }

}
