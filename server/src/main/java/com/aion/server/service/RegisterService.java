package com.aion.server.service;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import com.aion.server.component.mail.infra.dto.MessageData;
import com.aion.server.component.mail.infra.sender.MailSender;
import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.*;

import static com.aion.server.component.mail.config.MailServerConf.MAIL_SENDER;
import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.UPDATE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class RegisterService {

    private final DBClient dbClient;
    private final TokenService tokenService;
    private final LoginService loginService;
    private final MailSender mailSender;

    public RegisterService(final DBClient dbClient,
                           final TokenService tokenService,
                           final LoginService loginService,
                           final MailSender mailSender) {

        this.dbClient = dbClient;
        this.tokenService = tokenService;
        this.loginService = loginService;
        this.mailSender = mailSender;
    }

    public OutputUserInfos registerNewUser(final InputUserInfos userInfos) throws UserExistException {

        final List<String> valuesToFilWith = new ArrayList<>();
        final String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        try {
            if (!checkRegistered(userInfos)) {
                dbClient.insert(toInsertUser(userInfos, encryptedPassword), INSERT);
                valuesToFilWith.addAll(Arrays.asList("lien de vérification", "15 / 4 / 2020"));
                sendMail(userInfos, valuesToFilWith);                          //TODO add "/valid?token=" + token
                return tokenService.getUserWithToken(userInfos);
            }
            throw new UserExistException(userInfos.getUsername());
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}", userInfos.getMail(), e);
        }
        return new OutputUserInfos();
    }

    public void updateActivatedUser(final String token) throws SQLException {
        dbClient.insert(toUpdateUserActivated(token), UPDATE);
    }

    public boolean checkRegistered(final InputUserInfos userInfos) {
        return loginService.checkRegistered(userInfos);
    }

    private SQLQuery toInsertUser(final InputUserInfos userInfos,
                                  final String encryptedPassword) {

        return SQLQueryBuilder.buildInsertQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN, MAIL),
                singletonList(USERS_TABLE),
                asList(userInfos.getUsername(), encryptedPassword, userInfos.getMail())
        );
    }

    private SQLQuery toUpdateUserActivated(final String token) {
        return SQLQueryBuilder.buildUpdateQuery(
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getSet(), SQLQuery.ConditionType.EQUAL)),
                singletonList(new SQLQuery.Condition(getWhereToken(token), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhereToken(final String token) {
        Map<String, String> where = new HashMap<>();
        where.put(TOKEN_COLUMN, token);
        return where;
    }

    private Map<String, String> getSet() {
        Map<String, String> set = new HashMap<>();
        set.put(ACCOUNT_ACTIVATED, "1");
        return set;
    }

    private void sendMail(final InputUserInfos userInfos,
                          final List<String> valuesToFilWith) throws MessagingException {

        mailSender.sendMailTo(generateMessageData(userInfos, valuesToFilWith));
    }

    private MessageData generateMessageData(final InputUserInfos userInfos,
                                            final List<String> valuesToFilWith) {

        return new MessageData(new String[]{userInfos.getMail()},
                MAIL_SENDER,
                MailTemplate.CONFIRM_LOGIN,
                valuesToFilWith);
    }
}
