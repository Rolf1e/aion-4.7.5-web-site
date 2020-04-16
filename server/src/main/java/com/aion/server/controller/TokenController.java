package com.aion.server.controller;

import com.aion.server.service.TokenService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        return tokenService.getUserWithToken(userInfos);
    }

    @GetMapping("/valid")
    public boolean confirmMail(@RequestParam("token") String token) {
        try {
            return tokenService.checkToken(token);
        } catch (SQLException e) {
            log.error("Failed to check token {} ", token, e);
            return false;
        }
    }
}
