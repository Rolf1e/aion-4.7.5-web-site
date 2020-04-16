package com.aion.server.controller;

import com.aion.server.service.RegisterService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public OutputUserInfos register(@RequestBody InputUserInfos userToRegister) throws UserExistException {
        return registerService.registerNewUser(userToRegister);
    }
}
