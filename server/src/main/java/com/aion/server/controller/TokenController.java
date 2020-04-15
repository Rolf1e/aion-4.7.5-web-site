package com.aion.server.controller;

import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.TokenRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
public class TokenController {

    @Autowired
    private DBClient dbClient;

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        return new TokenRequestHandler(dbClient, userInfos)
                .getUserWithToken();
    }

    @GetMapping("/valid")
    public boolean confirmMail(@RequestParam("token") String token) {
        try {
            return new TokenRequestHandler(dbClient, token)
                    .checkToken();
        } catch (SQLException e) {
            log.error("Failed to check token {} ", token, e);
            return false;
        }
    }
}
