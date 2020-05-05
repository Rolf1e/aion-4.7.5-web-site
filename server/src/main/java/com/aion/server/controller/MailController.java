package com.aion.server.controller;

import com.aion.server.component.mail.infra.dto.WebMailData;
import com.aion.server.database.entity.login.AccountData;
import com.aion.server.service.MailService;
import com.aion.server.service.TokenRefresherService;
import com.aion.server.service.infra.exception.TokenRefresherException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Slf4j
//@RestController
@AllArgsConstructor
public class MailController {

    private final MailService mailService;
    private final TokenRefresherService tokenRefresherService;

//    @CrossOrigin(origins = "http://51.178.130.119:3000")
//    @PostMapping(value = "/send-mail", consumes = "application/json", produces = "application/json")
    public String sendMailTo(@RequestBody final WebMailData webMailData) {
        try {
            final AccountData accountData = tokenRefresherService.refreshToken(webMailData.getToken());
            final WebMailData sanitizedWebMailData = new WebMailData(
                    accountData.getToken(),
                    webMailData.getTo(),
                    webMailData.getSubject(),
                    webMailData.getContent()
            );
            mailService.sendMailTo(sanitizedWebMailData);
            log.info("Send mail to {}", sanitizedWebMailData.getTo());
            return "Your mail has been sent !";
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}", webMailData.getTo(), e);
            return "Failed to send mail";
        } catch (TokenRefresherException e) {
            log.error("Invalid token to send {}", webMailData.getTo(), e);
            return "Token is invalid";
        }
    }
}
