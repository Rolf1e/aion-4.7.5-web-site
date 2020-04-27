package com.aion.server.service;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import com.aion.server.component.mail.infra.dto.MessageData;
import com.aion.server.component.mail.infra.sender.MailSender;
import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import com.aion.server.service.infra.exception.UserExistException;
import com.aion.server.service.infra.utils.EncryptionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

import static com.aion.server.component.mail.config.MailServerConf.MAIL_SENDER;
import static java.util.Arrays.asList;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterService {

    private final TokenService tokenService;
    private final LoginService loginService;
    private final MailSender mailSender;
    private final AccountDataRepository accountDataRepository;

    public OutputUserInfos registerNewUser(final InputUserInfos userInfos) throws UserExistException, MessagingException, EncodeException {
        if (!checkRegistered(userInfos)) {
            final String token = tokenService.generateToken();
            final List<String> valuesToFilWith = asList("/valid?token=" + token, "15 / 4 / 2020");
            final String encryptedPassword = EncryptionUtils.toEncode(userInfos.getPassword());
            final AccountData accountData = insertUserWithToken(userInfos, token, encryptedPassword);
            sendMail(userInfos, valuesToFilWith);
            return new OutputUserInfos(accountData, "Successfully register user");
        }
        throw new UserExistException(userInfos.getUsername());
    }

    public void updateActivatedUser(final String token) throws UserDoesntExistException {
        final Optional<AccountData> byToken = accountDataRepository.findByToken(token);
        if (!byToken.isPresent()) {
            throw new UserDoesntExistException(token);
        }
        final AccountData accountData = byToken.get();
        accountData.setActivated(1);
        accountDataRepository.save(accountData);
        log.info("User {}  has confirmed email ", accountData.getId());
    }

    public boolean checkRegistered(final InputUserInfos userInfos) throws EncodeException {
        return loginService.checkRegistered(userInfos);
    }

    public void deleteUser(final InputUserInfos userInfos) {
        accountDataRepository.deleteByNameAndPassword(userInfos.getUsername(), userInfos.getPassword());
    }

    private AccountData insertUserWithToken(final InputUserInfos userInfos,
                                            final String token,
                                            final String encryptedPassword) {

        final AccountData accountData = new AccountData(userInfos, token, encryptedPassword);
        return accountDataRepository.save(accountData);
    }

    private void sendMail(final InputUserInfos userInfos,
                          final List<String> valuesToFilWith) throws MessagingException {

        mailSender.sendMailTo(generateMessageData(userInfos, valuesToFilWith));
        log.info("Mail has been sent to {}", userInfos.getUsername());
    }

    private MessageData generateMessageData(final InputUserInfos userInfos,
                                            final List<String> valuesToFilWith) {

        return new MessageData(new String[]{userInfos.getMail()},
                MAIL_SENDER,
                MailTemplate.CONFIRM_LOGIN,
                valuesToFilWith);
    }
}
