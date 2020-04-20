package com.aion.server.controller;

import com.aion.server.service.RegisterService;
import com.aion.server.service.TokenService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final RegisterService registerService;

    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        return tokenService.getUserWithToken(userInfos);
    }

    @GetMapping("/valid")
    public boolean confirmMail(@RequestParam("token") String token) {
        try {
            if (tokenService.checkToken(token)) {
                registerService.updateActivatedUser(token);
                return true;
            }
        } catch (SQLException e) {
            log.error("Failed to check token {} ", token, e);
        }
        return false;
    }
}
