package com.aion.server.controller;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.service.RegisterService;
import com.aion.server.service.TokenRefresherService;
import com.aion.server.service.TokenService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.exception.InputInformationException;
import com.aion.server.service.infra.exception.TokenRefresherException;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class TokenController {

    public static final String GOOD_RESPONSE_HTML = "/templates/account/token-validation/goodResponse.html";
    public static final String BAD_RESPONSE_HTML = "/templates/account/token-validation/badResponse.html";
    private final TokenService tokenService;
    private final RegisterService registerService;
    private final TokenRefresherService tokenRefresherService;

    @CrossOrigin
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    //TODO TO REFACTOR BECOZ THIS IS UGLY !!
    public OutputUserInfos getToken(@RequestBody InputUserInfos userInfos) {
        try {
            //if token is given
            final String token = Optional.ofNullable(userInfos.getToken())
                    .orElse("");
            if (!token.equals("")) {
                return new OutputUserInfos(tokenRefresherService.refreshToken(token), "Successfully retrieving token !");
            }
            final Optional<AccountData> userWithToken = tokenService.getUserWithToken(userInfos);
            return userWithToken.map(accountData -> new OutputUserInfos(accountData, "Successfully getting token"))
                    .orElseGet(() -> new OutputUserInfos(userInfos, "Failed to log user"));
        } catch (InputInformationException e) {
            log.error("User information are bad ", e);
            return new OutputUserInfos(userInfos, "User information are bad");
        } catch (EncodeException e) {
            log.error("Failed to encode password");
            return new OutputUserInfos(userInfos, "Failed to encode password");
        } catch (TokenRefresherException e) {
            log.error("Failed to refresh token");
            return new OutputUserInfos(userInfos, "Failed to refresh token");
        }
    }

    //Todo redirection
    @CrossOrigin
    @GetMapping("/valid")
    public String confirmMail(@RequestParam("token") String token) throws IOException {
        try {
            if (tokenService.checkToken(token)) {
                registerService.updateActivatedUser(token);
                return IOUtils.toString(this.getClass().getResourceAsStream(GOOD_RESPONSE_HTML), StandardCharsets.UTF_8);
            }
        } catch (UserDoesntExistException e) {
            log.error("User {} exist", token, e);
        }
        return IOUtils.toString(this.getClass().getResourceAsStream(BAD_RESPONSE_HTML), StandardCharsets.UTF_8);
    }
}
