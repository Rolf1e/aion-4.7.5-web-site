package com.aion.server.controller;

import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.RegisterRequestHandler;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private DBClient dbClient;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public OutputUserInfos register(@RequestBody InputUserInfos userToRegister) throws UserExistException {
        return new RegisterRequestHandler(dbClient, userToRegister)
                .registerNewUser();
    }
}
