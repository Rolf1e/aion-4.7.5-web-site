package com.aion.server.controller;

import com.aion.server.controller.dto.Login;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.login.LoginResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticateController {

    @Autowired
    private DBClient dbClient;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public Boolean login(@RequestBody Login login) {
        return new LoginResolver(dbClient, login)
                .checkExist();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }
}
