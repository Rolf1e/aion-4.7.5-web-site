package com.aion.server.controller;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.service.RegisterService;
import com.aion.server.service.TokenService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.exception.InputInformationException;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final RegisterService registerService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        //todo add check from token
        try {
            final Optional<AccountData> userWithToken = tokenService.getUserWithToken(userInfos);
            return userWithToken.map(accountData -> new OutputUserInfos(accountData, "Successfully getting token"))
                    .orElseGet(() -> new OutputUserInfos(userInfos, "Failed to log user"));
        } catch (InputInformationException e) {
            log.error("User information are bad ", e);
            return new OutputUserInfos(userInfos, "User information are bad");
        } catch (EncodeException e) {
            log.error("Failed to encode password");
            return new OutputUserInfos(userInfos, "Failed to encode password");
        }
    }

    //Todo redirection
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/valid")
    public String confirmMail(@RequestParam("token") String token) {
        try {
            if (tokenService.checkToken(token)) {
                registerService.updateActivatedUser(token);
                return "You finished account validation";
            }
        } catch (UserDoesntExistException e) {
            log.error("User {} exist", token, e);
        }
        return "Something went wrong !";
    }
}
