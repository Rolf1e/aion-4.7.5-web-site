package com.aion.server.controller;

import com.aion.server.database.infra.DBClient;
import com.aion.server.service.dto.InputUserInfos;
import com.aion.server.service.dto.OutputUserInfos;
import com.aion.server.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private DBClient dbClient;

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        return new TokenService(userInfos, dbClient)
                .getUserWithToken();
    }
}
