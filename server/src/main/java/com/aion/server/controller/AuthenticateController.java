package com.aion.server.controller;

import com.aion.server.controller.dto.Login;
import com.aion.server.database.infra.DBClient;
import com.aion.server.component.login.LoginResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    @Autowired
    private DBClient dbClient;

    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public Boolean login(@RequestBody Login login) {
        return new LoginResolver(dbClient, login)
                .exist();
    }
}
