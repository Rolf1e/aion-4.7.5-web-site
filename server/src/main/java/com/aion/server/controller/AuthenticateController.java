package com.aion.server.controller;

import com.aion.server.database.infra.DBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    @Autowired
    private DBClient dbClient;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }
}
