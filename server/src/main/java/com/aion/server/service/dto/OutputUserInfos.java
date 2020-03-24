package com.aion.server.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputUserInfos {

    private String username;
    private String password;
    private String token;

    public OutputUserInfos(String username,
                           String password,
                           String token) {

        this.username = username;
        this.password = password;
        this.token = token;
    }
}
