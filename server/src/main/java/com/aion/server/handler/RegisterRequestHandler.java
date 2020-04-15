package com.aion.server.handler;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import com.aion.server.component.mail.infra.dto.MessageData;
import com.aion.server.component.mail.infra.sender.MailSender;
import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.exception.UserExistException;
import com.aion.server.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.aion.server.component.mail.config.MailServerConf.MAIL_SENDER;
import static com.aion.server.database.config.TableDBConfig.*;
import static java.util.Arrays.asList;

@Slf4j
public class RegisterRequestHandler extends AbstractRequestHandler {

    private String encryptedPassword;
    private final MailSender mailSender;
    private final List<String> valuesToFilWith;

    public RegisterRequestHandler(final DBClient dbClient,
                                  InputUserInfos userInfos,
                                  final MailSender mailSender) {

        super(dbClient, userInfos);
        encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        this.mailSender = mailSender;
        valuesToFilWith = new ArrayList<>();
    }

    public OutputUserInfos registerNewUser() throws UserExistException {
        try {
            if (!checkRegistered()) {
                insert(toInsertUser());
                valuesToFilWith.addAll(Arrays.asList("lien de vérification", "15 / 4 / 2020"));
                sendMail();
                return new TokenRequestHandler(dbClient, userInfos)
                        .getUserWithToken();
            }
            throw new UserExistException(userInfos.getUsername());
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}", userInfos.getMail(), e);
        }
        return new OutputUserInfos();
    }

    public boolean checkRegistered() {
        return new LoginRequestHandler(dbClient, userInfos)
                .checkRegistered();
    }

    private SQLQuery toInsertUser() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN, MAIL),
                Collections.singletonList(USERS_TABLE),
                asList(userInfos.getUsername(), encryptedPassword, userInfos.getMail())
        );
    }

    private void sendMail() throws MessagingException {
        mailSender.sendMailTo(generateMessageData());
    }

    private MessageData generateMessageData() {
        return new MessageData(new String[]{userInfos.getMail()},
                MAIL_SENDER,
                MailTemplate.CONFIRM_LOGIN,
                valuesToFilWith);
    }
}
