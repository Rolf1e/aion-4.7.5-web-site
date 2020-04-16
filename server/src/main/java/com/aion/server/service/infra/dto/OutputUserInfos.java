package com.aion.server.service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputUserInfos {

    private String id;
    private String username;
    private String password;
    private String token;
    private boolean error;

    public OutputUserInfos(String username,
                           String password,
                           String token) {

        this.username = username;
        this.password = password;
        this.token = token;
    }

    public OutputUserInfos(final InputUserInfos userInfos,
                           final boolean error) {
        this.username = userInfos.getUsername();
        this.password = userInfos.getPassword();
        this.error = error;
    }

}
