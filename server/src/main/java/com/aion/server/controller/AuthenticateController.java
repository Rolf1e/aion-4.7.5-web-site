package com.aion.server.controller;

import com.aion.server.controller.dto.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public Boolean login(@RequestBody Login login) {
        return true;
    }
}
