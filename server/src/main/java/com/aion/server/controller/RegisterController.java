package com.aion.server.controller;

import com.aion.server.service.RegisterService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public OutputUserInfos register(@RequestBody InputUserInfos userToRegister) {
        try {
            return registerService.registerNewUser(userToRegister);
        } catch (UserExistException e) {
            log.error("User {} already exist", userToRegister.getUsername(), e);
        }
        return new OutputUserInfos(userToRegister, true );
    }
}
