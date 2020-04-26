package com.aion.server.service.infra.dto;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.service.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputUserInfos {

    private long id;
    private String username;
    private String password;
    private String token;
    private long shard;
    private int premium;
    private String error;

    public OutputUserInfos(String username,
                           String password,
                           String token) {

        this.username = username;
        this.password = password;
        this.token = token;
    }

    public OutputUserInfos(final InputUserInfos userInfos,
                           final String error) {
        this.username = userInfos.getUsername();
        this.password = userInfos.getPassword();
        this.error = error;
    }


    public OutputUserInfos(final AccountData accountData,
                           final String error) {
        this.username = accountData.getName();
        this.password = EncryptionService.toDecode(accountData.getPassword());
        this.token = accountData.getToken();
        this.premium = accountData.getMembership();
        this.shard = accountData.getToll();
        this.error = error;
    }

}
