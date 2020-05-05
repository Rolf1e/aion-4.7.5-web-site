package com.aion.server.controller;

import com.aion.server.service.RegisterService;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.exception.UserExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Slf4j
@RestController
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @CrossOrigin
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public OutputUserInfos register(@RequestBody InputUserInfos userToRegister) {
        try {
            return registerService.registerNewUser(userToRegister);
        } catch (UserExistException e) {
            log.error("User {} already exist", userToRegister.getUsername(), e);
            return new OutputUserInfos(userToRegister, "User already exist");
        } catch (EncodeException e) {
            log.error("Failed to encode password for user {}", userToRegister.getUsername(), e);
            return new OutputUserInfos(userToRegister, "Failed to encode password");
        } catch (MessagingException e) {
            log.error("Failed to send mail to user {}", userToRegister.getUsername(), e);
            registerService.deleteUser(userToRegister);
            return new OutputUserInfos(userToRegister, "Please check your e-mail");
        }
    }
}
