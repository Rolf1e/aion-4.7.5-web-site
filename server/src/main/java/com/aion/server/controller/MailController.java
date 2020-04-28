package com.aion.server.controller;

import com.aion.server.component.mail.infra.dto.WebMailData;
import com.aion.server.service.MailService;
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
public class MailController {

    private final MailService mailService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/send-mail", consumes = "application/json", produces = "application/json")
    public String sendMailTo(@RequestBody final WebMailData webMailData) {
        try {
            mailService.sendMailTo(webMailData);
            log.info("Send mail to {}", webMailData.getTo());
            return "Your mail has been sent !";
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}", webMailData.getTo(), e);
            return "Failed to send mail";
        }
    }
}
